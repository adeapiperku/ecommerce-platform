package com.ecommerce_platform.service.interf;

// import com.ecommerce_platform.dto.LoginRequest;
import com.ecommerce_platform.dto.Response;
import com.ecommerce_platform.dto.UserDto;
import com.ecommerce_platform.model.User;

public interface UserService {
    Response registerUser(UserDto registrationRequest);
    // Response loginUser(LoginRequest loginRequest);
    Response getAllUsers();
    User getLoginUser();
    Response getUserInfoAndOrderHistory();
}
