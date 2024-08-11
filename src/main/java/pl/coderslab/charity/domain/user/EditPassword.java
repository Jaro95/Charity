package pl.coderslab.charity.domain.user;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditPassword {
    @Size(min = 8, message = "{Size.registrationDTO.password}")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!*@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Hasło musi zawierać wielkie litery, małe litery, cyfry i znaki specjalne"
    )
    private String password;
    private String repeatPassword;
}
