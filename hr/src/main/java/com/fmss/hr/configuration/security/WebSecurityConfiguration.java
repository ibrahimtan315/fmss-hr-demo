package com.fmss.hr.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors();

        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/users/**").permitAll()
                .antMatchers("/candidates/**").permitAll()
                .antMatchers("/events/**").permitAll()
                .antMatchers("/adverts/**").permitAll()
                .antMatchers("/expense/**").permitAll()
                .antMatchers("/oldEvents/**").permitAll()
                .antMatchers("/upcomingMeetings/**").permitAll()
                .antMatchers("/overtime/**").permitAll()
                .antMatchers("/timesheets/**").permitAll()
                .antMatchers("/leave/**").permitAll()
                .antMatchers("/upcomingHolidays/**").permitAll()
                .antMatchers("/announcements/**").permitAll()
                .antMatchers("/survey/**").permitAll()
                .anyRequest().authenticated();
                
        http.exceptionHandling().accessDeniedPage("/users/login");

        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));


//        http.formLogin()
//                .loginPage("/users/login");
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/api/login")
                .antMatchers("/v2/api-docs")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/swagger-ui/**")
                .antMatchers("/configuration/**")
                .antMatchers("/webjars/**")
                .antMatchers("/public")
                .antMatchers("/images/**")
                .antMatchers("/users/**")
                .antMatchers("/users/login")
                .antMatchers("/users/sign-up")
                .antMatchers("/candidates/**")
                .antMatchers("/candidates/list/**")
                .antMatchers("/candidates/suggest")
                .antMatchers("candidates/files/**")
                .antMatchers("/events/**")
                .antMatchers("/oldEvents/**")
                .antMatchers("/upcomingMeetings/**")
                .antMatchers("/send-mail-to-candidate/**")
                .antMatchers("/send-mail-to-user/**")
                .antMatchers("/adverts/**")
                .antMatchers("/expense/**")
                .antMatchers("/departments/**")
                .antMatchers("/adverts/**")
                .antMatchers("/overtime/**")
                .antMatchers("/timesheets/**")
                .antMatchers("/leave/**")
                .antMatchers("/upcomingHolidays/**")
                .antMatchers("/announcements/**")
                .antMatchers("/survey/**")
                .and()
                .ignoring()
                .antMatchers("/h2-console/**/**");

        web.httpFirewall(new DefaultHttpFirewall());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://localhost:3000", "http://10.134.3.16:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
