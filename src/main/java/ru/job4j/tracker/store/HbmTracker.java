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

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public Items add(Items items) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(items);
        session.getTransaction().commit();
        session.close();
        return items;
    }

    public boolean replace(String id, Items items) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("update Items set name =: name, created =: created, description =: description where id =: id");
        query.setParameter("name", items.getName());
        query.setParameter("created", items.getCreated());
        query.setParameter("description", items.getDescription());
        query.setParameter("id", Integer.parseInt(id));
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return result == 1;
    }

    public boolean delete(String id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("delete Items where id =: id");
        query.setParameter("id", Integer.parseInt(id));
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return result == 1;
    }

    public List<Items> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Items> items = session.createQuery("from Items order by id").list();
        session.getTransaction().commit();
        session.close();
        return items;
    }

    public List<Items> findByName(String key) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Items where name =: name order by id");
        List<Items> items = query.setParameter("name", key).list();
        session.getTransaction().commit();
        session.close();
        return items;
    }

    public Items findById(String id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Items items = session.get(Items.class, Integer.parseInt(id));
        session.getTransaction().commit();
        session.close();
        return items;
    }

    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
        sf.close();
    }
}
