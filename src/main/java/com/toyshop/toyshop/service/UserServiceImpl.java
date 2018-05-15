package com.toyshop.toyshop.service;

import com.toyshop.toyshop.dao.RoleDao;
import com.toyshop.toyshop.dao.UserDao;
import com.toyshop.toyshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;

    @Override
    public void save(User user) {
        user.getRoles().add(roleDao.findByRole("USER"));
        userDao.save(user);
    }

    @Override
    public void update(User user){
        userDao.save(user);
    }

    @Nullable
    @Override
    public User findById(Long id) {
        if(userDao.findById(id).isPresent()){
            return userDao.findById(id).get();
        }
        return null;
    }

    @Nullable
    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }

    @Secured("ROLE_ADMIN")
    @Nullable
    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
