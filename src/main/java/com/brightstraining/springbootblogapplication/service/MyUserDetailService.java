package com.brightstraining.springbootblogapplication.service;

import com.brightstraining.springbootblogapplication.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Component("userDetailService")

public class MyUserDetailService implements UserDetailsService {

    //Overrides default spring security UserDetails as we will use the email as authenticator for login
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserAccount> optionalAccount = userAccountService.findOneByEmail(email);
        if(!optionalAccount.isPresent()) {
            throw new UsernameNotFoundException("Account not found");
        }
        UserAccount userAccount = optionalAccount.get();

        List<GrantedAuthority> grantedAuthorities = userAccount
                .getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());



        return new org.springframework.security.core.userdetails.User(userAccount.getEmail(), userAccount.getPassword(), grantedAuthorities);

    }

    @Autowired
    private UserAccountService userAccountService;

}
