package bg.softuni.heathy_desserts_recipes.config;


import bg.softuni.heathy_desserts_recipes.common.enums.Role;
import bg.softuni.heathy_desserts_recipes.model.repository.UserRepository;
import bg.softuni.heathy_desserts_recipes.service.AppUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
//@EnableWebSecurity
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity, SecurityContextRepository securityContextRepository) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll()
                        .requestMatchers("/", "/terms", "/contact", "/about", "/faq", "/error/**", "/test", "/api/test", "/api/users/{id}", "/api/users/all", "/api/recipes", "/api/recipes/{id}", "/api/about", "/api/about/delete/{id}","/api/about/emails", "messages/add", "messages/all-messages")
                        .permitAll()
                        .requestMatchers("/login/**", "/registration", "/registration/success")
                        .anonymous()
                        .requestMatchers("/admin/**", "/api/admin/**")
                        .hasRole(Role.ADMIN.name())
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(loginConfigurer -> {
                    loginConfigurer
                            .loginPage("/login")
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/users/me")
                            .failureUrl("/login?error=true");
                })
                .logout(logoutConfigurer -> {
                    logoutConfigurer
                            .logoutUrl("/logout")
                            .deleteCookies("JSESSIONID")
                            .clearAuthentication(true)
                            .invalidateHttpSession(true);
                }).securityContext(securityContextConfigurer -> {
                   securityContextConfigurer.
                securityContextRepository(securityContextRepository);
                });
                //.csrf.disable();
               // .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());





        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }






    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new AppUserDetailsService(userRepository);
    }
        @Bean
        public SecurityContextRepository securityContextRepository() {
            return new DelegatingSecurityContextRepository(
                    new RequestAttributeSecurityContextRepository(),
                    new HttpSessionSecurityContextRepository()
            );
        }

}
