package app.entities;


import app.InterFaces.Prepersist;
import app.InterFaces.Preupdate;
import app.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parcel implements Preupdate, Prepersist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false,name = "tracking_number")
    private String trackingNumber;

    @Column(unique = true, nullable = false, name = "sender_name")
    private String senderName;

    @Column(unique = true, nullable = false, name = "receiver_name")
    private String receiverName;

    @Column(unique = true, nullable = false, name = "delivery_status")
    private DeliveryStatus deliveryStatus;

    private LocalDate updated;

    @PreUpdate
    public void preUpdate() {
        this.updated = LocalDate.now();
    }

    @PrePersist
    public void prePersist() {
        this.updated = LocalDate.now();
    }



}
