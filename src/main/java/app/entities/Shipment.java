package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a shipment of a parcel from one location to another.
 * Each shipment is linked to a single parcel, a source location, and a destination location.
 */
@Entity // Marks this class as a JPA entity to be mapped to a database table
@Data // Lombok annotation that generates getters, setters, equals, hashCode, and toString
@AllArgsConstructor // Generates a constructor with all fields
@NoArgsConstructor // Generates a no-args constructor (required by JPA)
@EqualsAndHashCode // Ensures proper equality checks
@ToString // Generates a readable toString method
@Builder // Enables fluent builder-style object creation
@Table(name = "shipments") // Maps this entity to the "shipments" table in the database
public class Shipment {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates ID using database identity column
    private int id;

    /**
     * The parcel being shipped.
     * Many shipments can be linked to one parcel.
     * Lazy loading avoids fetching the parcel unless explicitly accessed.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "parcel_id", nullable = false) // Foreign key column in the shipments table
    private Parcel parcel;

    /**
     * The location from which the parcel is shipped.
     * Each shipment has one source location.
     * Eager loading ensures this is fetched immediately with the shipment.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "source_location_id") // Foreign key column for source location
    private Location sourceLocation;

    /**
     * The location to which the parcel is shipped.
     * Each shipment has one destination location.
     * Lazy loading defers fetching until needed.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "destination_location_id") // Foreign key column for destination location
    private Location destinationLocation;

    /**
     * The date and time when the shipment occurred.
     * This field is mandatory.
     */
    @Column(nullable = false, name = "shipment_date_time")
    private LocalDateTime shipmentDateTime;
}
