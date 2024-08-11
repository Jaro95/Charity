package pl.coderslab.charity.domain.donation;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.List;

@Builder
public record DonationUpdateRequest
        (
                @Min(value = 1)
                Integer quantity,
                List<Long> categoryIdList,
                Long institutionId,
                DonationAddress donationAddress,
                Boolean receive,
                Long userId
        )
{
}