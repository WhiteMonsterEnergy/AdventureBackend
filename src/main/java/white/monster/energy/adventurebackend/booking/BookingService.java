package white.monster.energy.adventurebackend.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import white.monster.energy.adventurebackend.bookedActivities.BookedActivity;
import white.monster.energy.adventurebackend.profile.Profile;
import white.monster.energy.adventurebackend.profile.ProfileRepository;
import white.monster.energy.adventurebackend.profile.ProfileService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ProfileRepository profileRepository;
    private final ProfileService    profileService;

    // Saves a new booking to the database.
    public Booking create(Booking booking)
    {
        // establish data in database in correct order to ensure proper foreign-keying
        booking.setId(bookingRepository.save(new Booking()).getId()); // "reserve" spot in database for bookedActivities to reference
        booking.getVisitor().setId(profileService.createProfile(booking.getVisitor()).getId()); // todo, get if existing
        booking.getBookedActivities().forEach(bookedActivity -> bookedActivity.setBooking(booking));

        return bookingRepository.save(booking);
    }

    // Finds one booking by its ID.
    @Transactional(readOnly = true)
    public Booking getById(int id) {
        return bookingRepository.findById(id).orElse(null);
    }

    // Gets a list of all bookings.
    @Transactional(readOnly = true)
    public List<Booking> listAll() {
        return bookingRepository.findAll();
    }

    // Shows all bookings made by one visitor.
    @Transactional(readOnly = true)
    public List<Booking> listByCustomer(int visitorId) {
        return bookingRepository.findByVisitorId(visitorId);
    }

    // Shows bookings a few at a time (paged)
    @Transactional(readOnly = true)
    public Page<Booking> findAll(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    // Finds bookings with a certain status (like DRAFT or CONFIRMED).
    @Transactional(readOnly = true)
    public Page<Booking> findByStatus(String status, Pageable pageable) {
        return bookingRepository.findByStatusIn(List.of(status), pageable);
    }

    // Finds bookings that start within a certain time range.
    @Transactional(readOnly = true)
    public Page<Booking> findByStartTimeBetween(LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return bookingRepository.findByStartTimeBetween(from, to, pageable);
    }

    // Updates a booking by changing its details.
    @Transactional
    public Booking update(int id, BookingDto dto) {
        Booking b = getById(id);
        if (b == null) return null;

        if (dto.type() != null) b.setType(dto.type());
        if (dto.startTime() != null) b.setStartTime(dto.startTime());
        if (dto.endTime() != null) b.setEndTime(dto.endTime());
        if (dto.participants() != null) b.setParticipants(dto.participants());
        if (dto.status() != null) b.setStatus(dto.status());
        if (dto.notes() != null) b.setNotes(dto.notes());

        return bookingRepository.save(b);
    }

    // Deletes a booking by its ID.
    @Transactional
    public boolean delete(int id) {
        if (!bookingRepository.existsById(id)) return false;
        bookingRepository.deleteById(id);
        return true;
    }

    // Saves a booking. Used for both new and updated ones
    @Transactional
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking assignEmployee(int bookingId, int employeeId) {
        Booking booking = getById(bookingId);
        if (booking == null) throw new IllegalArgumentException("Booking not found");

        Profile operator = profileRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        booking.setOperator(operator);
        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public List<Booking> getBookingsForEmployee(int employeeId) {
        return bookingRepository.findByOperatorId(employeeId);
    }

}
