package com.chenling.servicesms.service.impl;

import com.chenling.internalcommon.dto.ResponseResult;
import com.chenling.internalcommon.dto.servicesms.SmsTemplateDto;
import com.chenling.internalcommon.dto.servicesms.request.SmsSendRequest;
import com.chenling.servicesms.constant.SmsStatusEnum;
import com.chenling.servicesms.dao.ServiceSmsRecordDao;
import com.chenling.servicesms.dao.ServiceSmsTemplateCustomDao;
import com.chenling.servicesms.entity.ServiceSmsRecord;
import com.chenling.servicesms.entity.ServiceSmsTemplate;
import com.chenling.servicesms.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述
 *
 * @author 陈岭
 * date： 2021-03-15
 * @version 1.0
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {
    /**
     * 缓存用于替换内容的模板
     */
    private final Map<String, String> templateMaps = new HashMap<String, String>();

    private final ServiceSmsTemplateCustomDao serviceSmsTemplateCustomDao;

    private final ServiceSmsRecordDao serviceSmsRecordDao;

    public SmsServiceImpl(ServiceSmsTemplateCustomDao serviceSmsTemplateCustomDao, ServiceSmsRecordDao serviceSmsRecordDao) {
        this.serviceSmsTemplateCustomDao = serviceSmsTemplateCustomDao;
        this.serviceSmsRecordDao = serviceSmsRecordDao;
    }

    @Override
    public ResponseResult sendSms(SmsSendRequest request) {
        for (String phoneNumber : request.getReceivers()) {
            List<SmsTemplateDto> templates = request.getData();

            ServiceSmsRecord sms = new ServiceSmsRecord();
            sms.setPhoneNumber(phoneNumber);
            // 81B*10 1024 1K 10*1024  1M
            for (SmsTemplateDto template : templates) {
                // 从DB加载模板内容至缓存
                if (!templateMaps.containsKey(template.getId())) {
                    //此处注释掉的内容为，将db模板加载到内存
                    ServiceSmsTemplate t = serviceSmsTemplateCustomDao.selectByTemplateId(template.getId());
                    System.out.println(t.getTemplateContent());
                    templateMaps.put(template.getId(),
                            serviceSmsTemplateCustomDao.selectByTemplateId(template.getId()).getTemplateContent());
                }

                // 替换占位符
                String content = templateMaps.get(template.getId());
                for (Map.Entry<String, Object> entry : template.getTemplateMap().entrySet()) {
                    content = StringUtils.replace(content, "${" + entry.getKey() + "}", "" + entry.getValue());
                }

                // 发生错误时，不影响其他手机号和模板的发送
                try {
                    int result = send(phoneNumber, template.getId(), template.getTemplateMap());

                    // 组装SMS对象
                    sms.setSendTime(new Date());
                    sms.setOperatorName("");
                    sms.setSendFlag(1);
                    sms.setSendNumber(0);
                    sms.setSmsContent(content);

                    if (result != SmsStatusEnum.SEND_SUCCESS.getCode()) {
                        throw new Exception("短信发送失败");
                    }
                } catch (Exception e) {
                    sms.setSendFlag(0);
                    sms.setSendNumber(1);
                    log.error("发送短信（" + template.getId() + "）失败：" + phoneNumber, e);
                } finally {
                    sms.setCreateTime(new Date());
                    serviceSmsRecordDao.insert(sms);
                }
            }
        }
        return ResponseResult.success("");
    }

    private int send(String phoneNumber, String templateId, Map<String, ?> map) throws Exception {
        JSONObject param = new JSONObject();
        param.putAll(map);

        return sendMsg(phoneNumber, templateId, param.toString());
    }

    private int sendMsg(String phoneNumber, String templateCode, String param) {

        /**
         *  供应商 发 短信
         */
        return SmsStatusEnum.SEND_SUCCESS.getCode();

    }
}
