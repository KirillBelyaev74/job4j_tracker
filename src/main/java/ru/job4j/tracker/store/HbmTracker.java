package ru.job4j.tracker.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.tracker.item.Items;

import java.util.List;

public class HbmTracker {

    private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private static final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public Items add(Items items) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(items);
            session.getTransaction().commit();
        }
        return items;
    }

    public boolean replace(String id, Items items) {
        int result;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("update Items set name =: name, created =: created, description =: description where id =: id");
            query.setParameter("name", items.getName());
            query.setParameter("created", items.getCreated());
            query.setParameter("description", items.getDescription());
            query.setParameter("id", Integer.parseInt(id));
            result = query.executeUpdate();
            session.getTransaction().commit();
        }
        return result == 1;
    }

    public boolean delete(String id) {
        int result;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("delete Items where id =: id");
            query.setParameter("id", Integer.parseInt(id));
            result = query.executeUpdate();
            session.getTransaction().commit();
        }
        return result == 1;
    }

    public List<Items> findAll() {
        List<Items> items;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            items = session.createQuery("from Items order by id").list();
            session.getTransaction().commit();
        }
        return items;
    }

    public List<Items> findByName(String key) {
        List<Items> items;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Items where name =: name order by id");
            items = query.setParameter("name", key).list();
            session.getTransaction().commit();
        }
        return items;
    }

    public Items findById(String id) {
        Items items;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            items = session.get(Items.class, Integer.parseInt(id));
            session.getTransaction().commit();
        }
        return items;
    }

    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
        sf.close();
    }
}
