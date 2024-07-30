package pl.coderslab.charity.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderslab.charity.domain.dataFactory.TestDataFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
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
        assertEquals(user,findUser.get());
    }

    @Test
    void findByToken() {
    }

    @Test
    void saveUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void testUpdateUser() {
    }

    @Test
    void sendRecoveryPasswordEmail() {
    }

    @Test
    void resetPassword() {
    }

    @Test
    void resetPasswordForAdmin() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getUsersWithRole() {
    }

    @Test
    void getOnlyUsers() {
    }

    @Test
    void getOnlyAdmins() {
    }

    @Test
    void registrationUser() {
    }

    @Test
    void activateUser() {
    }

    @Test
    void resetPasswordCheckEmail() {
    }

    @Test
    void resetPasswordCheckToken() {
    }

    @Test
    void testResetPassword() {
    }

    @Test
    void testUpdateUser1() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void deleteOccurrenceEmailInListReset() {
    }
}