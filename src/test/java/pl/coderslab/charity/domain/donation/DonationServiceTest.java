package pl.coderslab.charity.domain.donation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderslab.charity.domain.category.Category;
import pl.coderslab.charity.domain.category.CategoryRepository;
import pl.coderslab.charity.domain.dataFactory.TestDataFactory;
import pl.coderslab.charity.domain.institution.Institution;
import pl.coderslab.charity.domain.institution.InstitutionRepository;
import pl.coderslab.charity.domain.user.User;
import pl.coderslab.charity.domain.user.UserRepository;
import pl.coderslab.charity.infrastructure.security.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DonationServiceTest {

    @Mock
    private DonationRepository donationRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private InstitutionRepository institutionRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private DonationService donationService;
    private Institution institution = TestDataFactory.createInstitution();
    private Donation donation;
    private DonationAddRequest donationAddRequest;
    private DonationUpdateRequest donationUpdateRequest;

    @BeforeEach
    void setUp() {
        donation = TestDataFactory.createDonation();
        donationAddRequest = TestDataFactory.createAddDonation();
        donationUpdateRequest = DonationUpdateRequest.builder()
                .quantity(2)
                .institutionId(1L)
                .donationAddress(DonationAddress.builder()
                        .city("Update City")
                        .build())
                .build();
    }

    @Test
    void testGetAllDonations() {
        when(donationRepository.findAll()).thenReturn(List.of(donation));

        List<Donation> donations = donationService.getAllDonations();

        assertNotNull(donations);
        assertEquals(1,donations.size());
        assertEquals(donations.get(0),donation);
    }

    @Test
    void testGetDonation() {
        when(donationRepository.findById(1L)).thenReturn(Optional.of(donation));

        Optional<Donation> foundDonation = donationRepository.findById(1L);

        assertTrue(foundDonation.isPresent());
        assertEquals(donation,foundDonation.get());
    }

    @Test
    void testAddDonation() {
        institutionRepository.save(institution);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(TestDataFactory.createCategory()));
        when(institutionRepository.findById(donationAddRequest.institutionId())).thenReturn(Optional.of(institution));
        when(userRepository.findById(1L)).thenReturn(Optional.of(TestDataFactory.createUser()));

        DonationAddRequest result =  donationService.addDonation(donationAddRequest);

        assertEquals(donationAddRequest, result);
        verify(donationRepository, times(1)).save(any(Donation.class));
    }

    @Test
    void testUpdateDonation() {
        when(donationRepository.findById(1L)).thenReturn(Optional.of(donation));

        Optional<Donation> updateDonation = donationService.updateDonation(1L,donationUpdateRequest);

        assertTrue(updateDonation.isPresent());
        assertEquals(donationUpdateRequest.quantity(), updateDonation.get().getQuantity());
        assertEquals(donationUpdateRequest.institutionId(), updateDonation.get().getInstitution().getId());
        assertEquals(donationUpdateRequest.donationAddress().getCity(), updateDonation.get().getCity());
        verify(donationRepository,times(1)).save(any(Donation.class));
    }

    @Test
    void testDeleteDonation() {
        when(donationRepository.findById(1L)).thenReturn(Optional.of(donation));

        Optional<Donation> deleteDonation = donationService.deleteDonation(1L);

        assertTrue(deleteDonation.isPresent());
        verify(donationRepository, times(1)).delete(donation);
    }
}