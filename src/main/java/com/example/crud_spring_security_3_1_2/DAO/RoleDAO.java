package com.example.crud_spring_security_3_1_2.DAO;

import com.example.crud_spring_security_3_1_2.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDAO {
    Set<Role> findRoles(List<Long> roles);
}
