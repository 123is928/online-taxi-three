package com.chenling.servicepassengeruser.controller;

import com.chenling.internalcommon.dto.ResponseResult;
import com.chenling.internalcommon.dto.servicepassengeruser.request.LoginRequest;
import com.chenling.servicepassengeruser.service.PassengerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录
 *
 * @author 陈岭
 * date： 2021-03-18
 * @version 1.0
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PassengerUserService passengerUserService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginRequest request){

        String passengerPhone = request.getPassengerPhone();
        return passengerUserService.login(passengerPhone);
    }

    /**
     *
     * @param token
     * @return
     * @throws Exception
     */
    @PostMapping("/logout")
    public ResponseResult logout(String token) throws Exception{
        passengerUserService.logout(token);
        return ResponseResult.success("");
    }
}
