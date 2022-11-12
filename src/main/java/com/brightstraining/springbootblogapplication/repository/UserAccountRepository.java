package com.brightstraining.springbootblogapplication.repository;

import com.brightstraining.springbootblogapplication.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findOneByEmail(String email);

}
