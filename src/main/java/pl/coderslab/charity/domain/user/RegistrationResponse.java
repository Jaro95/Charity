package pl.coderslab.charity.domain.user;

public record RegistrationResponse(boolean successful, String message, RegistrationRequest registrationRequest) {
}
