package pl.coderslab.charity.domain.donation;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.charity.domain.category.Category;
import pl.coderslab.charity.domain.institution.Institution;
import pl.coderslab.charity.domain.user.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private int quantity;
    @NotEmpty
    @ManyToMany
    private List<Category> category;
    @ManyToOne
    private Institution institution;
    @NotBlank
    @Size(max=100)
    private String street;
    @NotBlank
    @Size(max=100)
    private String city;
    @NotBlank
    @Size(max=50)
    private String zipCode;
    @Min(value = 1)
    private long phoneNumber;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickUpDate;
    @NotNull
    private LocalTime pickUpTime;
    @Size(max=500)
    private String pickUpComment;
    private boolean receive;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;
    private LocalTime createdTime;
    @ManyToOne
    private User user;
}



