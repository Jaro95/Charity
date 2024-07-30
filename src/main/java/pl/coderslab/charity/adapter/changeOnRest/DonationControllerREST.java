package pl.coderslab.charity.adapter.changeOnRest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.charity.domain.category.CategoryRepository;
import pl.coderslab.charity.domain.donation.Donation;
import pl.coderslab.charity.domain.donation.DonationRepository;
import pl.coderslab.charity.domain.institution.InstitutionRepository;
import pl.coderslab.charity.domain.user.EditPassword;
import pl.coderslab.charity.domain.user.User;
import pl.coderslab.charity.domain.user.UserRepository;
import pl.coderslab.charity.domain.user.UserService;
import pl.coderslab.charity.infrastructure.security.CurrentUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Controller
@RequestMapping("/rest/donation")
@AllArgsConstructor
@Slf4j
public class DonationControllerREST {

    private final InstitutionRepository institutionRepository;
    private final CategoryRepository categoryRepository;
    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final UserService userService;


    @PostMapping("")
    public String postGiveDonation(@Valid Donation donation, @AuthenticationPrincipal CurrentUser currentUser,
                                   BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            model.addAttribute("institutions", institutionRepository.findAll());
            model.addAttribute("donation", donation);
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("messageError", "Napotkano błędy w formularzu");
            return "application/main";
        }
        donation.setUser(currentUser.getUser());
        donation.setCreatedDate(LocalDate.now());
        donation.setCreatedTime(LocalTime.now().withSecond(0).withNano(0));
        donation.setReceive(false);
        donationRepository.save(donation);
        redirectAttributes.addFlashAttribute("donationSuccessFull", "completed");
       // log.info("Added donation {}", donation.toString());
        return "redirect:/charity/donation";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        User user = userRepository.findById(currentUser.getUser().getId()).get();
        model.addAttribute("name", user.getName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("donation", donationRepository.countDonationsUser(user.getId()).get());
        return "application/profile";
    }

    @GetMapping("/profile/edit")
    public String getEditProfile(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        model.addAttribute("user", currentUser.getUser());
        return "application/editProfile";
    }

    @PostMapping("/profile/edit")
    public String postEditProfile(@Valid User user, Model model,
                                  BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("errors", result.getAllErrors());
            return "application/editProfile";
        }
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("message", "Edycja przebiegła pomyślnie");
        return "redirect:/charity/donation/profile";
    }

    @GetMapping("/profile/password")
    public String getEditProfile(Model model) {
        model.addAttribute("editPassword", new EditPassword());
        return "application/editPassword";
    }

    @PostMapping("/profile/password")
    public String postEditProfile(@Valid EditPassword editPassword, @AuthenticationPrincipal CurrentUser currentUser,
                                  Model model, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("editPassword", editPassword);
            model.addAttribute("errors", result.getAllErrors());
            return "application/editPassword";
        }

        if (!editPassword.getPassword().equals(editPassword.getRepeatPassword())) {
            model.addAttribute("editPassword", editPassword);
            model.addAttribute("messageError", "Hasła nie są takie same");
            return "application/editPassword";
        }
        userService.updateUser(currentUser.getUser(),editPassword.getPassword());
        redirectAttributes.addFlashAttribute("message", "Edycja przebiegła pomyślnie");
        return "redirect:/charity/donation/profile";
    }

    @GetMapping("/userDonation")
    public String getDonation(@AuthenticationPrincipal CurrentUser currentUser ,Model model) {
        model.addAttribute("donationList", donationRepository.allDonationsUser(currentUser.getUser()));
        return "application/donations";
    }

    @GetMapping("/userDonation/update")
    public String getEditDonation(@RequestParam Long id , @AuthenticationPrincipal CurrentUser currentUser,
                                  Model model) {
        Optional<Donation> donation = donationRepository.donationUserUpdate(currentUser.getUser(),id);
        if(donation.isEmpty()) {
            model.addAttribute("donationList", donationRepository.allDonationsUser(currentUser.getUser()));
            return "application/donations";
        }
        model.addAttribute("donation", donation.get());
        model.addAttribute("institutions", institutionRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("createdDate", donation.get().getCreatedDate());
        model.addAttribute("createdTime", donation.get().getCreatedTime());
        return "application/updateDonation";
    }

    @PostMapping("/userDonation/update")
    public String postEditDonation(@Valid Donation donation, @AuthenticationPrincipal CurrentUser currentUser,
                                   BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("donation", donation);
            model.addAttribute("institutions", institutionRepository.findAll());
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("createdDate", donation.getCreatedDate());
            model.addAttribute("createdTime", donation.getCreatedTime());
            model.addAttribute("errors", result.getAllErrors());
            return "application/updateDonation";
        }
        donation.setUser(currentUser.getUser());
        donationRepository.save(donation);
        redirectAttributes.addFlashAttribute("message", "Edycja przebiegła pomyslnie");
        log.info("Updated donation: {}", donation.toString());
        return "redirect:/charity/donation/userDonation";
    }

    @GetMapping("/userDonation/delete")
    public String getDeleteDonation(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        donationRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Dar usunięty pomyślnie");
        return "redirect:/charity/donation/userDonation";
    }
}
