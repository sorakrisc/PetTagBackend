package pet_tag.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
//                authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/registration").permitAll()
//                .antMatchers("/user/**").permitAll()
//                .antMatchers("/admin/**").hasAuthority("ADMIN")
//                .anyRequest().authenticated()
//
//                .and()
//                .csrf().disable()
//
//
//                .formLogin().loginProcessingUrl("/login").successHandler(new SuccessAuthentication()).failureHandler(new FailAuthentication())
//                    .usernameParameter("username")
//                    .passwordParameter("password")
//
//                .and()
//                .cors()
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/")
//
//                .and()
//
//                .exceptionHandling().authenticationEntryPoint(new EntryConfig())
//                .accessDeniedPage("/access-denied");        http

                .authorizeRequests()
                .antMatchers("/login", "/logout", "/user/register/**").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .formLogin().loginProcessingUrl("/login")
                .successHandler(new SuccessAuthentication()).failureHandler(new FailAuthentication())
                .and()
                .exceptionHandling().authenticationEntryPoint(new EntryConfig())
                .and()
                .cors()
                .and()
                .logout().logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .permitAll();

        http
                .csrf()
                .disable();

    }

}