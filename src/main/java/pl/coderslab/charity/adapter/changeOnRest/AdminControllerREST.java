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
import pl.coderslab.charity.domain.category.Category;
import pl.coderslab.charity.domain.category.CategoryRepository;
import pl.coderslab.charity.domain.donation.Donation;
import pl.coderslab.charity.domain.donation.DonationRepository;
import pl.coderslab.charity.domain.institution.Institution;
import pl.coderslab.charity.domain.institution.InstitutionRepository;
import pl.coderslab.charity.domain.user.EditPassword;
import pl.coderslab.charity.domain.user.User;
import pl.coderslab.charity.domain.user.UserRepository;
import pl.coderslab.charity.domain.user.UserService;
import pl.coderslab.charity.infrastructure.security.CurrentUser;
import pl.coderslab.charity.infrastructure.security.RoleRepository;

import java.util.List;
import java.util.Optional;


@Controller
@AllArgsConstructor
@RequestMapping("/rest/admin")
@Slf4j
public class AdminControllerREST {

  //  private final AdminService adminService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final InstitutionRepository institutionRepository;
    private final DonationRepository donationRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;


    @GetMapping("")
    public String panelAdmin(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        return "admin/adminPanel";
    }
    @GetMapping("/userList")
    public String userList(Model model) {
        model.addAttribute("userList", userRepository.onlyUsers());
        return "admin/userList";
    }
    @GetMapping("/adminList")
    public String adminList(Model model) {
        List<User> adminList = userRepository.onlyAdmins();
        model.addAttribute("userList", adminList);
        return "admin/adminList";
    }

    @GetMapping("/user/update")
    public String getUpdateUser(@RequestParam Long id, Model model) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("userId", user.getId());
        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("userRole", user.getRole());
        model.addAttribute("userPassword", user.getPassword());
        return "admin/updateUser";
    }

    @PostMapping("/user/update")
    public String postUpdateUser(@Valid User user,
                                 Model model, BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("userId", user.getId());
            model.addAttribute("userEmail", user.getEmail());
            model.addAttribute("roles", roleRepository.findAll());
            model.addAttribute("userRole", user.getRole());
            model.addAttribute("userPassword", user.getPassword());
            model.addAttribute("errors", result.getAllErrors());
            return "admin/updateUser";
        }
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("message", "Edycja przebiedła pomyslnie");
        return "redirect:/charity/admin";
    }

    @GetMapping("/user/delete")
    public String getDeleteUser(@RequestParam Long id, @AuthenticationPrincipal CurrentUser currentUser,
                                RedirectAttributes redirectAttributes) {
        if (currentUser.getUser().getId() == id) {
            redirectAttributes.addFlashAttribute("messageError", "Nie możesz usunąć samego siebie");
            return "redirect:/charity/admin";
        }
        User user = userRepository.findById(id).get();
        user.getRole().clear();
        userRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Użytkownik usunięty pomyślnie");
        return "redirect:/charity/admin";
    }

    @GetMapping("/user/password")
    public String getEditPassword(@RequestParam Long id ,Model model) {
        if (!id.describeConstable().isPresent()) {
            return "redirect:/charity/admin";
        }
        model.addAttribute("id", id);
        model.addAttribute("editPassword", new EditPassword());
        return "admin/editPassword";
    }

    @PostMapping("/user/password")
    public String postEditPassword(@RequestParam Long id, @Valid EditPassword editPassword,BindingResult result,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
        if (result.hasErrors()) {
            model.addAttribute("editPassword", new EditPassword());
            model.addAttribute("id", id);
            model.addAttribute("errors", result.getAllErrors());
            return "admin/editPassword";
        }
        if (!editPassword.getPassword().equals(editPassword.getRepeatPassword())) {
            model.addAttribute("editPassword", new EditPassword());
            model.addAttribute("id", id);
            model.addAttribute("messageError", "Hasła nie są takie same");
            return "admin/editPassword";
        }

        userService.resetPasswordForAdmin(id,editPassword.getPassword());
        redirectAttributes.addFlashAttribute("message", "Hasło zostało zmienione");
        return "redirect:/charity/admin";
    }

    @GetMapping("/category")
    public String getCategory(@RequestParam(required = false) Long updateId, Model model) {
        if (updateId != null) {
            model.addAttribute("updateId", updateId);
        }
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "admin/category";
    }

    @PostMapping("/category")
    public String postCategory(@RequestParam Long categoryId, @RequestParam String categoryName,
                               RedirectAttributes redirectAttributes) {
        Category category = categoryRepository.findById(categoryId).get();
        category.setName(categoryName);
        categoryRepository.save(category);

        redirectAttributes.addFlashAttribute("message", "Edycja przebiegła pomyślnie");
        return "redirect:/charity/admin/category";
    }

    @GetMapping("/category/add")
    public String getAddCategory(Model model) {
        model.addAttribute("category", new Category());
        return "admin/addCategory";
    }

    @PostMapping("/category/add")
    public String postAddCategory(@Valid Category category, Model model,
                                  BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("category", category);
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("messageError", "Napotkano błędy w formularzu");
            return "admin/addCategory";
        }
        categoryRepository.save(category);
        redirectAttributes.addFlashAttribute("message", "Kategoria dodana pomyślnie");
        return "redirect:/charity/admin/category/add";
    }

    @GetMapping("/category/delete")
    public String getDeleteCategory(@RequestParam Long deleteId, RedirectAttributes redirectAttributes) {
        categoryRepository.deleteById(deleteId);
        redirectAttributes.addFlashAttribute("message", "Kategoria usunięta pomyślnie");
        return "redirect:/charity/admin/category";
    }

    @GetMapping("/donation")
    public String getDonation(Model model) {
        model.addAttribute("donationList", donationRepository.findAll());
        return "admin/donation";
    }

    @GetMapping("/donation/update")
    public String getUpdateDonation(@RequestParam Long id, Model model) {
        Optional<Donation> donation = donationRepository.findById(id);
        if(donation.isEmpty()) {
            return "redirect:charity/admin/donation";
        }
        model.addAttribute("donation", donation.get());
        model.addAttribute("institutions", institutionRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("createdDate", donation.get().getCreatedDate());
        model.addAttribute("createdTime", donation.get().getCreatedTime());
        return "admin/updateDonation";
    }

    @PostMapping("/donation/update")
    public String postUpdateDonation(@Valid Donation donation, BindingResult result,
                                      RedirectAttributes redirectAttributes,
                                     Model model) {

        if(result.hasErrors()) {
            model.addAttribute("donation", donation);
            model.addAttribute("institutions", institutionRepository.findAll());
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("createdDate", donation.getCreatedDate());
            model.addAttribute("createdTime", donation.getCreatedTime());
            model.addAttribute("errors", result.getAllErrors());
            return "admin/updateDonation";
        }
        donation.setUser(donationRepository.findById(donation.getId()).get().getUser());
        donationRepository.save(donation);
        redirectAttributes.addFlashAttribute("message", "Edycja przebiegła pomyslnie");
        log.info("Updated donation: {}", donation.toString());
        return "redirect:/charity/admin/donation";
    }

    @GetMapping("/donation/delete")
    public String getDeleteDonation(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        donationRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Dar usunięty pomyślnie");
        return "redirect:/charity/admin/donation";
    }

    @GetMapping("/institution")
    public String getInstitution(@RequestParam(required = false) Long updateId, Model model) {
        if (updateId != null) {
            model.addAttribute("updateId", updateId);
        }
        model.addAttribute("institutionList", institutionRepository.findAll());
        return "admin/institution";
    }

    @PostMapping("/institution")
    public String postInstitution(@RequestParam long institutionId, @RequestParam String institutionName,
                                  @RequestParam String institutionDescription,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        Institution institution = institutionRepository.findById(institutionId).get();
        institution.setName(institutionName);
        institution.setDescription(institutionDescription);
        institutionRepository.save(institution);
        redirectAttributes.addFlashAttribute("message", "Edycja przebiegła pomyślnie");
        return "redirect:/charity/admin/institution";
    }

    @GetMapping("/institution/add")
    public String getAddInstitution(Model model) {
        model.addAttribute("institution", new Institution());
        return "admin/addInstitution";
    }

    @PostMapping("/institution/add")
    public String getAddInstitution(@Valid Institution institution, BindingResult result,
                                    Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("institution", institution);
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("messageError", "Napotkano błędy w formularzu");
            return "admin/addInstitution";
        }
        institutionRepository.save(institution);
        redirectAttributes.addFlashAttribute("message", "Dodano fundację");
        return "redirect:/charity/admin/institution/add";
    }

    @GetMapping("/institution/delete")
    public String getDeleteInstitution(@RequestParam Long deleteId, RedirectAttributes redirectAttributes) {
        institutionRepository.deleteById(deleteId);
        redirectAttributes.addFlashAttribute("message", "Fundacja usunięta pomyślnie");
        return "redirect:/charity/admin/institution";
    }

}
