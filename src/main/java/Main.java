import app.DaoS.LocationDAO;
import app.DaoS.ParcelDAO;
import app.DaoS.ShipmentDAO;
import app.config.HibernateConfig;
import app.entities.Parcel;
import app.records.ParcelDTO;
import app.services.Populator;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // I opgaven behøves der ikke en DB i pgadmin, fordi der skal kun laves metoder og teste de metoder.
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        ParcelDAO parcelDAO = new ParcelDAO(emf);
        LocationDAO locationDAO = new LocationDAO(emf);
        ShipmentDAO shipmentDAO = new ShipmentDAO(emf);


        List<ParcelDTO> parcels = Populator.populate(parcelDAO, locationDAO, shipmentDAO);
        parcels.forEach(System.out::println);

        Parcel parcelToUpdate = parcelDAO.getById(1);
        parcelToUpdate.setSenderName("Brian");
        Parcel updatedParcel = parcelDAO.update(parcelToUpdate);
        System.out.println("Updated parcel: " + updatedParcel.getSenderName());

        // test af metode i main for sjov :-)
        //dao.createParcel(new Parcel("tracking34567"," Philip", "Møffe", DeliveryStatus.PENDING, LocalDate.now()));

        emf.close();

    }
}
