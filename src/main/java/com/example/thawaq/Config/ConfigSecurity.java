package com.example.thawaq.Config;

import com.example.thawaq.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigSecurity {
    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(getDaoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("api/v1/user/register-client/**").permitAll()
                .requestMatchers("api/v1/user/register-expert/**").permitAll()
                .requestMatchers("api/v1/user/register-store/**").permitAll()
        /* v5 */.requestMatchers("api/v1/rating/get-both-by-cleaning-of-rating","api/v1/rating/get-restaurant-by-cleaning-of-rating","api/v1/rating/get-cafe-by-cleaning-of-rating").permitAll()
                .requestMatchers("api/v1/rating/get-branch-both-by-cleaning-of-rating","api/v1/rating/get-branch-restaurant-by-cleaning-of-rating","api/v1/rating/get-branch-cafe-by-cleaning-of-rating").permitAll()
                .requestMatchers("api/v1/rating/get-menu-both-by-cleaning-of-rating","api/v1/rating/get-menu-restaurant-by-cleaning-of-rating","api/v1/rating/get-menu-cafe-by-cleaning-of-rating").permitAll()
                .requestMatchers("/api/v1/rating/get-rating-by-latest","/api/v1/rating/get-rating-by-high","/api/v1/rating/get-rating-by-low","/api/v1/rating/get-rating-by-comment/{comment}").permitAll()
                .requestMatchers("/api/v1/address/get","/api/v1/branch/get","/api/v1/store/get").permitAll()
                .requestMatchers("api/v1/address/add","api/v1/address/update","api/v1/address/delete/{id}","/api/v1/branch/get-my-branches","/api/v1/branch/add/{storeID}","/api/v1/branch/update/{branchID}","/api/v1/branch/delete/{branchID}","/api/v1/store/get-my-store","/api/v1/store/add","/api/v1/store/update/{id}","/api/v1/store/delete/{id}").hasAuthority("STORE")
                .requestMatchers("api/v1/user/get-all").hasAuthority("ADMIN")
                .requestMatchers("api/v1/branch/add/**").hasAuthority("STORE")
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();

    }
}
