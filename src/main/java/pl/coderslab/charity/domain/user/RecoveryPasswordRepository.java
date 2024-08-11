package pl.coderslab.charity.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecoveryPasswordRepository extends JpaRepository<RecoveryPassword, Long> {

    Optional<RecoveryPassword> findByEmail(String email);
    Optional<RecoveryPassword> findByTokenRecoveryPassword(String token);
}
