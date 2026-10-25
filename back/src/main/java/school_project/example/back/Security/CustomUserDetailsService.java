package school_project.example.back.Security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import school_project.example.back.Entity.User;
import school_project.example.back.Repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

   @Override
public UserDetails loadUserByUsername(String email)
        throws UsernameNotFoundException {

    User user = userRepository.findByEmail(email)
        .orElseThrow(() ->
            new UsernameNotFoundException(
                "Usuário não encontrado: " + email
            )
        );

    List<GrantedAuthority> authorities =
        new ArrayList<>();

    if (user.getDirector() != null) {
        authorities.add(
            new SimpleGrantedAuthority("ROLE_DIRECTOR")
        );
    }

    if (user.getTeacher() != null) {
        authorities.add(
            new SimpleGrantedAuthority("ROLE_TEACHER")
        );
    }

    if (user.getStudent() != null) {
        authorities.add(
            new SimpleGrantedAuthority("ROLE_STUDENT")
        );
    }

    return org.springframework.security.core.userdetails.User
        .withUsername(user.getEmail())
        .password(user.getPassword())
        .authorities(authorities)

        // importante
        .disabled(!Boolean.TRUE.equals(user.getActive()))

        .build();
}
}