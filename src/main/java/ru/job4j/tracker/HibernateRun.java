package ru.job4j.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Timestamp;
import java.util.List;

public class HibernateRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Items items = create(new Items("Learn Hibernate", "Desc", new Timestamp(System.currentTimeMillis())), sf);
            System.out.println(items);
            items.setName("Learn Hibernate 5.");
            update(items, sf);
            System.out.println(items);
            Items rsl = findById(items.getId(), sf);
            System.out.println(rsl);
            delete(rsl.getId(), sf);
            List<Items> list = findAll(sf);
            for (Items it : list) {
                System.out.println(it);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static Items create(Items item, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public static void update(Items item, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(item);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(Integer id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Items item = new Items(null, null, null);
        item.setId(id);
        session.delete(item);
        session.getTransaction().commit();
        session.close();
    }

    public static List<Items> findAll(SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Items> result = session.createQuery("from ru.job4j.tracker.Items").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static Items findById(Integer id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Items result = session.get(Items.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
