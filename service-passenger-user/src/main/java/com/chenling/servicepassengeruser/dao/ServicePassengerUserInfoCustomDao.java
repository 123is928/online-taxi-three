package com.chenling.servicepassengeruser.dao;

import com.chenling.servicepassengeruser.entity.ServicePassengerUserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 功能描述
 *
 * @author 陈岭
 * date： 2021-03-18
 * @version 1.0
 */
@Mapper
public interface ServicePassengerUserInfoCustomDao extends ServicePassengerUserInfoDao {
    /**
     * 根据手机号查询乘客信息
     * @param passengerPhone
     * @return
     */
    ServicePassengerUserInfo selectByPhoneNumber(String passengerPhone);

}
