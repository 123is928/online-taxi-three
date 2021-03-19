package com.chenling.apipassenger.service;

import com.chenling.internalcommon.dto.ResponseResult;

public interface VerificationCodeService {
    ResponseResult send(String phoneNumber);
    ResponseResult verify(String phoneNumber, String code);
}
