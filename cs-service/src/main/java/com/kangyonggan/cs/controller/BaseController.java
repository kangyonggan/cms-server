package com.kangyonggan.cs.controller;

import com.kangyonggan.common.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author kangyonggan
 * @since 8/2/18
 */
@Log4j2
public class BaseController extends com.kangyonggan.common.web.BaseController {

    /**
     * 异常捕获
     *
     * @param e 异常
     * @return 返回统一异常响应
     */
    @ExceptionHandler
    @ResponseBody
    public Response handleException(Exception e) {
        Response response;
        if (e != null) {
            response = Response.getFailureResponse(e.getMessage());
        } else {
            response = Response.getFailureResponse();
        }

        log.error("捕获到异常", e);
        return response;
    }

}
