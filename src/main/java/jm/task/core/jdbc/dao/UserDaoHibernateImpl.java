package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        Util util = new Util();
        sessionFactory = util.connectHibernate();
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS Person"
                    + "(id INTEGER not NULL AUTO_INCREMENT, "
                    + " firstName VARCHAR(50), "
                    + " lastName VARCHAR(50), "
                    + " age INTEGER, "
                    + " PRIMARY KEY ( id ))").executeUpdate();
            transaction.commit();
        } catch (Exception exception)  {
            exception.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS Person").executeUpdate();
            transaction.commit();
        } catch (Exception exception)  {
            exception.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = sessionFactory.openSession()) {
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
        } catch (Exception exception)  {
            exception.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction t = session.beginTransaction();
            User user = new User();
            user.setId(id);
            session.delete(user);
            t.commit();
        } catch (Exception exception)  {
            exception.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<User> query = session.createQuery("SELECT y FROM User y", User.class);
            list = query.getResultList();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction t = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            t.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

//Привет! @Игорь Рыжаков помоги пожалуйста понять обратную связь проверяющего по задаче  https://platform.kata.academy/fake/user/13038/courses/2/1/1/5