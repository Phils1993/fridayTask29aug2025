import app.config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Hibernate;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        
    }
}
