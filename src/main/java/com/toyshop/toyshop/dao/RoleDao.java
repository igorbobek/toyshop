package com.toyshop.toyshop.dao;

import com.toyshop.toyshop.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RoleDao  extends CrudRepository<Role,Long>{
    Role findByRole(String role);
}
