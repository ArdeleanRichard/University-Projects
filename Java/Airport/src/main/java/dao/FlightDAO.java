package dao;

import model.City;
import model.Flight;
import model.User;
import org.hibernate.*;
import util.SessionFactoryUtil;

import java.util.ArrayList;
import java.util.List;

public class FlightDAO {

    private SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();

    public Long insert(Flight flight)
    {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();

        currentSession.persist(flight);
        transaction.commit();

        return flight.getId();
    }

    public void update(Flight flight)
    {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.update(flight);
        transaction.commit();
    }

    public void delete(Flight flight)
    {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.delete(flight);
        transaction.commit();
    }

    public Flight findById(Long id)
    {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        Flight result = (Flight) currentSession.get(Flight.class, id);
        transaction.commit();
        return result;
    }

    public List<Flight> getAllData(){
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Flight> flights = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from " + Flight.class.getName());
            flights = query.list();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return flights;
    }

    public Flight findByFlightNumber(String number) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Flight> flight = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Flight WHERE number=:number");
            query.setParameter("number", number);
            flight = query.list();
            System.out.println(flight);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return flight.get(0);
    }
}
