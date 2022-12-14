package com.brightstraining.springbootblogapplication.service;

import com.brightstraining.springbootblogapplication.model.Authority;
import com.brightstraining.springbootblogapplication.model.UserAccount;
import com.brightstraining.springbootblogapplication.repository.AuthorityRepository;
import com.brightstraining.springbootblogapplication.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public List<UserAccount> getAllUsers() {
        return userAccountRepository.findAll();
    }

    @Override
    public void saveUser(UserAccount userAccount) {


        if (userAccount.getId() == null) {
            if (userAccount.getAuthorities().isEmpty()) {
                Set<Authority> authorities = new HashSet<>();
                authorityRepository.findById("ROLE_USER").ifPresent(authorities::add);
                userAccount.setAuthorities(authorities);
            }
        }
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        this.userAccountRepository.save(userAccount);
    }

    @Override
    public UserAccount getUser(long id) {
        //creating this in case there is no value for id field
        Optional<UserAccount> optional = this.userAccountRepository.findById(id);
        UserAccount userAccount = null;

        if(optional.isPresent()) {
            userAccount = optional.get();
        } else {
            throw new RuntimeException("User with that id " + id + " was not found");
        }
        return userAccount;
    }

    @Override
    public void deleteUserById(long id) {
        // add exception here for checking if incorrect id is entered
        boolean exists = this.userAccountRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("User with that id " + id + " was not found.");
        }
        //delete employee if exists
        this.userAccountRepository.deleteById(id);

    }
    public Optional<UserAccount> findOneByEmail(String email) {
        return userAccountRepository.findOneByEmail(email);
    }


}