package ru.stepup.demohikari.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepup.demohikari.Entity.User;
import ru.stepup.demohikari.Dao.UserDao;

import java.util.List;

@Service
public class UserService {

    private UserDao userDao;
    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public long save (User user) {
        return userDao.save(user);
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public User getUser(long id) {
        return userDao.getUser(id);
    }

    public void updateUser(long id, String name) {
        userDao.updateUser(id, name);
    }

    public void deleteAll() {
        userDao.deleteAll();
    }
}


