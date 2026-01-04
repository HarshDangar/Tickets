package org.harsh.tickets.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.harsh.tickets.domain.entities.Ticket;
import org.harsh.tickets.domain.entities.TicketStatusEnum;
import org.harsh.tickets.domain.entities.TicketType;
import org.harsh.tickets.domain.entities.User;
import org.harsh.tickets.exceptions.TicketSoldOutException;
import org.harsh.tickets.exceptions.TicketTypeNotFoundException;
import org.harsh.tickets.exceptions.UserNotFoundException;
import org.harsh.tickets.repositories.TicketRepository;
import org.harsh.tickets.repositories.TicketTypeRepository;
import org.harsh.tickets.repositories.UserRepository;
import org.harsh.tickets.services.QrCodeService;
import org.harsh.tickets.services.TicketTypeService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                String.format("User with ID %s not found", userId)
        ));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId)
                .orElseThrow(() -> new TicketTypeNotFoundException(
                        String.format("Ticket type with ID %s not found", ticketTypeId)
                ));

        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());
        Integer totalAvailable = ticketType.getTotalAvailable();

        if (purchasedTickets + 1 > totalAvailable) {
            throw new TicketSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket);
        qrCodeService.generateQrCode(savedTicket);

        return ticketRepository.save(savedTicket);
    }
}
