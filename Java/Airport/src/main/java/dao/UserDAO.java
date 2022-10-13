package dao;

import model.User;
import org.hibernate.*;
import util.SessionFactoryUtil;

import java.util.List;


public class UserDAO {

    private SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();

    public Long insert(User user)
    {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();

        currentSession.persist(user);
        transaction.commit();

        return user.getId();
    }

    public void update(User user)
    {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.update(user);
        transaction.commit();
    }

    public void delete(User user)
    {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.delete(user);
        transaction.commit();
    }

    public User findById(Long id)
    {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        User result = (User) currentSession.get(User.class, id);
        transaction.commit();
        return result;
    }

    public User getUser(String username, String password) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<User> users = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM User WHERE username = :username AND password = :password");
            query.setParameter("username", username);
            query.setParameter("password", password);
            users = query.list();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return users != null && !users.isEmpty() ? users.get(0) : null;
    }
}
