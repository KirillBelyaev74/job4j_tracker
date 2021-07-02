package ru.job4j.tracker.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.job4j.tracker.item.Items;

import java.util.List;
import java.util.function.Function;

public class HbmTracker {

    private static class InstanceSessionFactory {
        private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
    }

    private static SessionFactory getInstance() {
        return InstanceSessionFactory.SESSION_FACTORY;
    }

    public <T> T action(Function<Session, T> action) {
        T t;
        try (Session session = getInstance().openSession()) {
            session.beginTransaction();
            t = action.apply(session);
            session.getTransaction().commit();
        }
        return t;
    }

    public Items add(Items items) {
        try (Session session = getInstance().openSession()) {
            session.beginTransaction();
            session.save(items);
            session.getTransaction().commit();
        }
        return items;
    }

    public boolean replace(String id, Items items) {
        int result;
        try (Session session = getInstance().openSession()) {
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
        try (Session session = getInstance().openSession()) {
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
        try (Session session = getInstance().openSession()) {
            session.beginTransaction();
            items = session.createQuery("from Items order by id").list();
            session.getTransaction().commit();
        }
        return items;
    }

    public List<Items> findByName(String key) {
        List<Items> items;
        try (Session session = getInstance().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Items where name =: name order by id");
            items = query.setParameter("name", key).list();
            session.getTransaction().commit();
        }
        return items;
    }

    public Items findById(String id) {
        Items items;
        try (Session session = getInstance().openSession()) {
            session.beginTransaction();
            items = session.get(Items.class, Integer.parseInt(id));
            session.getTransaction().commit();
        }
        return items;
    }

    public boolean clearTable() {
        int result = 0;
        try (Session session = getInstance().openSession()) {
            session.beginTransaction();
            Query<Items> query = session.createQuery("delete from Items");
            result = query.executeUpdate();
        }
        return result != 0;
    }
}
