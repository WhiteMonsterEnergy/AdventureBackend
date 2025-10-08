package white.monster.energy.adventurebackend.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    @Transactional(readOnly = true)
    public Page<Booking> findAll(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Booking> findByStatus(String status, Pageable pageable) {
        return bookingRepository.findByStatusIn(List.of(status), pageable);
    }

    @Transactional(readOnly = true)
    public Page<Booking> findByStartTimeBetween(LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return bookingRepository.findByStartTimeBetween(from, to, pageable);
    }

    @Transactional
    public Booking update(Long id, BookingDto dto) {
        Booking b = getById(id);
        if (b == null) return null;

        if (dto.type() != null) b.setType(dto.type());
        if (dto.startTime() != null) b.setStartTime(dto.startTime());
        if (dto.endTime() != null) b.setEndTime(dto.endTime());
        if (dto.participants() != null) b.setParticipants(dto.participants());
        if (dto.status() != null) b.setStatus(dto.status());
        if (dto.totalPrice() != null) b.setTotalPrice(dto.totalPrice());
        if (dto.notes() != null) b.setNotes(dto.notes());
        if (dto.holdExpiresAt() != null) b.setHoldExpiresAt(dto.holdExpiresAt());

        return bookingRepository.save(b);
    }

    @Transactional
    public boolean delete(Long id) {
        if (!bookingRepository.existsById(id)) return false;
        bookingRepository.deleteById(id);
        return true;
    }
}
