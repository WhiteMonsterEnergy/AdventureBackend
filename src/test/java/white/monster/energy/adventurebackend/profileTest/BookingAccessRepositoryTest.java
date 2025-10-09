package white.monster.energy.adventurebackend.profileTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import white.monster.energy.adventurebackend.profile.BookingAccess;
import white.monster.energy.adventurebackend.profile.BookingAccessRepository;
import white.monster.energy.adventurebackend.profile.AccessType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookingAccessRepositoryTest {

    @Autowired
    private BookingAccessRepository repository;

    @Test
    void testFindBookingIdsByProfileId() {
        // Arrange
        BookingAccess access = new BookingAccess();
        access.setProfileId(100);
        access.setBookingId(200);
        access.setAccessType(AccessType.VIEW);
        repository.save(access);

        // Act
        List<Integer> bookingIds = repository.findBookingIdsByProfileId(100);

        // Assert
        assertFalse(bookingIds.isEmpty());
        assertEquals(200, bookingIds.get(0));
    }


    @Test
    void testDeleteByProfileId() {
        // Arrange
        BookingAccess access = new BookingAccess();
        access.setProfileId(101);
        access.setBookingId(201);
        access.setAccessType(AccessType.EDIT);
        repository.save(access);

        // Act
        repository.deleteByProfileId(101);

        // Assert
        List<Integer> bookingIds = repository.findBookingIdsByProfileId(101);
        assertTrue(bookingIds.isEmpty());
    }
}
