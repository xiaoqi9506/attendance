package cn.howtoplay.attendance.extension;

import cn.howtoplay.attendance.common.Payload;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * 统一异常处理
 *
 * @author xiaoqi on 2019/3/1
 */
@Provider
@Component
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        e.printStackTrace();
        if (e instanceof ApplicationException) {
            //如果是自定义异常
            String msg = e.getMessage();
            String[] msgs = msg.replaceAll("^@|@^", "").trim().split("@");
            return Response.status(Integer.valueOf(msgs[0].trim()))
                    .entity(new Payload<>(null, msgs[0], msgs[1]))
                    .type(MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new Payload<>(null ,"500", "Internal Server Error."))
                .type(MediaType.APPLICATION_JSON).build();
    }
}
