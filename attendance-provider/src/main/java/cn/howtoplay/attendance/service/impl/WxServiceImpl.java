package cn.howtoplay.attendance.service.impl;

import cn.howtoplay.attendance.service.WxService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author xiaoqi on 2019/3/6
 */

@Service
public class WxServiceImpl implements WxService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${wx.authorization.http.url}")
    private String url;

    @Value("${wx.authorization.appid}")
    private String appid;

    @Value("${wx.authorization.secret}")
    private String secret;

    @Value("${wx.authorization.grant_type}")
    private String grantType;

    private final RestTemplate restTemplate;

    @Autowired
    public WxServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Map wxLogin(String code) {
        String finalUrl = url + "?" + "appid=" + appid + "&secret=" + secret + "&grant_type=" + grantType + "&js_code=" + code;
        logger.info("登录请求微信接口=======》url：{}", finalUrl);
        String result = restTemplate.getForObject(finalUrl, String.class);
        logger.info("请求返回结果=======>{}", result);
        return JSONObject.parseObject(result, Map.class);
    }
}
