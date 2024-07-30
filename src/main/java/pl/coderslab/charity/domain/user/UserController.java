package pl.coderslab.charity.domain.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.domain.institution.Institution;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/users")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public UserListResponse getAllUsers() {
        return new UserListResponse(userService.getAllUsers());
    }

    @GetMapping("/{role}")
    public UserListResponse getUsersWithRole(@PathVariable String role) {
        return new UserListResponse(userService.getUsersWithRole(role));
    }

    @PostMapping("/registration")
    public RegistrationResponse registration(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return userService.registrationUser(registrationRequest);
    }

    @GetMapping("/verification")
    public ActivateUserResponse activateUser(@RequestBody(required = false) ActivateUserRequest activateUserRequest) {
        return userService.activateUser(activateUserRequest);
    }

    @PostMapping("/recovery/{email}")
    public EmailCheckEmailResponse postRecoveryPasswordEmail(@PathVariable String email) {
        return userService.resetPasswordCheckEmail(email);
    }

    @GetMapping("/recovery/password")
    public ResetPasswordCheckTokenResponse getRecoveryPassword(@RequestBody(required = false) ResetPasswordCheckTokenRequest resetPasswordCheckTokenRequest) {
        return userService.resetPasswordCheckToken(resetPasswordCheckTokenRequest);
    }

    @PostMapping("/recovery/password")
    public ResetPasswordCheckTokenResponse postRecoveryPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
        return userService.resetPassword(resetPasswordRequest);
    }

    @PutMapping("/{id}")
    public UserResponse deleteUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return new UserResponse(userService.updateUser(id, userRequest));
    }

    @DeleteMapping("/{id}")
    public UserResponse deleteUser(@PathVariable Long id) {
        return new UserResponse(userService.deleteUser(id));
    }
}
