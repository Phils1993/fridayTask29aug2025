package app.entities;


import app.annotation.VeryImportant;
import app.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@VeryImportant
@Entity
@Data
@AllArgsConstructor
@Table(name = "parcels")
@NoArgsConstructor

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

    @OneToMany(mappedBy = "parcel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shipment> shipments = new ArrayList<>();

    public Parcel(String trackingNumber, String senderName, String receiverName, DeliveryStatus deliveryStatus, LocalDate updated) {
        this.trackingNumber = trackingNumber;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.deliveryStatus = deliveryStatus;
        this.updated = updated;
    }

    @PreUpdate
    public void preUpdate() {
        this.updated = LocalDate.now();
    }

    @PrePersist
    public void prePersist() {
        this.updated = LocalDate.now();
    }

    // Convenience methods for bidirectional mangement
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
