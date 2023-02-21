package org.firstproject.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

// hibernate initialization
public final class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private HibernateUtil() {
        // private constructor to prevent instantiation
    }

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .configure(new File("src/main/resources/hibernate.cfg.xml"))
                    .buildSessionFactory();
        } catch (HibernateException e) {
            System.err.println("SessionFactory creation failed. ");
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (SESSION_FACTORY == null) {
            return buildSessionFactory();
        }
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
