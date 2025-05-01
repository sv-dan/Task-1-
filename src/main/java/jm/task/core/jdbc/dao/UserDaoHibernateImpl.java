package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(SQLqueries.CREATE_TABLE).executeUpdate();
            transaction.commit();
            log.info("Таблица успешна создана");
        } catch (Exception ex) {
            if (transaction != null) transaction.rollback();
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(SQLqueries.DROP_TABLE).executeUpdate();
            log.info("Таблица успешно удалена");
        } catch (Exception ex) {
            if (transaction != null) transaction.rollback();
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            long id = user.getId();
            session.getTransaction().commit();
            log.info("User с именем {} добавлен в базу данных c id {}",  name, id);
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            log.info("Пользователь с id {} был успешно удален", id);
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            users = (ArrayList<User>) session.createQuery("FROM User", User.class).getResultList();
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
        if (!users.isEmpty()) {
            log.info("Содержимое таблицы: ");
            for (User user : users) {
                log.info("Пользователь: ", user);
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE public.users RESTART IDENTITY ").executeUpdate();
            session.getTransaction().commit();
            log.info("Таблица успешно очищена");
        } catch (Exception ex) {
            if (transaction != null) transaction.rollback();
            throw new CustomException(ex.getMessage());
        }
    }
}
