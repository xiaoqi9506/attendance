package cn.howtoplay.attendance.extension;

import javax.ws.rs.core.Response;
import java.io.Serializable;

/**
 * 自定义异常
 *
 * @author xiaoqi on 2019/3/1
 */
public class ApplicationException extends RuntimeException implements Serializable {

    public ApplicationException() {
        super();
    }

    public ApplicationException(Response.Status status, String msg) {
        super(status.getStatusCode() + "@" + msg);
    }

    public ApplicationException(Response.Status status, String msg, Exception e) {
        super(status.getStatusCode() + "@" + msg, e);
    }
}
