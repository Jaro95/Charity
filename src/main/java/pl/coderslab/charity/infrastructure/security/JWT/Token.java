package pl.coderslab.charity.infrastructure.security.JWT;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.domain.user.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accessToken;
    private String refreshToken;
    private boolean loggedOut;
    @ManyToOne
    private User user;
}
