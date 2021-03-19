package com.chenling.apipassenger.controller;

import com.chenling.apipassenger.request.ShortMsgRequest;
import com.chenling.apipassenger.service.VerificationCodeService;
import com.chenling.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发送验证码控制器
 *
 * @author 陈岭
 * date： 2021-03-14
 * @version 1.0
 */
@RestController
@RequestMapping("/verify-code")
public class VerificationCodeController {
    @Autowired
    private VerificationCodeService verificationCodeService;

    @PostMapping("/send")
    public ResponseResult send(@RequestBody @Validated ShortMsgRequest request){

        return verificationCodeService.send(request.getPhoneNumber());
    }
}
