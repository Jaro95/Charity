package pl.coderslab.charity.domain.donation;

import java.util.Optional;

public record DonationResponse(Optional<Donation> donation) {
}
