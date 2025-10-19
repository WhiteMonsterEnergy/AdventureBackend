package white.monster.energy.adventurebackend.booking;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import white.monster.energy.adventurebackend.profile.ProfileType;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.*;

@CrossOrigin
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    // Show a list of bookings.
    // search by time period or status and see results a few at a time (paged).
    @GetMapping
    public Page<BookingDto> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            HttpServletRequest request
    ) {
//        if (ProfileType.ADMIN.verifyAccessLevel(request)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Pageable pageable = PageRequest.of(page, size);

        // find bookings that match the search
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
        // look at each booking one by one. For every booking, run from() to make a BookingDto out of it.
        // and gather all those BookingDtos and put them back into a list.
        List<BookingDto> dtos = data.stream().map(BookingDto::from).collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, total);
    }

    // Find a booking by its ID and show it.
    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> get(@PathVariable int id, HttpServletRequest request) {
        if (ProfileType.ADMIN.verifyAccessLevel(request)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Booking b = service.getById(id);
        return (b == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(BookingDto.from(b));
    }

    // Create a new booking and save it.
    @PostMapping
    public ResponseEntity<BookingDto> create(@RequestBody Booking booking) {
        Booking created = service.create(booking);
        return ResponseEntity.created(URI.create("/api/bookings/" + created.getId()))
                .body(BookingDto.from(created));
    }

    // Change a booking that already exists.
    @PatchMapping("/{id}")
    public ResponseEntity<BookingDto> update(@PathVariable int id, @RequestBody BookingDto dto, HttpServletRequest request) {
        if (ProfileType.ADMIN.verifyAccessLevel(request)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Booking updated = service.update(id, dto);
        return (updated == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(BookingDto.from(updated));
    }

    // Delete a booking by its ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id, HttpServletRequest request) {
        if (ProfileType.ADMIN.verifyAccessLevel(request)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        boolean ok = service.delete(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/assign-employee")
    public ResponseEntity<?> assignEmployee(
            @RequestParam int bookingId,
            @RequestParam int employeeId,
            HttpServletRequest request) {
        if (ProfileType.ADMIN.verifyAccessLevel(request)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        try {
            Booking updated = service.assignEmployee(bookingId, employeeId);
            return ResponseEntity.ok(BookingDto.from(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/assigned-to/{employeeId}")
    public ResponseEntity<List<BookingDto>> getBookingsForEmployee(@PathVariable int employeeId, HttpServletRequest request) {
        if (ProfileType.OPERATOR.verifyAccessLevel(request)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        List<Booking> bookings = service.getBookingsForEmployee(employeeId);
        List<BookingDto> dtos = bookings.stream()
                .map(BookingDto::from)
                .toList();
        return ResponseEntity.ok(dtos);
    }

}
