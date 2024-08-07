package pl.coderslab.charity.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findFirstByEmail(String email);
    Optional<User> findByToken(String token);

    @Query("SELECT u FROM User u JOIN FETCH u.role r WHERE r.name = 'ROLE_ADMIN'")
    List<User> onlyAdmins();

    @Query("SELECT u FROM User u JOIN FETCH u.role r GROUP BY u HAVING COUNT(r) = 1")
    List<User> onlyUsers();

    @Query("SELECT u FROM User u JOIN FETCH u.role r where r.name = ?1")
    List<User> withRole(String role);
}
