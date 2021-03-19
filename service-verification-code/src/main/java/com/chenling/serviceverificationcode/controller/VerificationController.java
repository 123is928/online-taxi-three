package com.chenling.serviceverificationcode.controller;

import com.chenling.internalcommon.dto.ResponseResult;
import com.chenling.internalcommon.dto.serviceverificationcode.request.VerifyCodeRequest;
import com.chenling.serviceverificationcode.service.VerifyCodeService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

/**
 * 发送短信
 *
 * @author 陈岭
 * @version 1.0
 * date： 2021-03-14
 */
@Slf4j
@RestController
@RequestMapping("/verify-code")
public class VerificationController {
    private VerifyCodeService verifyCodeService;

    public VerificationController(VerifyCodeService verifyCodeService) {
        this.verifyCodeService = verifyCodeService;
    }

    @GetMapping("/generate/{identity}/{phoneNumber}")
    public ResponseResult generate(@PathVariable("identity") int identity , @PathVariable ("phoneNumber") String phoneNumber) {
        log.info("/generate/{identity}/{phoneNumber} ： 身份类型："+identity+",手机号："+phoneNumber);
        // 校验参数

        return verifyCodeService.generate(identity,phoneNumber);
    }

    @PostMapping("/verify")
    public ResponseResult verify(@RequestBody VerifyCodeRequest request) {
        log.info("/verify-code/verify  request:"+ JSONObject.fromObject(request));
        //获取手机号和验证码
        String phoneNumber = request.getPhoneNumber();
        int identity = request.getIdentity();
        String code = request.getCode();

        return verifyCodeService.verify(identity,phoneNumber,code);

    }
}
