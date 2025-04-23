package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoHibernateImpl.class);

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
            logger.info("Таблица создана");
        } catch (CustomException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
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
            logger.info("Таблица удалена");
        } catch (CustomException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
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
            logger.info("User с именем " + user + " добавлен в базу данных id " + id);
        } catch (CustomException e) {
            e.printStackTrace();
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
                logger.info("User с id " + id + " удален");
            } else {
                transaction.rollback();
                logger.info("User с id " + id + " не найден");
            }
        }catch (CustomException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.info("Произошла ошибка при удаление пользователя: " + e.getMessage());
        }

    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return  session.createQuery("FROM User", User.class).getResultList();
        } catch (CustomException e) {
            logger.info("Ошибка при получении пользователей: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            int result = session.createNativeQuery("DELETE FROM users").executeUpdate();
            transaction.commit();
            logger.info("Удалено " + result + " записей");
        } catch (CustomException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.info("Ошибка при очистке таблицы: " + e.getMessage());
        }

    }
}
