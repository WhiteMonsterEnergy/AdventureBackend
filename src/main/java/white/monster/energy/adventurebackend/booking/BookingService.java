package white.monster.energy.adventurebackend.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    @Transactional
    public Booking create(Booking booking) {
        if (booking.getStatus() == null) booking.setStatus("DRAFT");
        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public Booking getById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Booking> listAll() {
        return bookingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Booking> listByCustomer(Long visitorId) {
        return bookingRepository.findByVisitorId(visitorId);
    }

    @Transactional
    public Booking confirm(Long id) {
        Booking b = getById(id);
        if (b == null) return null;
        b.setStatus("CONFIRMED");
        b.setHoldExpiresAt(null);
        return bookingRepository.save(b);
    }

    @Transactional
    public Booking cancel(Long id) {
        Booking b = getById(id);
        if (b == null) return null;
        b.setStatus("CANCELLED");
        return bookingRepository.save(b);
    }

    @Transactional
    public Booking setHold(Long id, LocalDateTime expiresAt) {
        Booking b = getById(id);
        if (b == null) return null;
        b.setStatus("HOLD");
        b.setHoldExpiresAt(expiresAt);
        return bookingRepository.save(b);
    }
}
