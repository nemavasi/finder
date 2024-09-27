package my.tests.max_finder_with_swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/find").permitAll()
                        //.requestMatchers("/api/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/*", "/swagger.json/**", "/bus/v3/api-docs/**").permitAll()
//                        .requestMatchers("/swagger-ui/**",
//                                "/swagger-resources/*",
//                                "/v3/api-docs/**")
                      //  .permitAll()
                        //.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                    //    .anyRequest().authenticated()
                )
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }
}
