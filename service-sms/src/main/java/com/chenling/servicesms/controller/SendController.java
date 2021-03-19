package com.chenling.servicesms.controller;

import com.chenling.internalcommon.dto.ResponseResult;
import com.chenling.internalcommon.dto.servicesms.request.SmsSendRequest;
import com.chenling.servicesms.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发送短信
 *
 * @author 陈岭
 * date： 2021-03-15
 * @version 1.0
 */
@RestController
@RequestMapping("/send")
@Slf4j
public class SendController {
    private SmsService smsService;

    public SendController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/sms-template")
    public ResponseResult send(@RequestBody SmsSendRequest smsSendRequest){
        //输出收到的参数内容
        JSONObject param = JSONObject.fromObject(smsSendRequest);
        log.info("/send/alisms-template   request："+param.toString());
        return smsService.sendSms(smsSendRequest);
    }
}
