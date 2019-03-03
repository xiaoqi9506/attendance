package cn.howtoplay.attendance.filter;

import cn.howtoplay.attendance.annotation.NeedToken;
import cn.howtoplay.attendance.extension.ApplicationException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.Map;

/**
 * token拦截
 *
 * @author xiaoqi on 2019/3/1
 */

@Provider
@NeedToken
@Component
public class SecurityFilter implements ContainerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void filter(ContainerRequestContext request) {
        Map<String, Cookie> cookies = request.getCookies();
        //从cookie里拿token信息
        Cookie token = cookies.get("token");
        if (null == token ||  StringUtils.isEmpty(token.getValue())) {
            throw new ApplicationException(Response.Status.UNAUTHORIZED, "token为空，请重新登录");
        }
    }
}
