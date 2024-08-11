package pl.coderslab.charity.domain.donation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.domain.category.Category;
import pl.coderslab.charity.domain.category.CategoryRepository;
import pl.coderslab.charity.domain.institution.InstitutionRepository;
import pl.coderslab.charity.domain.user.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final InstitutionRepository institutionRepository;

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public Optional<Donation> getDonation(Long id) {
        return donationRepository.findById(id);
    }

    public DonationAddRequest addDonation(DonationAddRequest donationAddRequest) {
        List<Category> categories = new ArrayList<>();
        donationAddRequest.categoryIdList().forEach(c -> categories.add(categoryRepository.findById(c).orElseThrow()));
        donationRepository.save(Donation.builder()
                .quantity(donationAddRequest.quantity())
                .category(categories)
                .institution(institutionRepository.findById(donationAddRequest.institutionId()).orElseThrow(IllegalArgumentException::new))
                .street(donationAddRequest.street())
                .city(donationAddRequest.city())
                .zipCode(donationAddRequest.zipCode())
                .phoneNumber(donationAddRequest.phoneNumber())
                .pickUpDate(donationAddRequest.pickUpDate())
                .pickUpTime(donationAddRequest.pickUpTime())
                .pickUpComment(donationAddRequest.pickUpComment())
                .receive(false)
                .createdDate(LocalDate.now())
                .createdTime(LocalTime.now().withSecond(0).withNano(0))
                .user(userRepository.findById(donationAddRequest.userId()).orElseThrow(IllegalArgumentException::new))
                .build());
        log.info("Added new donation: {}", donationAddRequest);
        return donationAddRequest;
    }

    public Optional<Donation> updateDonation(Long id, DonationUpdateRequest donationUpdateRequest) {
        Optional<Donation> donation = donationRepository.findById(id);
        List<Category> categories = new ArrayList<>();
        donation.ifPresent(d -> {
            Optional.ofNullable(donationUpdateRequest.categoryIdList())
                    .filter(categoriesIdList -> !categoriesIdList.isEmpty())
                    .ifPresent(categoryIdList-> categoryIdList.forEach(categoryId ->
                            categories.add(categoryRepository.findById(categoryId)
                            .orElseThrow(IllegalArgumentException::new)))
                    );
            if (!categories.isEmpty()) {
                d.setCategory(categories.equals(d.getCategory()) ? d.getCategory() : categories);
            }

            Optional.ofNullable(donationUpdateRequest.quantity()).ifPresent(quantity ->
                d.setQuantity(d.getQuantity() == quantity ? d.getQuantity() : quantity)
            );

            Optional.ofNullable(donationUpdateRequest.institutionId()).ifPresent(institutionID ->
                    d.setInstitution(d.getInstitution().getId() == institutionID ? d.getInstitution()
                            : institutionRepository.findById(institutionID).orElseThrow(IllegalArgumentException::new))
            );

            Optional.ofNullable(donationUpdateRequest.donationAddress().getStreet()).ifPresent(street ->
                    d.setStreet(d.getStreet().equals(street) ? d.getStreet() : street)
            );

            Optional.ofNullable(donationUpdateRequest.donationAddress().getCity()).ifPresent(city ->
                    d.setCity(d.getCity().equals(city) ? d.getCity() : city)
            );

            Optional.ofNullable(donationUpdateRequest.donationAddress().getZipCode()).ifPresent(zipCode ->
                    d.setZipCode(d.getZipCode().equals(zipCode) ? d.getStreet() : zipCode)
            );

            Optional.ofNullable(donationUpdateRequest.donationAddress().getPhoneNumber()).ifPresent(phoneNumber ->
                    d.setPhoneNumber(d.getPhoneNumber() == phoneNumber ? d.getPhoneNumber() : phoneNumber)
            );

            Optional.ofNullable(donationUpdateRequest.donationAddress().getPickUpDate()).ifPresent(pickUpDate ->
                    d.setPickUpDate(d.getPickUpDate().equals(pickUpDate) ? d.getPickUpDate() : pickUpDate)
            );

            Optional.ofNullable(donationUpdateRequest.donationAddress().getPickUpTime()).ifPresent(pickUpTime ->
                    d.setPickUpTime(d.getPickUpTime().equals(pickUpTime) ? d.getPickUpTime() : pickUpTime)
            );

            Optional.ofNullable(donationUpdateRequest.receive()).ifPresent(receive ->
                    d.setReceive(d.isReceive() == receive ? d.isReceive()
                            : receive)
                    );

            Optional.ofNullable(donationUpdateRequest.donationAddress().getPickUpComment()).ifPresent(pickUpComment ->
                            d.setPickUpComment(d.getPickUpComment().equals(pickUpComment) ? d.getPickUpComment() : pickUpComment)
                    );

            Optional.ofNullable(donationUpdateRequest.userId()).ifPresent(userId -> d.setUser(d.getUser().getId() == userId ?
                            d.getUser() : userRepository.findById(userId).orElseThrow(IllegalArgumentException::new))
                    );

            donationRepository.save(d);
            log.info("Updated donation: {}", d);
        });

        return donation;
    }

    public Optional<Donation> deleteDonation(Long id) {
        Optional<Donation> donation = donationRepository.findById(id);
        donation.ifPresent(d -> {
            donationRepository.delete(d);
            log.info("Deleted donation: {}", d);
        });
        return donation;
    }
}
