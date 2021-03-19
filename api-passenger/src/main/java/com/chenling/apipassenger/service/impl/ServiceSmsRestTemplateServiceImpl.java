package com.chenling.apipassenger.service.impl;

import com.chenling.apipassenger.service.ServiceSmsRestTemplateService;
import com.chenling.internalcommon.dto.ResponseResult;
import com.chenling.internalcommon.dto.servicesms.SmsTemplateDto;
import com.chenling.internalcommon.dto.servicesms.request.SmsSendRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 功能描述
 *
 * @author 陈岭
 * date： 2021-03-14
 * @version 1.0
 */
@Service
public class ServiceSmsRestTemplateServiceImpl implements ServiceSmsRestTemplateService {
    private RestTemplate restTemplate;

    public ServiceSmsRestTemplateServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseResult sendSms(String phoneNumber, String code) {
        String http = "http://";
        String serviceName = "SERVICE-SMS";
        String uri = "/send/sms-template";

        String url = http + serviceName + uri;
        SmsSendRequest smsSendRequest = new SmsSendRequest();
        String[] phoneNumbers = new String[] {phoneNumber};
        smsSendRequest.setReceivers(phoneNumbers);

        List<SmsTemplateDto> data = new ArrayList<SmsTemplateDto>();
        SmsTemplateDto dto = new SmsTemplateDto();
        dto.setId("SMS_144145499");
        int templateSize = 1;
        HashMap<String, Object> templateMap = new HashMap<String, Object>(templateSize);
        templateMap.put("code", code);
        dto.setTemplateMap(templateMap);
        data.add(dto);

        smsSendRequest.setData(data);

        return restTemplate.postForEntity(url, smsSendRequest, ResponseResult.class).getBody();
    }
}
