package com.innovationm.hotel.service;

import com.innovationm.hotel.dao.LocationDao;
import com.innovationm.hotel.dao.UserDao;
import com.innovationm.hotel.model.primary.User;
import com.innovationm.hotel.model.secondary.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private LocationDao locationDao;

    @Transactional
    public User addUser(User user)
    {
        Location location=user.getLocation();
        locationDao.save(location);
        return userDao.save(user);
    }
    public List<User> getAllUsers()
    {
        return (List<User>) userDao.findAll();
    }
    public User getUserById(long id)
    {
        return userDao.findById(id).get();
    }
    public User getUserByUserName(String email)
    {
        User user=userDao.findByEmail(email).get();
        return user;
    }
    public User updateUser(User user,long id)
    {
        user.setId(id);
        return addUser(user);
    }
    public void deleteUser(long id)
    {
        userDao.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user;
        try {
                user=userDao.findByEmail(userName).get();
            }
        catch(org.springframework.dao.EmptyResultDataAccessException e)
        {
            throw new UsernameNotFoundException("not found");
        }
        catch(java.util.NoSuchElementException e)
        {
            throw new UsernameNotFoundException("User with EmailId:"+ userName+" does not exist\nPlese SignUp First");
            //	 throw new RestApiException(new RestApiError(RestApiHttpStatus.OK)).developerMessage("User with EmailId:"+ username+" does not exist\nPlese SignUp First");
        }
        String pass=user.getPass();
        Set<GrantedAuthority> authority= Collections.singleton(new SimpleGrantedAuthority("USER"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),pass, authority);
    }
}
