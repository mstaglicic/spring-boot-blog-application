package com.brightstraining.springbootblogapplication.service;

import com.brightstraining.springbootblogapplication.model.UserAccount;

import java.util.List;
import java.util.Optional;

public interface UserAccountService {
    List<UserAccount> getAllUsers();
    void saveUser(UserAccount userAccount);
    UserAccount getUser(long id);
    void deleteUserById(long id);

    Optional<UserAccount> findOneByEmail(String email);

}
