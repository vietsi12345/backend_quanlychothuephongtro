package com.zosh.task.user.service.service;

import com.zosh.task.user.service.modal.User;

import java.util.List;

public interface UserService {
    public User getUserProfile (String jwt);

    public List<User> getAllUser ();

    public User  newPassword( String jwt, String pwNew);
    User getUserById (Long id) throws Exception;

    List <User> GetAllStaffOrCustomer(String role);

    User cancelUser (Long id) throws Exception;
    User addUser (User user) ;
}
