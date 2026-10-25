package school_project.example.back.Security;

import java.util.List;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Value;
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
// Configura autenticação JWT, autorização por perfil e CORS da API.
public class SecurityConfig {

        private final CustomUserDetailsService userDetailsService;
        private final JwtAuthFilter jwtAuthFilter;

        @Value("${app.cors.allowed-origins:http://localhost:5173}")
        private String allowedOrigins;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration configuration)
                        throws Exception {

                return configuration.getAuthenticationManager();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {

                CorsConfiguration config = new CorsConfiguration();

                config.setAllowedOrigins(
                                Arrays.stream(allowedOrigins.split(","))
                                                .map(String::trim)
                                                .filter(origin -> !origin.isEmpty())
                                                .toList());

                config.setAllowedMethods(
                                List.of(
                                                "GET",
                                                "POST",
                                                "PUT",
                                                "DELETE",
                                                "PATCH",
                                                "OPTIONS"));

                config.setAllowedHeaders(
                                List.of("*"));

                config.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

                source.registerCorsConfiguration(
                                "/**",
                                config);

                return source;
        }

        @Bean
        public SecurityFilterChain filterChain(
                        HttpSecurity http,
                        AuthenticationManager authenticationManager)
                        throws Exception {

                http
                                .cors(cors -> cors.configurationSource(
                                                corsConfigurationSource()))

                                .csrf(csrf -> csrf.disable())

                                .sessionManagement(session -> session.sessionCreationPolicy(
                                                SessionCreationPolicy.STATELESS))

                                .authorizeHttpRequests(auth -> auth

                                                // =========================
                                                // CORS
                                                // =========================

                                                .requestMatchers(
                                                                HttpMethod.OPTIONS,
                                                                "/**")
                                                .permitAll()

                                                // =========================
                                                // LOGIN
                                                // =========================

                                                .requestMatchers(
                                                                "/api/auth/**")
                                                .permitAll()

                                                // =========================
                                                // USERS
                                                // somente diretor
                                                // =========================

                                                .requestMatchers(
                                                                "/api/users/**")
                                                .hasRole("DIRECTOR")

                                                // =========================
                                                // DIRECTORS
                                                // somente diretor
                                                // =========================

                                                .requestMatchers(
                                                                "/api/directors/**")
                                                .hasRole("DIRECTOR")

                                                // =========================
                                                // STUDENTS
                                                // =========================

                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/students/**")
                                                .hasAnyRole(
                                                                "STUDENT",
                                                                "TEACHER",
                                                                "DIRECTOR")

                                                .requestMatchers(
                                                                "/api/students/**")
                                                .hasRole("DIRECTOR")

                                                // =========================
                                                // TEACHERS
                                                // =========================

                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/teachers/**")
                                                .hasAnyRole(
                                                                "STUDENT",
                                                                "TEACHER",
                                                                "DIRECTOR")

                                                .requestMatchers(
                                                                "/api/teachers/**")
                                                .hasRole("DIRECTOR")

                                                // =========================
                                                // SUBJECTS
                                                // =========================

                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/subjects/**")
                                                .hasAnyRole(
                                                                "STUDENT",
                                                                "TEACHER",
                                                                "DIRECTOR")

                                                .requestMatchers(
                                                                "/api/subjects/**")
                                                .hasRole("DIRECTOR")

                                                // =========================
                                                // SCHOOL GRADES
                                                // =========================

                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/school-grades/**")
                                                .hasAnyRole(
                                                                "STUDENT",
                                                                "TEACHER",
                                                                "DIRECTOR")

                                                .requestMatchers(
                                                                "/api/school-grades/**")
                                                .hasRole("DIRECTOR")

                                                // =========================
                                                // CLASSROOMS
                                                // =========================

                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/classrooms/**")
                                                .hasAnyRole(
                                                                "STUDENT",
                                                                "TEACHER",
                                                                "DIRECTOR")

                                                .requestMatchers(
                                                                "/api/classrooms/**")
                                                .hasRole("DIRECTOR")

                                                // =========================
                                                // ENROLLMENTS
                                                // =========================

                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/enrollments/**")
                                                .hasAnyRole(
                                                                "STUDENT",
                                                                "TEACHER",
                                                                "DIRECTOR")

                                                .requestMatchers(
                                                                "/api/enrollments/**")
                                                .hasRole("DIRECTOR")

                                                // =========================
                                                // TEACHING ASSIGNMENTS
                                                // =========================

                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/teaching-assignments/**")
                                                .hasAnyRole(
                                                                "STUDENT",
                                                                "TEACHER",
                                                                "DIRECTOR")

                                                .requestMatchers(
                                                                "/api/teaching-assignments/**")
                                                .hasRole("DIRECTOR")

                                                // =========================
                                                // ACADEMIC PERIODS
                                                // =========================

                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/academic-periods/**")
                                                .hasAnyRole(
                                                                "STUDENT",
                                                                "TEACHER",
                                                                "DIRECTOR")

                                                .requestMatchers(
                                                                "/api/academic-periods/**")
                                                .hasRole("DIRECTOR")

                                                // =========================
                                                // GRADES
                                                // =========================

                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/grades/**")
                                                .hasAnyRole(
                                                                "STUDENT",
                                                                "TEACHER",
                                                                "DIRECTOR")

                                                .requestMatchers(
                                                                "/api/grades/**")
                                                .hasRole("TEACHER")

                                                // =========================
                                                // ATTENDANCE
                                                // =========================

                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/attendances/**")
                                                .hasAnyRole(
                                                                "STUDENT",
                                                                "TEACHER",
                                                                "DIRECTOR")

                                                .requestMatchers(
                                                                "/api/attendances/**")
                                                .hasRole("TEACHER")

                                                // =========================
                                                // RESTANTE
                                                // =========================

                                                .anyRequest()
                                                .authenticated())

                                .authenticationManager(
                                                authenticationManager)

                                .userDetailsService(
                                                userDetailsService)

                                .addFilterBefore(
                                                jwtAuthFilter,
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}