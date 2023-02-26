package ru.javawebinar.topjava.repository.jpa;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class JpaMealRepository implements MealRepository {


    @Autowired
    private SessionFactory sessionFactory;

    private Session openSession() {
        return sessionFactory.getCurrentSession();
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public Meal save(Meal meal, int userId) {
        User user = getRefUser(userId);
        if (meal.isNew()) {
            meal.setUser(user);
            em.persist(meal);
            return meal;
        } else {
            if (em.createQuery("UPDATE Meal m SET m.calories=:calories, m.dateTime=:dateTime, m.description=:description WHERE m.id=:id AND m.user=:user")
                    .setParameter("calories", meal.getCalories())
                    .setParameter("dateTime", meal.getDateTime())
                    .setParameter("description", meal.getDescription())
                    .setParameter("id", meal.getId())
                    .setParameter("user", user)
                    .executeUpdate() != 0) {
                meal.setUser(user);
                return meal;
            }
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = em.getReference(Meal.class, id);
        if (meal.getUser().getId() == userId) {
            Query query = em.createQuery("DELETE FROM Meal m WHERE m.id=:id");
            return query.setParameter("id", id).executeUpdate() != 0;
        } else return false;
    }

    @Override
    public Meal get(int id, int userId) {
        User user = getRefUser(userId);
        List<Meal> meals = em.createQuery("SELECT m FROM Meal m WHERE m.id=:id AND m.user=:user")
                .setParameter("id", id).setParameter("user", user).getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    private User getRefUser(int id) {
        return em.getReference(User.class, id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Query query = em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC");
        return query.setParameter("userId", userId).getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createQuery("SELECT m FROM Meal m WHERE m.user.id=?1 AND m.dateTime>=?2 AND m.dateTime<?3 ORDER BY m.dateTime DESC")
                .setParameter(1, userId).setParameter(2, startDateTime).setParameter(3, endDateTime)
                .getResultList();
    }
}