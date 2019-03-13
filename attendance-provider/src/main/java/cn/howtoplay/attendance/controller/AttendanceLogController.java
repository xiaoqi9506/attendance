package cn.howtoplay.attendance.controller;

import cn.howtoplay.attendance.common.Payload;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * @author xiaoqi on 2019/3/7
 */

@Path("/api/v1/attendancelogs")
@Service
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AttendanceLogController {

    @GET
    @Path("/")
    public Payload list() {
        return null;
    }

    @POST
    @Path("/")
    public Payload start(@QueryParam("courseId") String courseId) {
        return new Payload(null);
    }
}
