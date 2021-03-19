package com.chenling.apipassenger.service.impl;

import com.chenling.apipassenger.service.ServicePassengerUserService;
import com.chenling.internalcommon.dto.ResponseResult;
import com.chenling.internalcommon.dto.servicepassengeruser.request.LoginRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServicePassengerUserServiceImpl implements ServicePassengerUserService {

    private RestTemplate restTemplate;

    public ServicePassengerUserServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseResult login(String passengerPhone) {
        String url = "http://service-passenger-user/auth/login";

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassengerPhone(passengerPhone);
        ResponseResult result = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<Object>(loginRequest, null), ResponseResult.class).getBody();

        return result;
    }

}
