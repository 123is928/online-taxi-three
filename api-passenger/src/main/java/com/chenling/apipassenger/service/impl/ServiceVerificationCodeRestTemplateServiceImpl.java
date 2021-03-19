package com.chenling.apipassenger.service.impl;

import com.chenling.apipassenger.service.ServiceVerificationCodeRestTemplateService;
import com.chenling.internalcommon.dto.ResponseResult;
import com.chenling.internalcommon.dto.serviceverificationcode.request.VerifyCodeRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 功能描述
 *
 * @author 陈岭
 * date： 2021-03-14
 * @version 1.0
 */
@Service
public class ServiceVerificationCodeRestTemplateServiceImpl implements ServiceVerificationCodeRestTemplateService {
    private RestTemplate restTemplate;

    public ServiceVerificationCodeRestTemplateServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseResult generatorCode(int identity, String phoneNumber) {
        String url = "http://service-verification-code/verify-code/generate/"+identity+"/"+phoneNumber;

        ResponseResult result = restTemplate.exchange(url, HttpMethod.GET,new HttpEntity<Object>(null,null),ResponseResult.class).getBody();

        return result;
    }

    @Override
    public ResponseResult verifyCode(int identity, String phoneNumber, String code) {
        String url = "http://service-verification-code/verify-code/verify/";

        VerifyCodeRequest request = new VerifyCodeRequest();
        request.setCode(code);
        request.setIdentity(identity);
        request.setPhoneNumber(phoneNumber);

        ResponseResult result = restTemplate.exchange(url, HttpMethod.POST,new HttpEntity<Object>(request,null),ResponseResult.class).getBody();

        return result;
    }
}
