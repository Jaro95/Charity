package pl.coderslab.charity.adapter.forJsp;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.charity.domain.user.EditPassword;
import pl.coderslab.charity.domain.user.RegistrationRequest;
import pl.coderslab.charity.domain.user.RecoveryPassword;
import pl.coderslab.charity.domain.user.User;
import pl.coderslab.charity.domain.donation.DonationRepository;
import pl.coderslab.charity.domain.institution.InstitutionRepository;
import pl.coderslab.charity.domain.user.RecoveryPasswordRepository;
import pl.coderslab.charity.domain.user.UserRepository;
import pl.coderslab.charity.infrastructure.security.CurrentUser;
import pl.coderslab.charity.domain.user.UserService;

import java.util.Optional;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/charity")
public class HomeController {

    private final InstitutionRepository institutionRepository;
    private final DonationRepository donationRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final RecoveryPasswordRepository recoveryPasswordRepository;

    @GetMapping("")
    public String homeAction(Model model) {
        model.addAttribute("institutions", institutionRepository.findAll());
        model.addAttribute("quantityBag", donationRepository.allQuantityBag().orElse(0L));
        model.addAttribute("allDonations", donationRepository.allDonations().orElse(0L));
        return "home/main";
    }

    @GetMapping("/login")
    public String getLogin(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("wrongPassword", error);
        return "home/login";
    }

    @GetMapping("/registration")
    public String getRegistration(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "home/registration";
    }

    @PostMapping("/registration")
    public String postRegistration(@Valid RegistrationRequest registrationRequest, BindingResult result,
                                   Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("registrationRequest", registrationRequest);
            model.addAttribute("errors", result.getAllErrors());
            return "home/registration";
        }
        if (!registrationRequest.getPassword().equals(registrationRequest.getRepeatPassword())) {
            model.addAttribute("registrationRequest", registrationRequest);
            model.addAttribute("messageError", "Hasła nie są takie same");
            return "home/registration";
        }
        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            model.addAttribute("registrationRequest", registrationRequest);
            model.addAttribute("messageError", "Email jest już zajęty");
            return "home/registration";
        }

        userService.saveUser(registrationRequest);
        redirectAttributes.addFlashAttribute("message", "Rejestracja przebiegła pomyślnie");
        return "redirect:/charity/login";
    }

    @GetMapping("/recovery/email")
    public String getRecoveryPasswordEmail() {
        return "home/recoveryPasswordEmail";
    }

    @PostMapping("/recovery/email")
    public String postRecoveryPasswordEmail(@RequestParam String email, RedirectAttributes attributes) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()) {
           attributes.addFlashAttribute("messageError", "Niepoprawny Email");
            return "redirect:/charity/recovery/email";
        }
        userService.sendRecoveryPasswordEmail(email);
        attributes.addFlashAttribute("message", "Link z potwierdzeniem zmiany hasła został wysłany na email");
        return "redirect:/charity/login";
    }

    @GetMapping("/recovery/password")
    public String getRecoveryPassword(@RequestParam String token, RedirectAttributes redirectAttributes,Model model) {
        Optional<RecoveryPassword> recoveryPassword = recoveryPasswordRepository.findByTokenRecoveryPassword(token);
        if (recoveryPassword.isEmpty()) {
            redirectAttributes.addFlashAttribute("messageError", "Niepoprawny token");
            return "redirect:/charity";
        }
        model.addAttribute("editPassword", new EditPassword());
        model.addAttribute("email", recoveryPassword.get().getEmail());
        return "home/recoveryPassword";
    }

    @PostMapping("/recovery/password")
    public String postRecoveryPassword(@Valid EditPassword editPassword, BindingResult result, @RequestParam String email ,
                                       Model model,  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("editPassword", new EditPassword());
            model.addAttribute("email", email);
            model.addAttribute("errors", result.getAllErrors());
            return "home/recoveryPassword";
        }
        if (!editPassword.getPassword().equals(editPassword.getRepeatPassword())) {
            model.addAttribute("email", email);
            model.addAttribute("wrongRepeatPassword", "Hasła nie są takie same");
            return "home/recoveryPassword";
        }
        userService.resetPassword(email, editPassword.getPassword());
        redirectAttributes.addFlashAttribute("message", "Hasło zostało zmienione");
        return "redirect:/charity/login";
    }

    @GetMapping("/verification")
    public String activateUser(@RequestParam(required = false) String token, RedirectAttributes redirectAttributes) {
        Optional<User> user = userService.findByToken(token);
        if (user.isEmpty()) {
            redirectAttributes.addFlashAttribute("messageEnabled", "Token nieprawidłowy lub jego ważność wygasła");
            return "redirect:/gowithme/login";
        }
        user.get().setEnabled(true);
        user.get().setToken("verified");
        userService.updateUser(user.get());
        redirectAttributes.addFlashAttribute("message", "Konto zostało aktywowane");
        return "redirect:/charity/login";
    }

    @GetMapping("/validate")
    public String validateUser(@AuthenticationPrincipal CurrentUser currentUser, RedirectAttributes redirectAttributes) {
        if (!currentUser.getUser().isEnabled()) {
            redirectAttributes.addFlashAttribute("messageEnabled", "Konto nie zostało aktywowane");
            return "redirect:/charity/login";
        }
        return "redirect:/charity/donation";
    }
}


