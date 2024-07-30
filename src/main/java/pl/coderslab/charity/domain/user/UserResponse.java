package pl.coderslab.charity.domain.user;

import java.util.Optional;

public record UserResponse(Optional<User> user) {
}
