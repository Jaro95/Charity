package pl.coderslab.charity.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.coderslab.charity.domain.dataFactory.TestDataFactory;
import pl.coderslab.charity.infrastructure.security.Role;
import pl.coderslab.charity.infrastructure.security.RoleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private RecoveryPasswordRepository recoveryPasswordRepository;

    @InjectMocks
    private UserServiceImpl userService;
    private User user;

    @BeforeEach
    void setUp() {
        user = TestDataFactory.createUser();
    }

    @Test
    void findByEmail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Optional<User> findUser = userService.findByEmail(user.getEmail());

        assertTrue(findUser.isPresent());
        assertEquals(user, findUser.get());
    }

    @Test
    void findByToken() {
        when(userRepository.findByToken(user.getToken())).thenReturn(Optional.of(user));

        Optional<User> findUser = userService.findByToken(user.getToken());

        assertTrue(findUser.isPresent());
        assertEquals(user, findUser.get());
    }

    @Test
    void testSaveUser() {
        RegistrationRequest addUser = TestDataFactory.addUser();
        when(roleRepository.findByName("ROLE_USER")).thenReturn(TestDataFactory.createRole());
        when(passwordEncoder.encode(addUser.getPassword())).thenReturn(addUser.getPassword());
        when(userRepository.findByEmail(addUser.getEmail())).thenReturn(Optional.of(user));

        userService.saveUser(addUser);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser() {
        userService.updateUser(user);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdatePasswordUser() {
        when(passwordEncoder.matches(user.getPassword(), "password")).thenReturn(false);

        userService.updatePasswordUser(user, "password");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testSendRecoveryPasswordEmail() {
        RecoveryPassword recoveryPassword = TestDataFactory.createRecoveryPassword();
        when(recoveryPasswordRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(recoveryPassword));

        userService.sendRecoveryPasswordEmail(user.getEmail());

        verify(recoveryPasswordRepository, times(1)).save(any(RecoveryPassword.class));
    }

    @Test
    void resetPassword() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        userService.resetPassword(user.getEmail(), TestDataFactory.addUser().getPassword());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> userList = userService.getAllUsers();

        assertEquals(user, userList.get(0));
    }

    @Test
    void testGetUsersWithRole() {
        Role role = TestDataFactory.createRole();
        when(userRepository.withRole(role.getName())).thenReturn(List.of(user));

        List<User> userList = userService.getUsersWithRole(role.getName());

        assertEquals(user, userList.get(0));
    }

    @Test
    void testRegistrationWrongPasswordUser() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .email("u@u")
                .firstName("name")
                .lastName("lastName")
                .password("Test123!")
                .repeatPassword("Test12")
                .build();
        RegistrationResponse registrationResponse = new RegistrationResponse(false, "Passwords are not the same", registrationRequest);

        RegistrationResponse userRegistrationResponse = userService.registrationUser(registrationRequest);

        assertNotNull(userRegistrationResponse);
        assertEquals(registrationRequest, userRegistrationResponse.registrationRequest());
        assertEquals(registrationResponse.message(), userRegistrationResponse.message());
        assertEquals(registrationResponse.successful(), userRegistrationResponse.successful());
    }

    @Test
    void testRegistrationOccupationEmailUser() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .email("u@u")
                .firstName("name")
                .lastName("lastName")
                .password("Test123!")
                .repeatPassword("Test123!")
                .build();
        RegistrationResponse registrationResponse = new RegistrationResponse(false, "Email is already taken", registrationRequest);
        when(userRepository.findFirstByEmail(registrationRequest.getEmail())).thenReturn(Optional.of(user));

        RegistrationResponse userRegistrationResponse = userService.registrationUser(registrationRequest);

        assertNotNull(userRegistrationResponse);
        assertEquals(registrationRequest, userRegistrationResponse.registrationRequest());
        assertEquals(registrationResponse.message(), userRegistrationResponse.message());
        assertEquals(registrationResponse.successful(), userRegistrationResponse.successful());
    }


    @Test
    void testRegistrationSuccessfulUser() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .email("u@u")
                .firstName("name")
                .lastName("lastName")
                .password("Test123!")
                .repeatPassword("Test123!")
                .build();
        RegistrationResponse registrationResponse = new RegistrationResponse(true, "Registration successful", registrationRequest);
        when(userRepository.findFirstByEmail(registrationRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(registrationRequest.getEmail())).thenReturn(Optional.of(user));

        RegistrationResponse userRegistrationResponse = userService.registrationUser(registrationRequest);

        assertNotNull(userRegistrationResponse);
        assertEquals(registrationRequest, userRegistrationResponse.registrationRequest());
        assertEquals(registrationResponse.message(), userRegistrationResponse.message());
        assertEquals(registrationResponse.successful(), userRegistrationResponse.successful());
    }

    @Test
    void activateWrongTokenUser() {
        ActivateUserRequest activateUserRequest = new ActivateUserRequest("token");
        ActivateUserResponse activateUserResponse = new ActivateUserResponse(false, "Token invalid or expired");
        when(userRepository.findByToken(activateUserRequest.token())).thenReturn(Optional.empty());

        ActivateUserResponse tokenActivateUserResponse = userService.activateUser(activateUserRequest);

        assertNotNull(tokenActivateUserResponse);
        assertEquals(activateUserResponse, tokenActivateUserResponse);
    }

    @Test
    void activateSuccessUser() {
        ActivateUserRequest activateUserRequest = new ActivateUserRequest("token");
        ActivateUserResponse activateUserResponse = new ActivateUserResponse(true, "The account has been activated");
        when(userRepository.findByToken(activateUserRequest.token())).thenReturn(Optional.of(user));

        ActivateUserResponse tokenActivateUserResponse = userService.activateUser(activateUserRequest);

        assertNotNull(tokenActivateUserResponse);
        assertEquals(activateUserResponse, tokenActivateUserResponse);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void resetPasswordCheckWrongEmail() {
        String email = "wrongEmail";
        EmailCheckEmailResponse emailCheckEmailResponse = new EmailCheckEmailResponse(false, "Wrong Email");
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        EmailCheckEmailResponse emailCheckValidEmailResponse = userService.resetPasswordCheckEmail(email);

        assertNotNull(emailCheckEmailResponse);
        assertEquals(emailCheckEmailResponse, emailCheckValidEmailResponse);
    }

    @Test
    void resetPasswordCheckCorrectEmail() {
        String email = user.getEmail();
        EmailCheckEmailResponse emailCheckEmailResponse = new EmailCheckEmailResponse(true, "A password reset link has been sent to your email address");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(recoveryPasswordRepository.findByEmail(email)).thenReturn(Optional.of(getRecoveryPassword()));

        EmailCheckEmailResponse emailCheckValidEmailResponse = userService.resetPasswordCheckEmail(email);

        assertNotNull(emailCheckEmailResponse);
        assertEquals(emailCheckEmailResponse, emailCheckValidEmailResponse);
        verify(recoveryPasswordRepository, times(1)).delete(any(RecoveryPassword.class));
        verify(recoveryPasswordRepository, times(1)).save(any(RecoveryPassword.class));
    }

    @Test
    void resetPasswordCheckWrongToken() {
        ResetPasswordCheckTokenRequest resetPasswordCheckTokenRequest = new ResetPasswordCheckTokenRequest("token");
        ResetPasswordCheckTokenResponse resetPasswordCheckTokenResponse = new ResetPasswordCheckTokenResponse(false, "Token is invalid");
        when(recoveryPasswordRepository.findByTokenRecoveryPassword(resetPasswordCheckTokenRequest.token())).thenReturn(Optional.empty());

        ResetPasswordCheckTokenResponse resetPasswordCheckValidTokenResponse = userService.resetPasswordCheckToken(resetPasswordCheckTokenRequest);

        assertNotNull(resetPasswordCheckTokenResponse);
        assertEquals(resetPasswordCheckTokenResponse, resetPasswordCheckValidTokenResponse);
    }

    @Test
    void resetPasswordCheckCorrectToken() {
        ResetPasswordCheckTokenRequest resetPasswordCheckTokenRequest = new ResetPasswordCheckTokenRequest("token");
        ResetPasswordCheckTokenResponse resetPasswordCheckTokenResponse = new ResetPasswordCheckTokenResponse(true, "Token is valid");
        when(recoveryPasswordRepository.findByTokenRecoveryPassword(resetPasswordCheckTokenRequest.token())).thenReturn(Optional.of(getRecoveryPassword()));

        ResetPasswordCheckTokenResponse resetPasswordCheckValidTokenResponse = userService.resetPasswordCheckToken(resetPasswordCheckTokenRequest);

        assertNotNull(resetPasswordCheckTokenResponse);
        assertEquals(resetPasswordCheckTokenResponse, resetPasswordCheckValidTokenResponse);
    }

    @Test
    void testResetPasswordInvalidToken() {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest("invalidToken", "Testy123!", "Testy123!");

        ResetPasswordResponse resetPasswordResponse = new ResetPasswordResponse(false, "Token is invalid");
        when(recoveryPasswordRepository.findByTokenRecoveryPassword(resetPasswordRequest.token())).thenReturn(Optional.empty());


        ResetPasswordResponse validResetPassword = userService.resetPassword(resetPasswordRequest);

        assertNotNull(validResetPassword);
        assertEquals(resetPasswordResponse, validResetPassword);
    }

    @Test
    void testResetInvalidPassword() {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest("invalidToken", "Testy1", "Testy123!");

        ResetPasswordResponse resetPasswordResponse = new ResetPasswordResponse(false, "Passwords are not the same");
        when(recoveryPasswordRepository.findByTokenRecoveryPassword(resetPasswordRequest.token())).thenReturn(Optional.of(getRecoveryPassword()));


        ResetPasswordResponse validResetPassword = userService.resetPassword(resetPasswordRequest);

        assertNotNull(validResetPassword);
        assertEquals(resetPasswordResponse, validResetPassword);
    }

    @Test
    void testResetPasswordCorrectData() {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest("invalidToken", "Testy123!", "Testy123!");

        ResetPasswordResponse resetPasswordResponse = new ResetPasswordResponse(true, "The password has been changed");
        when(recoveryPasswordRepository.findByTokenRecoveryPassword(resetPasswordRequest.token())).thenReturn(Optional.of(getRecoveryPassword()));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(recoveryPasswordRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(getRecoveryPassword()));


        ResetPasswordResponse validResetPassword = userService.resetPassword(resetPasswordRequest);

        assertNotNull(validResetPassword);
        assertEquals(resetPasswordResponse, validResetPassword);
        verify(userRepository, times(1)).save(any(User.class));
        verify(recoveryPasswordRepository, times(1)).delete(any(RecoveryPassword.class));
    }

    @Test
    void testUpdateUserRest() {
        UserRequest userRequest = UserRequest.builder()
                .name("new name")
                .enabled(true)
                .build();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Optional<User> updateUser = userService.updateUser(user.getId(), userRequest);

        assertTrue(updateUser.isPresent());
        assertEquals(user, updateUser.get());
        verify(userRepository,times(1)).save(any(User.class));
    }

    @Test
    void deleteUser() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Optional<User> deleteUser = userService.deleteUser(user.getId());

        assertTrue(deleteUser.isPresent());
        assertEquals(user,deleteUser.get());
        verify(userRepository,times(1)).delete(any(User.class));
    }

    @Test
    void deleteOccurrenceEmailInListReset() {
        String email = "Token";
        when(recoveryPasswordRepository.findByEmail(email)).thenReturn(Optional.of(getRecoveryPassword()));

        userService.deleteOccurrenceEmailInListReset(email);

        verify(recoveryPasswordRepository, times(1)).delete(any(RecoveryPassword.class));
    }

    public RecoveryPassword getRecoveryPassword() {
        return new RecoveryPassword(1L, user.getEmail(), "Token", LocalDateTime.now());
    }
}