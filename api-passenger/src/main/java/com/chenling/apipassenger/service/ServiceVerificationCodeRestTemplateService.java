package com.chenling.apipassenger.service;

import com.chenling.internalcommon.dto.ResponseResult;

public interface ServiceVerificationCodeRestTemplateService {

    public ResponseResult generatorCode(int identity, String phoneNumber);

    public ResponseResult verifyCode(int identity, String phoneNumber , String code);
}
