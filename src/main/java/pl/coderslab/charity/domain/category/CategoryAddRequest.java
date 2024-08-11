package pl.coderslab.charity.domain.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryAddRequest(@NotBlank String name) {
}
