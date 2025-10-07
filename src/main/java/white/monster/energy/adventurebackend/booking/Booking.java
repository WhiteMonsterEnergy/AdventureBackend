package white.monster.energy.adventurebackend.booking;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "bookings",
        indexes = {
                @Index(name = "idx_bookings_start_time", columnList = "start_time"),
                @Index(name = "idx_bookings_customer_id", columnList = "customer_id"),
                @Index(name = "idx_bookings_status", columnList = "status")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** "PRIVATE" or "COMPANY" (kept as String to avoid adding enum files) */
    @Column(nullable = false, length = 20)
    private String type;

    /** First activity start and final activity end for this booking */
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    /** Total number of participants */
    @Column(nullable = false)
    private Integer participants;

    /** Booking state: e.g., DRAFT, HOLD, PENDING_PAYMENT, CONFIRMED, COMPLETED, CANCELLED */
    @Column(nullable = false, length = 30)
    private String status;

    /** Final price (DKK) at time of confirmation */
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;

    /** Free-text notes for special requirements */
    @Column(length = 1000)
    private String notes;

    /** Reference to customer (scalar FK for now) */
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    /** Optional hold expiration for temporary slot reservations during checkout */
    @Column(name = "hold_expires_at")
    private LocalDateTime holdExpiresAt;

    /** Audit + optimistic locking */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private long version;
}
