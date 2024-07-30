package pl.coderslab.charity.domain.user;

import jakarta.validation.constraints.Size;

import java.util.Set;

public record UserRequest(
        @Size(max = 50)
        String email,
        @Size(max = 30)
        String name,
        @Size(max = 30)
        String lastName,
        String password,
        Boolean enabled,
        Set<Long> roleIdList
) {
}
