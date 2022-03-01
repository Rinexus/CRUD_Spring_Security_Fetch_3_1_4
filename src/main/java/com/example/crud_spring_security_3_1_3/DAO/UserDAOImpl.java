package com.example.crud_spring_security_3_1_3.DAO;

import com.example.crud_spring_security_3_1_3.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsers(){
         return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User getUser(Long id) {
        String HQL = "SELECT u FROM User u WHERE u.id =: userId";
        return entityManager.createQuery(HQL, User.class)
                .setParameter("userId", id)
                .getSingleResult();
    }

    @Override
    public User getUserByName(String username) {
        String HQL = "SELECT u FROM User u WHERE u.username =: username";
        return entityManager.createQuery(HQL, User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public void save(User user){
        entityManager.persist(user);
    }

    @Override
    public void update(User updatedUser) {
        entityManager.merge(updatedUser);
    }

    @Override
    public void delete (Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }
}
