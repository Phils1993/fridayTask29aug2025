package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@Table(name = "shipments")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    // link to a parcel (package)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parcel_id", nullable = false)
    private Parcel parcel;


    // Source location
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_location_id")
    private Location sourceLocation;

    // Destination location
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "destination_location_id")
    private Location destinationLocation;


    @Column(nullable = false, name = "shipment_date_time")
    private LocalDateTime shipmentDateTime;


}
