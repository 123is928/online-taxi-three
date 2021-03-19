package com.chenling.servicesms.dao;

import com.chenling.servicesms.entity.ServiceSmsTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ServiceSmsTemplateCustomDao extends ServiceSmsTemplateDao {

    ServiceSmsTemplate selectByTemplateId(@Param("templateId")String templateId);
}
