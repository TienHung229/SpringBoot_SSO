package com.example.sso;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@SpringBootApplication
public class SsoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
    }
}

/**
 * Controller để xử lý các request web
 */
@Controller
class WebController {

    @GetMapping("/")
    public String home() {
        // Trả về trang chủ, ai cũng có thể truy cập
        return "home";
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal OidcUser oidcUser) {
        // Trả về trang profile, chỉ người đã đăng nhập mới truy cập được
        // Lấy thông tin người dùng từ OIDC token và đưa vào model
        model.addAttribute("profile", oidcUser.getClaims());
        model.addAttribute("profileImage", oidcUser.getPicture());
        return "profile";
    }
}

/**
 * Lớp cấu hình bảo mật cho ứng dụng
 */
@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Value("${auth0.domain}")
    private String domain;

    @Value("${spring.security.oauth2.client.registration.auth0.client-id}")
    private String clientId;

    /**
     * Cấu hình các quy tắc bảo mật
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/").permitAll() // Cho phép truy cập trang chủ
                        .anyRequest().authenticated()      // Bắt buộc đăng nhập cho các trang khác
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/profile", true) // Chuyển hướng đến /profile sau khi đăng nhập thành công
                )
                .logout(logout -> logout
                        .addLogoutHandler(logoutHandler()) // Thêm handler để xử lý đăng xuất khỏi Auth0
                        .logoutSuccessUrl("/")             // Chuyển hướng về trang chủ sau khi đăng xuất
                );
        return http.build();
    }

    /**
     * Tạo một LogoutHandler để xóa phiên đăng nhập trên Auth0.
     * Nếu không có bước này, người dùng chỉ đăng xuất khỏi ứng dụng Spring Boot
     * nhưng vẫn còn phiên đăng nhập trên Auth0.
     */
    private LogoutHandler logoutHandler() {
        return (request, response, authentication) -> {
            try {
                String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
                String logoutUrl = String.format(
                        "https://%s/v2/logout?client_id=%s&returnTo=%s",
                        domain,
                        clientId,
                        baseUrl
                );
                response.sendRedirect(logoutUrl);
            } catch (IOException e) {
                // Xử lý lỗi nếu có
                e.printStackTrace();
            }
        };
    }
}
