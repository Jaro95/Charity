package pl.coderslab.charity.infrastructure.security.JWT;

import jakarta.validation.constraints.NotBlank;

public record UserRequest (@NotBlank String username, @NotBlank String password){
}
