package pl.coderslab.charity.domain.donation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/donations")
public class DonationsController {

    private final DonationService donationService;

    @GetMapping("")
    public DonationListResponse getAllDonations() {
        return new DonationListResponse(donationService.getAllDonations());
    }

    @GetMapping("/{id}")
    public DonationResponse getDonation(@PathVariable Long id) {
        return new DonationResponse(donationService.getDonation(id));
    }

    @PostMapping("/add")
    public DonationAddResponse addDonationUser(@RequestBody @Valid DonationAddRequest donationAddRequest) {
        return new DonationAddResponse(donationService.addDonation(donationAddRequest));
    }

    @PutMapping("/{id}")
    public DonationResponse updateDonation( @PathVariable Long id, @RequestBody @Valid DonationUpdateRequest donationUpdateRequest) {
        return new DonationResponse(donationService.updateDonation(id ,donationUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public DonationResponse deleteDonation(@PathVariable Long id) {
        return new DonationResponse(donationService.deleteDonation(id));
    }
}
