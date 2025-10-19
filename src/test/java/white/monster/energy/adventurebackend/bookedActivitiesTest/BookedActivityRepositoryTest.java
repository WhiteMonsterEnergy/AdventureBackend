package white.monster.energy.adventurebackend.bookedActivitiesTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import white.monster.energy.adventurebackend.bookedActivities.BookedActivity;
import white.monster.energy.adventurebackend.bookedActivities.BookedActivityRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for BookedActivityRepository. */
public class BookedActivityRepositoryTest {

    /** Test the findByBookingId method of BookedActivityRepository. */
    @Test
    void testFindByBookingId() {
        // Arrange: Mock the repository and set up expected behavior
        BookedActivityRepository repo = Mockito.mock(BookedActivityRepository.class);
        BookedActivity ba = new BookedActivity();
        // Set properties of ba as needed
        Mockito.when(repo.findByBookingId(42)).thenReturn(List.of(ba));

        // Act: Call the method under test
        List<BookedActivity> result = repo.findByBookingId(42);

        // Assert: Verify the results
        assertEquals(1, result.size());
        // Further assertions can be made based on the properties of ba
        assertEquals(ba, result.get(0));
    }

    /** Test the countByBookingId method of BookedActivityRepository. */
    @Test
    void testCountByBookingId() {
        // Arrange: Mock the repository and set up expected behavior
        BookedActivityRepository repo = Mockito.mock(BookedActivityRepository.class);
        // Assume there are 2 booked activities for bookingId 42
        Mockito.when(repo.countByBookingId(42)).thenReturn(2L);

        // Act: Call the method under test
        long count = repo.countByBookingId(42);

        // Assert: Verify the results
        assertEquals(2L, count);
    }

    /** Test the deleteByBookingId method of BookedActivityRepository. */
    @Test
    void testDeleteByBookingId() {
        // Arrange: Mock the repository
        BookedActivityRepository repo = Mockito.mock(BookedActivityRepository.class);

        // Act: Call the method under test
        repo.deleteByBookingId(42);

        // Assert: Verify that the method was called
        Mockito.verify(repo).deleteByBookingId(42);
    }
}
