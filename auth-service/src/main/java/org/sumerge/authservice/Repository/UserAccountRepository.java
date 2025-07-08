package org.sumerge.authservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sumerge.authservice.Entity.UserAccount;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
    Optional<UserAccount> findByEmail(String email);
}

