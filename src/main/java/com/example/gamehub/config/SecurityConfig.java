package com.example.gamehub.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/index.html", "/index.js", "/pages/store.html",
                        "/javaScript/store.js",
                        "/pages/cart.html", "/javaScript/cart.js", "/assets/images/**", "/assets/tipografias/**",
                        "/taildwind.config.js", "/style.css", "/styles.css", "/pages/gamedetails.html", "/pages/gamedetails.html?id=*", "/javaScript/gamedetails.js" , "assets/modules/funciones.js").permitAll()
                .requestMatchers("/pages/profile.html", "/javaScript/profile.js").hasAuthority("CUSTOMER")
                .requestMatchers("/pages/admin.html","/javaScript/admin.js").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/customers").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/games").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/purchase").hasAuthority("CUSTOMER")
                .requestMatchers(HttpMethod.GET, "/api/games", "/api/games/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/customers").hasAuthority("CUSTOMER")
                .requestMatchers(HttpMethod.PATCH, "/api/games").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/customers").hasAuthority("CUSTOMER")
                .requestMatchers(HttpMethod.DELETE, "/api/games/*").hasAuthority("ADMIN")

                .requestMatchers("/h2-console/**").permitAll().anyRequest().denyAll());

        http.csrf(AbstractHttpConfigurer::disable);

        http.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(
                HeadersConfigurer.FrameOptionsConfig::disable));

        http.formLogin(formLogin -> formLogin
                .loginPage("/index.html")
                .loginProcessingUrl("/api/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {
                    clearAuthenticationAttributes(request);
                    System.out.println("Welcome, " + authentication.getName() + "!");
                })
                .failureHandler((request, response, exception) -> response.sendError(401))
                .permitAll());

        http.rememberMe(Customizer.withDefaults());

        http.logout(logout -> logout.logoutUrl("/api/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()));

        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint((request, response, authException) ->
                        response.sendError(401)));

        return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

}
