import app.DaoS.LocationDAO;
import app.DaoS.ParcelDAO;
import app.DaoS.ShipmentDAO;
import app.config.HibernateConfig;
import app.entities.Location;
import app.entities.Parcel;
import app.entities.Shipment;
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


        Populator populator = new Populator(locationDAO,parcelDAO,shipmentDAO);
        populator.populate();


        testInMain();
        emf.close();

    }


    public static void testInMain(){
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        LocationDAO locationDAO = new LocationDAO(emf);
        ParcelDAO parcelDAO = new ParcelDAO(emf);
        ShipmentDAO shipmentDAO = new ShipmentDAO(emf);

        //  Get all parcels
        List<Parcel> parcels = parcelDAO.getAll();
        System.out.println(" Parcels:");
        for (Parcel parcel : parcels) {
            System.out.println(" - ID: " + parcel.getId() + ", Tracking: " + parcel.getTrackingNumber());
        }
        //  Get all shipments
        List<Shipment> shipments = shipmentDAO.getAll();
        System.out.println("\n Shipments:");
        for (Shipment shipment : shipments) {
            System.out.println(" - ID: " + shipment.getId() +
                    ", Parcel: " + shipment.getParcel().getTrackingNumber() +
                    ", From: " + shipment.getSourceLocation().getAddress() +
                    " → To: " + shipment.getDestinationLocation().getAddress());
        }

        //  Get all locations
        List<Location> locations = locationDAO.getAll();
        System.out.println("\n Locations:");
        for (Location location : locations) {
            System.out.println(" - ID: " + location.getId() + ", Address: " + location.getAddress());
        }

        //  Get shipments for a specific parcel
        Parcel firstParcel = parcels.get(0);
        System.out.println("\n Shipments for Parcel " + firstParcel.getTrackingNumber() + ":");
        for (Shipment shipment : firstParcel.getShipments()) {
            System.out.println(" - Shipment ID: " + shipment.getId() +
                    ", From: " + shipment.getSourceLocation().getAddress() +
                    " → To: " + shipment.getDestinationLocation().getAddress());
        }
    }
}
