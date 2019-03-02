package cn.howtoplay.attendance.extension;

import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;

@Component
public class AppRuntimeEnv {

    private ThreadLocal<String> token = ThreadLocal.withInitial(() -> null);


    public AppRuntimeEnv ensureToken(String token) {
        if (null == token) {
            throw new ApplicationException(Response.Status.UNAUTHORIZED, "token失效");
        }
        this.token.set(token);
        return this;
    }


    public void setToken(String token) {
        this.token.set(token);
    }

    public String getToken() {
        return token.get();
    }

}