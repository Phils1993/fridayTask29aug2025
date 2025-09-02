import app.DaoS.ParcelDAO;
import app.config.HibernateConfig;
import app.entities.Parcel;
import app.enums.DeliveryStatus;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Hibernate;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        // I opgaven behøves der ikke en DB i pgadmin, fordi der skal kun laves metoder og teste de metoder.
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        ParcelDAO dao = new ParcelDAO(emf);

        // test af metode i main for sjov :-)
        dao.createParcel(new Parcel("tracking34567"," Philip", "Møffe", DeliveryStatus.PENDING, LocalDate.now()));

        emf.close();
        
    }
}
