package school_project.example.back.Security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
            throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173")); // origem do seu front (Vite)
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager)
            throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // <- adiciona isso
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // libera preflight OPTIONS pra qualquer rota, sem exigir autenticação
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers("/api/auth/**").permitAll()

                        .requestMatchers("/api/users/directors/**").hasRole("DIRECTOR")

                        .requestMatchers(HttpMethod.POST, "/api/users/teachers").hasRole("DIRECTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/users/teachers/**").hasRole("DIRECTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/teachers/**").hasRole("DIRECTOR")
                        .requestMatchers(HttpMethod.GET, "/api/users/teachers/**")
                        .hasAnyRole("STUDENT", "TEACHER", "DIRECTOR")

                        .requestMatchers(HttpMethod.POST, "/api/users/students").hasRole("DIRECTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/users/students/**").hasRole("DIRECTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/students/**").hasRole("DIRECTOR")
                        .requestMatchers(HttpMethod.GET, "/api/users/students/**")
                        .hasAnyRole("STUDENT", "TEACHER", "DIRECTOR")

                        .requestMatchers(HttpMethod.POST, "/api/boletins/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/api/boletins/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/api/boletins/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.GET, "/api/boletins/**").hasAnyRole("STUDENT", "TEACHER")

                        .anyRequest().authenticated())
                .authenticationManager(authManager)
                .userDetailsService(userDetailsService)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}