package pl.coderslab.charity.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.coderslab.charity.infrastructure.security.JWT.CustomLogoutHandler;
import pl.coderslab.charity.infrastructure.security.JWT.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomLogoutHandler logoutHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/login", "/api/users/registration","/api/users/verification",
                                        "/api/users/recovery/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasRole("SUPER_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/categories/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/institutions/**").hasRole("SUPER_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/institutions/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/institutions/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("SUPER_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("SUPER_ADMIN")
                                //.requestMatchers("/**").permitAll()
//                        .requestMatchers("/**", "/charity/login", "/charity/registration").permitAll()
//                        .requestMatchers("/charity/**").hasAnyRole("USER", "ADMIN", "SUPER_ADMIN")
//                        .requestMatchers("/charity/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
//                        .requestMatchers("/images/**", "/css/**", "/js/**", "/WEB-INF/views/**").permitAll()
                                .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        e -> e.accessDeniedHandler(
                                        (request, response, accessDeniedException) -> response.setStatus(403)
                                )
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .logout(l -> l
                        .logoutUrl("/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()
                        ));
//                .formLogin(form -> form
//                        .loginPage("/charity/login")
//                        .failureHandler(customAuthenticationFailureHandler())
//                        .defaultSuccessUrl("/charity/validate", true)
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/charity/donation/logout")
//                        .logoutSuccessUrl("/charity")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                        .permitAll()
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/charity/donation/logout", "GET"))
//                )

//                .securityContext(context -> context
//                        .requireExplicitSave(false)
//                );

        return http.build();
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            response.sendRedirect("/charity/login?error=true");
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
