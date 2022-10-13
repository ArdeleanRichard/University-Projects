package dao;

import model.City;
import model.User;
import org.hibernate.*;
import util.SessionFactoryUtil;

import java.util.ArrayList;
import java.util.List;

public class CityDAO {


    private SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();

    public Long insert(City city)
    {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();

        currentSession.persist(city);
        transaction.commit();

        return city.getId();
    }

    public void update(City city)
    {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.update(city);
        transaction.commit();
    }

    public void delete(City city)
    {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.delete(city);
        transaction.commit();
    }

    public City findById(Long id)
    {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        City result = (City) currentSession.get(City.class, id);
        transaction.commit();
        return result;
    }

    public City findByName(String name) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<City> city = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM City WHERE name=:name");
            query.setParameter("name", name);
            city = query.list();
            System.out.println(city);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return city.get(0);
    }
}
