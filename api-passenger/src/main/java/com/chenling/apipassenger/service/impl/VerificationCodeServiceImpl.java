package com.chenling.apipassenger.service.impl;

import com.chenling.apipassenger.service.ServiceSmsRestTemplateService;
import com.chenling.apipassenger.service.ServiceVerificationCodeRestTemplateService;
import com.chenling.apipassenger.service.VerificationCodeService;
import com.chenling.internalcommon.constant.CommonStatusEnum;
import com.chenling.internalcommon.constant.IdentityConstant;
import com.chenling.internalcommon.dto.ResponseResult;
import com.chenling.internalcommon.dto.serviceverifycode.response.VerifyCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * 功能描述
 *
 * @author 陈岭
 * date： 2021-03-14
 * @version 1.0
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
    private ServiceVerificationCodeRestTemplateService serviceVerificationCodeRestTemplateService;
    private ServiceSmsRestTemplateService serviceSmsRestTemplateService;

    public VerificationCodeServiceImpl(ServiceVerificationCodeRestTemplateService serviceVerificationCodeRestTemplateService, ServiceSmsRestTemplateService serviceSmsRestTemplateService) {
        this.serviceVerificationCodeRestTemplateService = serviceVerificationCodeRestTemplateService;
        this.serviceSmsRestTemplateService = serviceSmsRestTemplateService;
    }

    @Override
    public ResponseResult send(String phoneNumber) {

        // 获取验证码
        ResponseResult responseResult = serviceVerificationCodeRestTemplateService.generatorCode(IdentityConstant.PASSENGER,phoneNumber);
        VerifyCodeResponse verifyCodeResponse = null;
        if (responseResult.getCode() == CommonStatusEnum.SUCCESS.getCode()){
            JSONObject data = JSONObject.fromObject(responseResult.getData().toString());
            verifyCodeResponse = (VerifyCodeResponse)JSONObject.toBean(data,VerifyCodeResponse.class);

        }else {
            return ResponseResult.fail("获取验证码失败");
        }

        String code = verifyCodeResponse.getCode();

        ResponseResult result = serviceSmsRestTemplateService.sendSms(phoneNumber,code);
        if (result.getCode() != CommonStatusEnum.SUCCESS.getCode()){
            return ResponseResult.fail("发送短信 失败");
        }

        return ResponseResult.success("");

    }

    @Override
    public ResponseResult verify(String phoneNumber, String code) {

        return serviceVerificationCodeRestTemplateService.verifyCode(IdentityConstant.PASSENGER,phoneNumber,code);
    }
}
