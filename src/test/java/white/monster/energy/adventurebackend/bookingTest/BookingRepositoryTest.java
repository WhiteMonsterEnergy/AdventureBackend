package white.monster.energy.adventurebackend.bookingTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import jakarta.persistence.EntityManager;
import white.monster.energy.adventurebackend.booking.Booking;
import white.monster.energy.adventurebackend.booking.BookingRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() throws Exception {
        // Arrange: Persist test bookings
        entityManager.persist(createBooking("ACTIVITY", "CONFIRMED", 1, LocalDateTime.now().minusDays(1), LocalDateTime.now(), BigDecimal.valueOf(100)));
        entityManager.persist(createBooking("ACTIVITY", "HOLD", 2, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), BigDecimal.valueOf(200)));
        entityManager.persist(createBooking("ACTIVITY", "CANCELLED", 1, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(4), BigDecimal.valueOf(300)));
        entityManager.flush();
    }

    private Booking createBooking(String type, String status, int visitorId, LocalDateTime start, LocalDateTime end, BigDecimal price) throws Exception {
        Booking b = new Booking();
        var clazz = b.getClass();

        var typeField = clazz.getDeclaredField("type");
        typeField.setAccessible(true);
        typeField.set(b, type);

        var statusField = clazz.getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(b, status);

        var visitorIdField = clazz.getDeclaredField("visitorId");
        visitorIdField.setAccessible(true);
        visitorIdField.set(b, visitorId);

        var startTimeField = clazz.getDeclaredField("startTime");
        startTimeField.setAccessible(true);
        startTimeField.set(b, start);

        var endTimeField = clazz.getDeclaredField("endTime");
        endTimeField.setAccessible(true);
        endTimeField.set(b, end);

        var participantsField = clazz.getDeclaredField("participants");
        participantsField.setAccessible(true);
        participantsField.set(b, 1);

        var totalPriceField = clazz.getDeclaredField("totalPrice");
        totalPriceField.setAccessible(true);
        totalPriceField.set(b, price);

        return b;
    }


    @Test
    void testFindByVisitorId() {
        // Act
        List<Booking> bookings = bookingRepository.findByVisitorId(1);

        // Assert
        assertEquals(2, bookings.size());
    }

    @Test
    void testFindByStatusIn() {
        // Act
        List<Booking> bookings = bookingRepository.findByStatusIn(List.of("HOLD", "CONFIRMED"), org.springframework.data.domain.PageRequest.of(0, 10)).getContent();

        // Assert
        assertEquals(2, bookings.size());
    }

    @Test
    void testFindByStartTimeBetween() {
        // Act
        LocalDateTime from = LocalDateTime.now().minusDays(2);
        LocalDateTime to = LocalDateTime.now().plusDays(2);
        List<Booking> bookings = bookingRepository.findByStartTimeBetween(from, to, org.springframework.data.domain.PageRequest.of(0, 10)).getContent();

        // Assert
        assertEquals(2, bookings.size());
    }
}
