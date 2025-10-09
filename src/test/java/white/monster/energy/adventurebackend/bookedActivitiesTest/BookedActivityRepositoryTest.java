package white.monster.energy.adventurebackend.bookedActivitiesTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import white.monster.energy.adventurebackend.bookedActivities.BookedActivity;
import white.monster.energy.adventurebackend.bookedActivities.BookedActivityRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookedActivityRepositoryTest {

    @Test
    void testFindByBookingId() {
        // Arrange
        BookedActivityRepository repo = Mockito.mock(BookedActivityRepository.class);
        BookedActivity ba = new BookedActivity();
        Mockito.when(repo.findByBookingId(42)).thenReturn(List.of(ba));

        // Act
        List<BookedActivity> result = repo.findByBookingId(42);

        // Assert
        assertEquals(1, result.size());
        assertEquals(ba, result.get(0));
    }

    @Test
    void testCountByBookingId() {
        // Arrange
        BookedActivityRepository repo = Mockito.mock(BookedActivityRepository.class);
        Mockito.when(repo.countByBookingId(42)).thenReturn(2L);

        // Act
        long count = repo.countByBookingId(42);

        // Assert
        assertEquals(2L, count);
    }

    @Test
    void testDeleteByBookingId() {
        // Arrange
        BookedActivityRepository repo = Mockito.mock(BookedActivityRepository.class);

        // Act
        repo.deleteByBookingId(42);

        // Assert
        Mockito.verify(repo).deleteByBookingId(42);
    }
}
