package dao;

import model.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.SessionFactoryUtil;

public class AdminDAO {

    private SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();

    public Long insert(Admin admin)
    {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();

        currentSession.persist(admin);
        transaction.commit();

        return admin.getId();
    }

    public void update(Admin admin)
    {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.update(admin);
        transaction.commit();
    }

    public void delete(Admin admin)
    {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.delete(admin);
        transaction.commit();
    }

    public Admin findById(Long id)
    {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        Admin result = (Admin) currentSession.get(Admin.class, id);
        transaction.commit();
        return result;
    }

    public Admin findByName(String name) {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        Admin result = (Admin) currentSession.get(Admin.class, name);
        transaction.commit();
        return result;
    }
}
