package org.example.jfxhibernate;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 * Utilidad para gestionar la configuración de Hibernate y la creación de la fábrica de sesiones.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration()
                .configure()
                .setProperty("hibernate.connection.password", System.getenv("hibernate_password"))
                .setProperty("hibernate.connection.username", System.getenv("hibernate_username"))
                .buildSessionFactory();
    }

    /**
     * Obtiene la fábrica de sesiones de Hibernate.
     *
     * @return la fábrica de sesiones de Hibernate.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
