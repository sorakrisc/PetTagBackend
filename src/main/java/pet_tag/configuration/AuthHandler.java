package pet_tag.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import pet_tag.service.UserService;

import java.util.ArrayList;

@Component
public class AuthHandler implements AuthenticationProvider {
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Check with Database

        if (userService.authenticate(email, password)){
            return new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
        }


        return null;

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
