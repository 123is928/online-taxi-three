package com.chenling.serviceverificationcode.service.impl;

import com.chenling.internalcommon.constant.CommonStatusEnum;
import com.chenling.internalcommon.constant.IdentityConstant;
import com.chenling.internalcommon.constant.RedisKeyPrefixConstant;
import com.chenling.internalcommon.dto.ResponseResult;
import com.chenling.internalcommon.dto.serviceverifycode.response.VerifyCodeResponse;
import com.chenling.serviceverificationcode.service.VerifyCodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * 短信服务,生产6位验证码
 *
 * @author 陈岭
 * @version 1.0
 * date： 2021-03-14
 */
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {
    public final RedisTemplate<String,String> redisTemplate;

    public VerifyCodeServiceImpl(RedisTemplate<String,String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public ResponseResult<VerifyCodeResponse> generate(int identity, String phoneNumber) {
        //校验 发送时限，三挡验证，不能无限制发短信
//        checkSendCodeTimeLimit(phoneNumber);

        // 0.9*9=8.1+1 9,去掉首位为0的情况。 0.11225478552211(0.0-<1)
        String code = String.valueOf((int)((Math.random() * 9 + 1) * Math.pow(10,5)));

        /**
         * 有人用这种写法。生成6位code，错误用法，虽然大部分情况下结果正确，但不能这么用，偶尔位数不够？
         */
//        String code = String.valueOf(new Random().nextInt(1000000));

        //生成redis key
        String keyPre = generateKeyPreByIdentity(identity);
        String key = keyPre + phoneNumber;
        //存redis，2分钟过期
        BoundValueOperations<String, String> codeRedis = redisTemplate.boundValueOps(key);

//        Boolean aBoolean = codeRedis.setIfAbsent(code);
//        if (aBoolean){
//            codeRedis.expire(2,TimeUnit.MINUTES);
//        }
        codeRedis.set(code,2,TimeUnit.MINUTES);
//        codeRedis.expire(2,TimeUnit.MINUTES);

        //返回
        VerifyCodeResponse result = new VerifyCodeResponse();
        result.setCode(code);
        return ResponseResult.success(result);
    }

    @Override
    public ResponseResult verify(int identity, String phoneNumber, String code) {
        //三档验证


        //生成redis key
        String keyPre = generateKeyPreByIdentity(identity);
        String key = keyPre + phoneNumber;
        BoundValueOperations<String, String> codeRedis = redisTemplate.boundValueOps(key);
        String redisCode = codeRedis.get();

        if(StringUtils.isNotBlank(code)
                && StringUtils.isNotBlank(redisCode)
                && code.trim().equals(redisCode.trim())) {

            return ResponseResult.success("");
        }else {
            return ResponseResult.fail(CommonStatusEnum.VERIFY_CODE_ERROR.getCode(), CommonStatusEnum.VERIFY_CODE_ERROR.getValue());
        }
    }

    /**
     * 根据身份类型生成对应的缓存key
     * @param identity
     * @return
     */
    private String generateKeyPreByIdentity(int identity){
        String keyPre = "";
        if (identity == IdentityConstant.PASSENGER){
            keyPre = RedisKeyPrefixConstant.PASSENGER_LOGIN_CODE_KEY_PRE;
        }else if (identity == IdentityConstant.DRIVER){
            keyPre = RedisKeyPrefixConstant.DRIVER_LOGIN_CODE_KEY_PRE;
        }
        return keyPre;
    }
}
