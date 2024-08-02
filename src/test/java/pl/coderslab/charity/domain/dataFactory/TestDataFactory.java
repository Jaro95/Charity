package pl.coderslab.charity.domain.dataFactory;

import pl.coderslab.charity.domain.category.Category;
import pl.coderslab.charity.domain.donation.Donation;
import pl.coderslab.charity.domain.donation.DonationAddRequest;
import pl.coderslab.charity.domain.institution.Institution;
import pl.coderslab.charity.domain.user.RecoveryPassword;
import pl.coderslab.charity.domain.user.RegistrationRequest;
import pl.coderslab.charity.domain.user.User;
import pl.coderslab.charity.infrastructure.security.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class TestDataFactory {

    public static Category createCategory() {
        return Category
                .builder()
                .id(1L)
                .name("name 1")
                .build();
    }

    public static Institution createInstitution() {
        return Institution.builder()
                .id(1L)
                .name("Institution 1")
                .description("Description 1")
                .build();
    }

    public static Set<Role> createRoleList() {
        return Set.of(createRole());
    }

    public static Role createRole() {
        return Role.builder().name("USER").build();
    }

    public static User createUser() {
        return User.builder()
                .id(1)
                .email("u@u")
                .name("userName")
                .lastName("userLastName")
                .password("password")
                .enabled(true)
                .role(createRoleList())
                .token("token")
                .createdAccount(LocalDateTime.of(2024, 8, 1, 14, 0, 0))
                .build();
    }

    public static RegistrationRequest addUser() {
        return RegistrationRequest .builder()
                .email("new@u")
                .firstName("new userName")
                .lastName("new userLastName")
                .password("new password")
                .build();
    }

    public static Donation createDonation() {
        return Donation.builder()
                .quantity(3)
                .category(List.of(createCategory()))
                .institution(createInstitution())
                .street("street")
                .city("city")
                .zipCode("zipCode")
                .phoneNumber(777666555)
                .pickUpDate(LocalDate.parse("2024-09-01"))
                .pickUpTime(LocalTime.of(14, 0, 0))
                .pickUpComment("comment")
                .receive(false)
                .createdDate(LocalDate.of(2024,8,1))
                .createdTime(LocalTime.of(14, 0, 0))
                .user(createUser())
                .build();
    }

    public static DonationAddRequest createAddDonation() {
        return DonationAddRequest.builder()
                .quantity(3)
                .categoryIdList(List.of(1L))
                .street("street Add")
                .city("city Add")
                .zipCode("zipCode")
                .phoneNumber(333222111)
                .pickUpDate(LocalDate.parse("2024-09-01"))
                .pickUpTime(LocalTime.of(14, 0, 0))
                .pickUpComment("comment")
                .receive(false)
                .createdDate(LocalDate.of(2024,8,1))
                .createdTime(LocalTime.of(14, 0, 0))
                .userId(1L)
                .build();
    }

    public static RecoveryPassword createRecoveryPassword() {
        return RecoveryPassword.builder()
                .id(1L)
                .email("u@u")
                .tokenRecoveryPassword("Recovery Token")
                .localDateTime(LocalDateTime.of(2024, 8, 1, 14, 0, 0))
                .build();
    }
}
