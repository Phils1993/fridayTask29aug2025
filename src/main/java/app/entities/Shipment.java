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
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;



    // link to a parcel (package)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "parcel_id", nullable = false) lav hellere denne som en typedquery
    private Parcel parcel;


    // Source location
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Location sourceLocation;

    // Destination location
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Location destinationLocation;


    @Column(nullable = false,name = "shipment_date_time")
    private LocalDateTime shipmentDateTime;

    public Shipment(Parcel parcel, Location sourceLocation, Location destinationLocation, LocalDateTime shipmentDateTime) {
        this.parcel = parcel;
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
        this.shipmentDateTime = shipmentDateTime;
    }

}
