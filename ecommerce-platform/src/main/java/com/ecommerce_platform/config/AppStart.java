package com.ecommerce_platform.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ecommerce_platform.dto.UserDto;
import com.ecommerce_platform.service.interf.UserService;

@RequiredArgsConstructor
@Component
public class AppStart {

    private final UserService userService;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String email = "admin@gmail.com";

        if (userService.findByEmail(email).isEmpty()) {
            UserDto adminDto = new UserDto();
            adminDto.setName("Admin");
            adminDto.setEmail(email);
            adminDto.setPassword("admin");
            adminDto.setPhoneNumber("1231234567");
            adminDto.setRole("admin");

            userService.registerUser(adminDto);
        }
    }
}
