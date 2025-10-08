package white.monster.energy.adventurebackend.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.lang.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookingDto(
        int id,
        String type,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Integer participants,
        String status,
        BigDecimal totalPrice,
        String notes,
        int visitorId,
        LocalDateTime holdExpiresAt
) {

    static BookingDto from(Booking b) {
        int visitorId = (b.getVisitorId() != 0) ? b.getVisitorId() : 0;
        return new BookingDto(
                b.getId(),
                b.getType(),
                b.getStartTime(),
                b.getEndTime(),
                b.getParticipants(),
                b.getStatus(),
                b.getTotalPrice(),
                b.getNotes(),
                visitorId,
                b.getHoldExpiresAt()
        );
    }

    Booking toEntity() {
        Booking.BookingBuilder bb = Booking.builder()
                .id(id)
                .type(type)
                .startTime(startTime)
                .endTime(endTime)
                .participants(participants)
                .status(status)
                .totalPrice(totalPrice)
                .notes(notes)
                .holdExpiresAt(holdExpiresAt);

        return bb.build();
    }
}
