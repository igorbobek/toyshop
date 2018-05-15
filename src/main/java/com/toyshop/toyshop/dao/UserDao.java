package com.toyshop.toyshop.dao;

import com.toyshop.toyshop.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserDao extends CrudRepository<User,Long>{
    User findByEmail(String email);
    User findByName(String login);
}
