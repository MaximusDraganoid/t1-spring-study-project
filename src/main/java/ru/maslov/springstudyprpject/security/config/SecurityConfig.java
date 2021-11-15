package ru.maslov.springstudyprpject.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.maslov.springstudyprpject.security.filters.CustomAuthenticationFilter;
import ru.maslov.springstudyprpject.security.filters.CustomAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Value(value = "${secret.word}")
    private String secretWord;

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(authenticationManager(), secretWord);
        CustomAuthorizationFilter authorizationFilter = new CustomAuthorizationFilter(secretWord);

        authenticationFilter.setFilterProcessesUrl("/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //todo: рефакторить, обязательно
        http.authorizeRequests().anyRequest().permitAll();

//
//        http.authorizeRequests().antMatchers("/login/**").permitAll()
//                .and()
//                    .authorizeRequests()
//                    .antMatchers(HttpMethod.POST,"/doctors/**")
//                    .hasAnyAuthority(Role.MAIN_DOCTOR.name(), Role.ADMIN.name())
//                .and()
//                    .authorizeRequests()
//                    .antMatchers(HttpMethod.DELETE,"/doctors/**")
//                    .hasAnyAuthority(Role.MAIN_DOCTOR.name(), Role.ADMIN.name())
//                .and()
//                    .authorizeRequests().antMatchers(HttpMethod.PUT,"/doctors/**").hasAnyAuthority(Role.MAIN_DOCTOR.name(), Role.ADMIN.name(), Role.DOCTOR.name())
//                .and()
//                    .authorizeRequests().antMatchers(HttpMethod.GET,"/doctors/**").hasAnyAuthority(Role.MAIN_DOCTOR.name(), Role.ADMIN.name(), Role.DOCTOR.name())
//                .and()
//                    .authorizeRequests().antMatchers(HttpMethod.POST, "/specialization/**").hasAuthority(Role.ADMIN.name())
//                .and()
//                    .authorizeRequests().antMatchers(HttpMethod.DELETE, "/specialization/**").hasAuthority(Role.ADMIN.name())
//                .and()
//                    .authorizeRequests().antMatchers(HttpMethod.PUT, "/specialization/**").hasAuthority(Role.ADMIN.name())
//                .and()
//                    .authorizeRequests().antMatchers(HttpMethod.GET, "/specialization/**").hasAnyAuthority(Role.ADMIN.name(), Role.MAIN_DOCTOR.name())
//                .and()
//                    .authorizeRequests().antMatchers("/patients/**").hasAnyAuthority(Role.ADMIN.name(), Role.PATIENT.name())
//                .and()
//                    .authorizeRequests().antMatchers(HttpMethod.GET, "/patients/**").hasAnyAuthority(Role.MAIN_DOCTOR.name(), Role.DOCTOR.name())
//                .and()
//                    .authorizeRequests().anyRequest().authenticated();

        http.addFilter(authenticationFilter);
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
