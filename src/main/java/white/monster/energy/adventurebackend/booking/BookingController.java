package white.monster.energy.adventurebackend.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.*;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    // --- LIST (paged) ---
    // GET /api/bookings?page=0&size=20&status=CONFIRMED&from=2025-10-01T00:00:00&to=2025-10-31T23:59:59
    @GetMapping
    public Page<BookingDto> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        Pageable pageable = PageRequest.of(page, size);

        // filtering using service methods
        List<Booking> data;
        long total;

        if (from != null && to != null) {
            Page<Booking> p = service.findByStartTimeBetween(from, to, pageable);
            data = p.getContent();
            total = p.getTotalElements();
        } else if (status != null && !status.isBlank()) {
            Page<Booking> p = service.findByStatus(status, pageable);
            data = p.getContent();
            total = p.getTotalElements();
        } else {
            Page<Booking> p = service.findAll(pageable);
            data = p.getContent();
            total = p.getTotalElements();
        }

        List<BookingDto> dtos = data.stream().map(BookingDto::from).collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, total);
    }

    // --- GET by id ---
    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> get(@PathVariable int id) {
        Booking b = service.getById(id);
        return (b == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(BookingDto.from(b));
    }

    // --- CREATE ---
    @PostMapping
    public ResponseEntity<BookingDto> create(@RequestBody BookingDto dto) {
        Booking b = dto.toEntity();
        Booking created = service.create(b);
        return ResponseEntity.created(URI.create("/api/bookings/" + created.getId()))
                .body(BookingDto.from(created));
    }

    // --- UPDATE ---
    @PatchMapping("/{id}")
    public ResponseEntity<BookingDto> update(@PathVariable int id, @RequestBody BookingDto dto) {
        Booking updated = service.update(id, dto);
        return (updated == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(BookingDto.from(updated));
    }

    // --- DELETE ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean ok = service.delete(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/assign-employee")
    public ResponseEntity<?> assignEmployee(
            @RequestParam int bookingId,
            @RequestParam int employeeId) {
        try {
            Booking updated = service.assignEmployee(bookingId, employeeId);
            return ResponseEntity.ok(BookingDto.from(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/assigned-to/{employeeId}")
    public ResponseEntity<List<BookingDto>> getBookingsForEmployee(@PathVariable int employeeId) {
        List<Booking> bookings = service.getBookingsForEmployee(employeeId);
        List<BookingDto> dtos = bookings.stream()
                .map(BookingDto::from)
                .toList();
        return ResponseEntity.ok(dtos);
    }

}
