package com.example.crud_spring_security_3_1_3.DAO;

import com.example.crud_spring_security_3_1_3.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDAOImpl implements RoleDAO{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Role> findRoles(List<Long> roles) {
        String HQL = "SELECT r FROM Role r WHERE r.id IN :rolesIds";
        TypedQuery<Role> query = entityManager.createQuery(HQL, Role.class);
        query.setParameter("rolesIds", roles);
        return new HashSet<>(query.getResultList());
    }
}
