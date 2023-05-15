package sm.persistance.orm;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {
    private StandardServiceRegistry registry;

    private SessionFactory sessionFactory;

    public HibernateUtils(StandardServiceRegistry registry) {
        this.registry = registry;
    }

    private SessionFactory getNewSessionFactory () {
        MetadataSources metadata = new MetadataSources(registry);
        return metadata.buildMetadata().buildSessionFactory();
    }

    public SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            sessionFactory = getNewSessionFactory();
        }
        return sessionFactory;
    }
}
