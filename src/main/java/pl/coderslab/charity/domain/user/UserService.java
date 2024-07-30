package pl.coderslab.charity.domain.user;

import pl.coderslab.charity.infrastructure.security.JWT.UserLoginRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);
    void saveUser(RegistrationRequest user);
    void updateUser(User user, String password);
    void updateUser(User user);
    void sendRecoveryPasswordEmail(String email);
    void resetPassword(String email, String password);
    void resetPasswordForAdmin(Long id, String password);
    List<User> getAllUsers();
    List<User> getOnlyUsers();
    List<User> getOnlyAdmins();
    List<User> getUsersWithRole(String role);
    RegistrationResponse registrationUser(RegistrationRequest registrationRequest);
    ActivateUserResponse activateUser(ActivateUserRequest activateUserRequest);
    EmailCheckEmailResponse resetPasswordCheckEmail(String email);
    ResetPasswordCheckTokenResponse resetPasswordCheckToken(ResetPasswordCheckTokenRequest resetPasswordCheckTokenRequest);
    ResetPasswordCheckTokenResponse resetPassword(ResetPasswordRequest resetPasswordRequest);
    Optional<User> updateUser(Long id, UserRequest userRequest);
    Optional<User> deleteUser(Long id);
}
