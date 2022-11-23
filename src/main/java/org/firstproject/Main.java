package org.firstproject;

import org.firstproject.model.entity.User;
import org.firstproject.util.HibernateUtil;
import org.hibernate.Session;

public class Main {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // open transaction
        session.getTransaction().begin();

        User user = new User();
        user.setUserName("Andriy");
        user.setUserLastName("Kolokolov");
        user.setUserAge((byte) 26);

        //save() and persist() are similar, but still different.
        // save() immediately persist the entity and returns the generated ID.
        // persist() just marks the entity for insertion. The ID, depending on the identifier generator,
        // may be generated asynchronously, for example when the session is flushed.

        // prepare object to commit
        session.persist(user);

        // commit/save changes (transaction closes)
        session.getTransaction().commit();

        // close session
        session.close();

        // close Session Factory
        HibernateUtil.close();

    }
}