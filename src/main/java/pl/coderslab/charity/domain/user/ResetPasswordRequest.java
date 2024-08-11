package pl.coderslab.charity.domain.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(String token,
                                   @Size(min = 8, message = "{Size.registrationDTO.password}")
                                   @Pattern(
                                           regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!*@#$%^&+=])(?=\\S+$).{8,}$",
                                           message = "Hasło musi zawierać wielkie litery, małe litery, cyfry i znaki specjalne"
                                   )
                                   String password,
                                   String repeatPassword) {
}
