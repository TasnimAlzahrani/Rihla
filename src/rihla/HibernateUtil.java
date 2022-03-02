/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author randah
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // loads configuration and mappings
            Configuration configuration = new Configuration().configure("/rihla/hibernate.cfg.xml");
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            configuration.addAnnotatedClass(Student.class);

            configuration.addAnnotatedClass(Driver.class);
            configuration.addAnnotatedClass(Bus.class);
            configuration.addAnnotatedClass(Notification.class);
            configuration.addAnnotatedClass(Neighborhood.class);
            configuration.addAnnotatedClass(MissingItem.class);
            configuration.addAnnotatedClass(ServedNeighborhood.class);
            configuration.addAnnotatedClass(TripsSchedule_fromCampus.class);
            configuration.addAnnotatedClass(TripsSchedule_toCampus.class);
            configuration.addAnnotatedClass(Student.class);
            configuration.addAnnotatedClass(StudentAddress.class);
            configuration.addAnnotatedClass(CardInformation.class);
            configuration.addAnnotatedClass(Schedule_of_student.class);

            // builds a session factory from the service registry
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
