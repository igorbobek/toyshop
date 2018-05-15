package com.toyshop.toyshop.service;


import com.toyshop.toyshop.model.User;

public interface UserService {

    void save(User user);
    User findById(Long id);
    User findByName(String name);
    User findByEmail(String email);
    void update(User user);

}
