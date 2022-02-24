package com.example.crud_spring_security_3_1_2.DAO;

import com.example.crud_spring_security_3_1_2.model.Role;
import com.example.crud_spring_security_3_1_2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Repository
public class UserDAOImpl implements UserDAO{
    private final PasswordEncoder bCryptPasswordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(PasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

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
        Role userRole = new Role();
        userRole.setId(2l);
        userRole.setName("ROLE_USER");
        entityManager.merge(userRole);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(userRole));
        entityManager.persist(user);
    }

    @Override
    public void update(User updatedUser, Set<Role> roles) {
        updatedUser.setRoles(roles);
        entityManager.merge(updatedUser);
    }

    @Override
    public void delete (Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }
}
