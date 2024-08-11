package pl.coderslab.charity.domain.institution;

import jakarta.validation.constraints.NotBlank;

public record InstitutionAddRequest(
        @NotBlank
        String name,
        @NotBlank
        String description
) {

}
