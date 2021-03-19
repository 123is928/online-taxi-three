package com.chenling.apipassenger.service;


import com.chenling.internalcommon.dto.ResponseResult;

public interface AuthService {
    ResponseResult auth(String passengerPhone, String code);
}
