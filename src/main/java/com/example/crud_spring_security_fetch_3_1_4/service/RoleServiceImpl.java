package com.example.crud_spring_security_fetch_3_1_4.service;

import com.example.crud_spring_security_fetch_3_1_4.DAO.RoleDAO;
import com.example.crud_spring_security_fetch_3_1_4.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public Set<Role> findRoles(List<Long> roles) {
        return roleDAO.findRoles(roles);
    }
}
