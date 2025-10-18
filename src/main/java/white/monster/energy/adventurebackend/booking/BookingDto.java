package white.monster.energy.adventurebackend.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.lang.*;
import white.monster.energy.adventurebackend.employee.Employee;
import white.monster.energy.adventurebackend.profile.Profile;

import java.time.LocalDateTime;

// This helps skip any empty fields when sending data out as JSON
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookingDto(
        int id,
        String type,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Integer participants,
        String status,
        String notes,
        Profile visitor,
        Profile operator
) {
    // Turns a Booking object into a BookingDto so it can be sent to the frontend.
    static BookingDto from(Booking b) {
        int visitorId = (b.getVisitor().getId() != 0) ? b.getVisitor().getId() : 0;
        Profile empl = b.getOperator();
        return new BookingDto(
                b.getId(),
                b.getType(),
                b.getStartTime(),
                b.getEndTime(),
                b.getParticipants(),
                b.getStatus(),
                b.getNotes(),
                b.getVisitor(),
                b.getOperator()
        );
    }
    // Turns a BookingDto back into a Booking so it can be saved in the database.
    Booking toEntity() {
        Booking.BookingBuilder bb = Booking.builder()
                .id(id)
                .type(type)
                .startTime(startTime)
                .endTime(endTime)
                .participants(participants)
                .status(status)
                .notes(notes)
                .visitor(visitor)
                .operator(operator);

        return bb.build();
    }
}
