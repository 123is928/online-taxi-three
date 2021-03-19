package com.chenling.servicesms.service;

import com.chenling.internalcommon.dto.ResponseResult;
import com.chenling.internalcommon.dto.servicesms.request.SmsSendRequest;

/**
 * 功能描述
 *
 * @author 陈岭
 * date： 2021-03-15
 * @version 1.0
 */
public interface SmsService {
    /**
     * 发送短信
     * @param request
     * @return
     */
    ResponseResult sendSms(SmsSendRequest request);
}
