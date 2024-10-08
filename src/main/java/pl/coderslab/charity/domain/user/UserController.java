package pl.coderslab.charity.domain.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public UserListResponse getAllUsers() {
        return new UserListResponse(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public UserResponse getUsersWithRole(@PathVariable Long id) {
        return new UserResponse(userService.findById(id));
    }

    @GetMapping("/role/{role}")
    public UserListResponse getUsersWithRole(@PathVariable String role) {
        return new UserListResponse(userService.getUsersWithRole(role));
    }

    @PostMapping("/registration")
    public RegistrationResponse registration(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return userService.registrationUser(registrationRequest);
    }

    @Operation(
            summary = "Verify the user's token for authenticity, then activate the user account."
    )
    @GetMapping("/verification")
    public ActivateUserResponse activateUser(@RequestBody(required = false) ActivateUserRequest activateUserRequest) {
        return userService.activateUser(activateUserRequest);
    }

    @Operation(
            summary = "Sends a token to the specified email address with the option to change the password."
    )
    @PostMapping("/recovery/{email}")
    public EmailCheckEmailResponse postRecoveryPasswordEmail(@PathVariable String email) {
        return userService.resetPasswordCheckEmail(email);
    }

    @Operation(
            summary = "Authenticate the recovery password token to ensure it's validity."
    )
    @GetMapping("/recovery/password")
    public ResetPasswordCheckTokenResponse getRecoveryPassword(@RequestBody(required = false) ResetPasswordCheckTokenRequest resetPasswordCheckTokenRequest) {
        return userService.resetPasswordCheckToken(resetPasswordCheckTokenRequest);
    }

    @Operation(
            summary = "Authenticate the recovery password token to ensure it's validity and then reset the user's password accordingly."
    )
    @PostMapping("/recovery/password")
    public ResetPasswordResponse postRecoveryPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
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
