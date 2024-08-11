package pl.coderslab.charity.infrastructure.security.JWT;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(@NotBlank String username, @NotBlank String password){
}
