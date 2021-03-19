package com.chenling.apipassenger.service;

import com.chenling.internalcommon.dto.ResponseResult;

public interface ServiceSmsRestTemplateService {
    public ResponseResult sendSms(String phoneNumber , String code);
}
