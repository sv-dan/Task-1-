package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;




import java.util.List;
@Slf4j
public class UserDaoHibernateImpl implements UserDao {


    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String createTable = SQLqueries.CREATE_TABLE;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(createTable).executeUpdate();
            transaction.commit();
            log.info("Таблица создана");
        } catch (CustomException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new CustomException(e.getMessage());
        }
    }


    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        String dropTable = SQLqueries.DROP_TABLE;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(dropTable).executeUpdate();
            transaction.commit();
            log.info("Таблица удалена");
        } catch (CustomException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new CustomException(e.getMessage());
        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            transaction.commit();
            long id = user.getId();
            log.info("User с именем " + user + " добавлен в базу данных id " + id);
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }


    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
                transaction.commit();
                log.info("User с id " + id + " удален");
            } else {
                transaction.rollback();
                log.info("User с id " + id + " не найден");
            }
        }catch (CustomException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new CustomException(e.getMessage());
        }

    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return  session.createQuery("FROM User", User.class).getResultList();
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            int result = session.createNativeQuery("DELETE FROM users").executeUpdate();
            transaction.commit();
            log.info("Удалено " + result + " записей");
        } catch (CustomException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new CustomException(e.getMessage());
        }

    }
}
