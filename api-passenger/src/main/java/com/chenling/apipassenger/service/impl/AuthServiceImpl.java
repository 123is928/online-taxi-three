package com.chenling.apipassenger.service.impl;

import com.chenling.apipassenger.service.AuthService;
import com.chenling.apipassenger.service.ServicePassengerUserService;
import com.chenling.apipassenger.service.ServiceVerificationCodeRestTemplateService;
import com.chenling.internalcommon.constant.CommonStatusEnum;
import com.chenling.internalcommon.constant.IdentityConstant;
import com.chenling.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录
 *
 * @author 陈岭
 * date： 2021-03-18
 * @version 1.0
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final ServiceVerificationCodeRestTemplateService serviceVerificationCodeRestTemplateService;

    private final ServicePassengerUserService servicePassengerUserService;

    public AuthServiceImpl(ServiceVerificationCodeRestTemplateService serviceVerificationCodeRestTemplateService, ServicePassengerUserService servicePassengerUserService) {
        this.serviceVerificationCodeRestTemplateService = serviceVerificationCodeRestTemplateService;
        this.servicePassengerUserService = servicePassengerUserService;
    }

    @Override
    public ResponseResult auth(String passengerPhone, String code) {
        // 验证验证码：
        ResponseResult responseResult = serviceVerificationCodeRestTemplateService.verifyCode(IdentityConstant.PASSENGER,passengerPhone,code);
        if (responseResult.getCode() != CommonStatusEnum.SUCCESS.getCode()){
            return ResponseResult.fail("登录失败：验证码校验失败");
        }

        // 用户登录
        responseResult = servicePassengerUserService.login(passengerPhone);
        if (responseResult.getCode() != CommonStatusEnum.SUCCESS.getCode()){
            return ResponseResult.fail("登录失败：登录失败");
        }

        return responseResult;
    }
}
