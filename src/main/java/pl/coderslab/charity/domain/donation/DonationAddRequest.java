package pl.coderslab.charity.domain.donation;

import jakarta.validation.constraints.*;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
public record DonationAddRequest
        (
        @NotNull
        int quantity,
        @NotEmpty
        List<Long> categoryIdList,
        @NotNull
        Long institutionId,
        @NotBlank
        @Size(max=100)
        String street,
        @NotBlank
        @Size(max=100)
        String city,
        @NotBlank
        @Size(max=50)
        String zipCode,
        @Min(value = 1)
        long phoneNumber,
        @NotNull
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate pickUpDate,
        @NotNull
        LocalTime pickUpTime,
        boolean receive,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate createdDate,
        LocalTime createdTime,
        @Size(max=500)
        String pickUpComment,
        @NotNull
        Long userId
        ) {
}
