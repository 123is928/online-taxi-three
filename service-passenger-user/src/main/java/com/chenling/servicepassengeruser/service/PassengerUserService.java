package com.chenling.servicepassengeruser.service;

import com.chenling.internalcommon.dto.ResponseResult;

/**
 * 功能描述
 *
 * @author 陈岭
 * date： 2021-03-18
 * @version 1.0
 */
public interface PassengerUserService {
    ResponseResult login(String passengerPhone);

    ResponseResult logout(String token);
}
