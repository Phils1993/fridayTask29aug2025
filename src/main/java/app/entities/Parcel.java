package app.entities;


import app.annotation.VeryImportant;
import app.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@VeryImportant
@Entity
@Data
@AllArgsConstructor
@Table(name = "parcels")
@NoArgsConstructor
@Builder
@ToString

public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false, name = "tracking_number")
    private String trackingNumber;

    @Column(unique = true, nullable = false, name = "sender_name")
    private String senderName;

    @Column(unique = true, nullable = false, name = "receiver_name")
    private String receiverName;

    @Column(unique = true, nullable = false, name = "delivery_status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private LocalDate updated;

    @OneToMany(mappedBy = "parcel", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    @Builder.Default
    private List<Shipment> shipments = new ArrayList<>();


    @PreUpdate
    public void preUpdate() {
        this.updated = LocalDate.now();
    }

    @PrePersist
    public void prePersist() {
        this.updated = LocalDate.now();
    }

    // Convenience methods for bidirectional management
    public void addShipment(Shipment shipment) {
        this.shipments.add(shipment);
        if (shipment != null) {
            shipment.setParcel(this);
        }
    }

    public void removeShipment(Shipment shipment) {
        this.shipments.remove(shipment);
        if (shipment != null) {
            shipment.setParcel(null);
        }
    }


}
