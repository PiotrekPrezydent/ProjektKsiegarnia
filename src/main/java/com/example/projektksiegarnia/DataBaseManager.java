package com.example.projektksiegarnia;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Obsługa Hibernate dla aplikacji
 * kalsa ta odpowiada za inicjalizacje i udostepnanie dostepu
 * {@link SessionFactory} ktore sa uzywane w bazie danych
 */

public class DataBaseManager {
    /**
     *  pojedyncza instancja SessionFactory
     */
    private static final SessionFactory sessionFactory = buildSessionFactory();
    /**
     * inicjalizuje SesssionFactory czyli interface hibernate potrzebny do interakcji z baza
     *  @return zainicjalizowany {@link SessionFactory}
     *  @throws ExceptionInInitializerError jeśli tworzenie {@link SessionFactory} nie powiedzie się
     */
    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            System.out.println(ex.getCause().getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }
    /**
     *  pojedyncza instancja SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    /**
     *  zamyka i zwalnia zasoby
     */
    public static void shutdown() {
        getSessionFactory().close();
    }
}