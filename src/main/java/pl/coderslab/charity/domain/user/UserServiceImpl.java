package pl.coderslab.charity.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import pl.coderslab.charity.infrastructure.security.Role;
import pl.coderslab.charity.infrastructure.security.RoleRepository;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final RecoveryPasswordRepository recoveryPasswordRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByToken(String token) {
        return userRepository.findByToken(token);
    }

    @Override
    public void saveUser(RegistrationRequest user) {
        Role userRole = roleRepository.findByName("ROLE_USER");
        userRepository.save(User.builder()
                .email(user.getEmail())
                .name(user.getFirstName())
                .lastName(user.getLastName())
                .role(new HashSet<>(Arrays.asList(userRole)))
                .password(passwordEncoder.encode(user.getPassword()))
                .createdAccount(LocalDateTime.now())
                .enabled(false)
                .token(UUID.randomUUID().toString())
                .build());
        emailService.sendVerificationEmail(user.getEmail(), userRepository.findByEmail(user.getEmail()).get().getToken());
    }

    @Override
    public void updateUser(User user, String password) {
        if (!passwordEncoder.matches(user.getPassword(), password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void sendRecoveryPasswordEmail(String email) {
        deleteOccurrenceEmailInListReset(email);
        recoveryPasswordRepository.save(RecoveryPassword.builder()
                .email(email)
                .tokenRecoveryPassword(UUID.randomUUID().toString())
                .localDateTime(LocalDateTime.now())
                .build());
        Optional<RecoveryPassword> recoveryPassword = recoveryPasswordRepository.findByEmail(email);
        emailService.sendResetPassword(email, recoveryPassword.get().getTokenRecoveryPassword());
    }

    @Override
    public void resetPassword(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        user.ifPresent(e -> {
            user.get().setPassword(passwordEncoder.encode(password));
            userRepository.save(user.get());
            deleteOccurrenceEmailInListReset(email);
            log.info("User {} changed password", user.get().getEmail());
        });
    }

    @Override
    public void resetPasswordForAdmin(Long id, String password) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(e -> {
            user.get().setPassword(passwordEncoder.encode(password));
            userRepository.save(user.get());
            log.info("User {} changed password", user.get().getEmail());
        });
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.getRole().forEach(role -> role.getUser().clear());
        }
        return users;
    }

    @Override
    public List<User> getUsersWithRole(String role) {
        List<User> users = userRepository.withRole(role);
        for (User user : users) {
            for (Role roleUser : user.getRole()) {
                roleUser.getUser().clear();
            }
        }
        return users;
    }

    @Override
    public List<User> getOnlyUsers() {
        List<User> users = userRepository.onlyUsers();
        for (User user : users) {
            for (Role role : user.getRole()) {
                role.getUser().clear();
            }
        }
        return users;
    }

    @Override
    public List<User> getOnlyAdmins() {
        List<User> users = userRepository.onlyAdmins();
        for (User user : users) {
            for (Role role : user.getRole()) {
                role.getUser().clear();
            }
        }
        return users;
    }

    @Override
    public RegistrationResponse registrationUser(RegistrationRequest registrationRequest) {
        if (!registrationRequest.getPassword().equals(registrationRequest.getRepeatPassword())) {
            return new RegistrationResponse(false,"Passwords are not the same", registrationRequest);
        }
        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            return new RegistrationResponse(false,"Email is already taken", registrationRequest);
        }
        saveUser(registrationRequest);
        log.info("Added new user:\nEmail:{}\nName:{}\nLast name:{}", registrationRequest.getEmail(),
                registrationRequest.getFirstName(),registrationRequest.getLastName());
        return new RegistrationResponse(true,"Registration successful", registrationRequest);
    }


    @Override
    public EmailCheckEmailResponse resetPasswordCheckEmail(String email) {
        Optional<User> user = findByEmail(email);
        if (user.isEmpty()) {
            return new EmailCheckEmailResponse(false,"Wrong Email");
        }
        sendRecoveryPasswordEmail(email);
        return new EmailCheckEmailResponse(true,"A password reset link has been sent to your email address");
    }

    @Override
    public ResetPasswordCheckTokenResponse resetPasswordCheckToken(ResetPasswordCheckTokenRequest resetPasswordCheckTokenRequest) {
        Optional<RecoveryPassword> recoveryPassword = recoveryPasswordRepository.findByTokenRecoveryPassword(resetPasswordCheckTokenRequest.token());
        if (recoveryPassword.isEmpty()) {
            return new ResetPasswordCheckTokenResponse(false,"Token is invalid");
        }
        return new ResetPasswordCheckTokenResponse(true, "Token is valid");
    }

    @Override
    public ResetPasswordCheckTokenResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        Optional<RecoveryPassword> recoveryPassword = recoveryPasswordRepository.findByTokenRecoveryPassword(resetPasswordRequest.token());
        if (recoveryPassword.isEmpty()) {
            return new ResetPasswordCheckTokenResponse(false,"Token is invalid");
        }
        if (!resetPasswordRequest.password().equals(resetPasswordRequest.repeatPassword())) {
            return new ResetPasswordCheckTokenResponse(false,"Passwords are not the same");
        }
        resetPassword(recoveryPassword.get().getEmail(), resetPasswordRequest.password());
        return new ResetPasswordCheckTokenResponse(true,"The password has been changed");
    }

    public void deleteOccurrenceEmailInListReset(String email) {
        Optional<RecoveryPassword> validOccurrenceEmail = recoveryPasswordRepository.findByEmail(email);
        validOccurrenceEmail.ifPresent( e ->
                recoveryPasswordRepository.delete(validOccurrenceEmail.get())
        );
    }
}
