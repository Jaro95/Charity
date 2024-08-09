package pl.coderslab.charity.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void saveUser(RegistrationRequest user) {
        Role userRole = roleRepository.findByName("ROLE_USER");
        userRepository.save(User.builder()
                .email(user.getEmail())
                .name(user.getFirstName())
                .lastName(user.getLastName())
                .role(new HashSet<>(Collections.singletonList(userRole)))
                .password(passwordEncoder.encode(user.getPassword()))
                .createdAccount(LocalDateTime.now())
                .enabled(false)
                .token(UUID.randomUUID().toString())
                .build());
        emailService.sendVerificationEmail(user.getEmail(), userRepository.findByEmail(user.getEmail()).orElseThrow().getToken());
    }

    @Override
    public void updatePasswordUser(User user, String password) {
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
        emailService.sendResetPassword(email, recoveryPassword.orElseThrow().getTokenRecoveryPassword());
    }

    @Override
    public void resetPassword(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        user.ifPresent(e -> {
            user.get().setPassword(passwordEncoder.encode(password));
            userRepository.save(user.get());
            deleteOccurrenceEmailInListReset(email);
            changePasswordInformation(user.get());
        });
    }

    @Override
    public void resetPasswordForAdmin(Long id, String password) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(e -> {
            user.get().setPassword(passwordEncoder.encode(password));
            userRepository.save(user.get());
            changePasswordInformation(user.get());
        });
    }

    public void changePasswordInformation(User user) {
        log.info("User {} changed password", user.getEmail());
    }

    @Override
    public List<User> getUsersWithRole(String role) {
        return userRepository.withRole(role);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getOnlyUsers() {
        return userRepository.onlyUsers();
    }

    @Override
    public List<User> getOnlyAdmins() {
        return userRepository.onlyAdmins();
    }

    @Override
    public RegistrationResponse registrationUser(RegistrationRequest registrationRequest) {
        if (!registrationRequest.getPassword().equals(registrationRequest.getRepeatPassword())) {
            return new RegistrationResponse(false, "Passwords are not the same", registrationRequest);
        }
        if (userRepository.findFirstByEmail(registrationRequest.getEmail()).isPresent()) {
            return new RegistrationResponse(false, "Email is already taken", registrationRequest);
        }
        saveUser(registrationRequest);
        log.info("Added new user:\nEmail:{}\nName:{}\nLast name:{}", registrationRequest.getEmail(),
                registrationRequest.getFirstName(), registrationRequest.getLastName());
        return new RegistrationResponse(true, "Registration successful", registrationRequest);
    }

    @Override
    public ActivateUserResponse activateUser(ActivateUserRequest activateUserRequest) {
        Optional<User> user = findByToken(activateUserRequest.token());
        if (user.isEmpty()) {
            return new ActivateUserResponse(false, "Token invalid or expired");
        }
        user.get().setEnabled(true);
        user.get().setToken("verified");
        updateUser(user.get());
        return new ActivateUserResponse(true, "The account has been activated");
    }

    @Override
    public EmailCheckEmailResponse resetPasswordCheckEmail(String email) {
        Optional<User> user = findByEmail(email);
        if (user.isEmpty()) {
            return new EmailCheckEmailResponse(false, "Wrong Email");
        }
        sendRecoveryPasswordEmail(email);
        return new EmailCheckEmailResponse(true, "A password reset link has been sent to your email address");
    }

    @Override
    public ResetPasswordCheckTokenResponse resetPasswordCheckToken(ResetPasswordCheckTokenRequest resetPasswordCheckTokenRequest) {
        Optional<RecoveryPassword> recoveryPassword = recoveryPasswordRepository.findByTokenRecoveryPassword(resetPasswordCheckTokenRequest.token());
        if (recoveryPassword.isEmpty()) {
            return new ResetPasswordCheckTokenResponse(false, "Token is invalid");
        }
        return new ResetPasswordCheckTokenResponse(true, "Token is valid");
    }

    @Override
    public ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        Optional<RecoveryPassword> recoveryPassword = recoveryPasswordRepository.findByTokenRecoveryPassword(resetPasswordRequest.token());

        if (recoveryPassword.isEmpty()) {
            return new ResetPasswordResponse(false, "Token is invalid");
        }
        if (!resetPasswordRequest.password().equals(resetPasswordRequest.repeatPassword())) {
            return new ResetPasswordResponse(false, "Passwords are not the same");
        }
        resetPassword(recoveryPassword.get().getEmail(), resetPasswordRequest.password());
        return new ResetPasswordResponse(true, "The password has been changed");
    }

    @Override
    public Optional<User> updateUser(Long id, UserRequest userRequest) {
        Optional<User> user = userRepository.findById(id);
        Set<Role> roles = new HashSet<>();
        user.ifPresent(u -> {
            Optional.ofNullable(userRequest.email()).ifPresent(email ->
                    u.setEmail(u.getEmail().equals(email) ? u.getEmail() : email)
            );
            Optional.ofNullable(userRequest.name()).ifPresent(name ->
                    u.setName(u.getName().equals(name) ? u.getName() : name)
            );
            Optional.ofNullable(userRequest.lastName()).ifPresent(lastName ->
                    u.setLastName(u.getLastName().equals(lastName) ? u.getLastName() : lastName)
            );
            Optional.ofNullable(userRequest.password()).ifPresent(password -> {
                if(!passwordEncoder.matches(password,u.getPassword())) {
                    u.setPassword(passwordEncoder.encode(password));
                }
            });
            Optional.ofNullable(userRequest.enabled()).ifPresent(enabled ->
                    u.setEnabled(u.isEnabled() == enabled ? u.isEnabled() : enabled)
            );
            Optional.ofNullable(userRequest.roleIdList())
                    .filter(roleIdList -> !roleIdList.isEmpty())
                    .ifPresent(roleIdList -> roleIdList.forEach(roleId ->
                            roles.add(roleRepository.findById(roleId).orElseThrow(IllegalArgumentException::new)))
                    );
            if(!roles.isEmpty()) {
                u.setRole(u.getRole().equals(roles) ? u.getRole() : roles);
            }
            userRepository.save(u);
            log.info("Updated user: {}", u);
        });
        return user;
    }

    @Override
    @Transactional
    public Optional<User> deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(u -> {
            u.getRole().clear();
            userRepository.delete(u);
            log.info("User deleted: {}", u);
        });
        return user;
    }

    public void deleteOccurrenceEmailInListReset(String email) {
        Optional<RecoveryPassword> validOccurrenceEmail = recoveryPasswordRepository.findByEmail(email);
        validOccurrenceEmail.ifPresent(e ->
                recoveryPasswordRepository.delete(validOccurrenceEmail.get())
        );
    }
}
