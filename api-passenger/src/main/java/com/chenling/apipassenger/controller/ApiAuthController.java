package com.chenling.apipassenger.controller;

import com.chenling.apipassenger.request.UserAuthRequest;
import com.chenling.apipassenger.service.AuthService;
import com.chenling.internalcommon.dto.ResponseResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 *
 * @author 陈岭
 * date： 2021-03-18
 * @version 1.0
 */
@RestController
@RequestMapping("/auth")
public class ApiAuthController {
    private final AuthService authService;

    public ApiAuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody @Validated UserAuthRequest userAuthRequest) {

        String passengerPhone = userAuthRequest.getPassengerPhone();
        String code = userAuthRequest.getCode();

        return authService.auth(passengerPhone , code);

    }
}
