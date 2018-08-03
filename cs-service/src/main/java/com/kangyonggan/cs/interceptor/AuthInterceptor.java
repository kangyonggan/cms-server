package com.kangyonggan.cs.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.kangyonggan.app.util.Collections3;
import com.kangyonggan.app.util.SpringUtils;
import com.kangyonggan.common.Resp;
import com.kangyonggan.common.Response;
import com.kangyonggan.cs.annotation.PermissionMenu;
import com.kangyonggan.cs.annotation.PermissionRole;
import com.kangyonggan.cs.annotation.PermissionUser;
import com.kangyonggan.cs.service.MenuService;
import com.kangyonggan.cs.service.RoleService;
import com.kangyonggan.cs.service.impl.MenuServiceImpl;
import com.kangyonggan.cs.util.AuthUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录认证、身份认证
 *
 * @author kangyonggan
 * @since 6/8/18
 */
@Log4j2
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断是否登录
        boolean isLogin = AuthUtil.isLogin();
        if (!isLogin) {
            // 9998: 登录失效
            Response resp = Response.getFailureResponse(Resp.INVALID_LOGIN.getRespCo(), Resp.INVALID_LOGIN.getRespMsg());
            writeResponse(response, resp);
            return false;
        }

        // 判断是否有权限访问
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (!validMenu(response, handlerMethod)) {
                return false;
            }
            if (!validRole(response, handlerMethod)) {
                return false;
            }
            if (!validUser(response, handlerMethod)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 校验菜单权限
     *
     * @param response
     * @param handlerMethod
     * @return
     */
    private boolean validMenu(HttpServletResponse response, HandlerMethod handlerMethod) {
        PermissionMenu permissionMenu = handlerMethod.getMethodAnnotation(PermissionMenu.class);
        if (permissionMenu != null) {
            MenuService menuService = SpringUtils.getBean(MenuServiceImpl.class);
            List<String> menuCodes = menuService.findMenuCodesByUsername(AuthUtil.currentUsername());
            if (!hasPermission(menuCodes, permissionMenu.value())) {
                Response resp = Response.getFailureResponse(Resp.PERMISSION_DENIED.getRespCo(), Resp.PERMISSION_DENIED.getRespMsg());
                writeResponse(response, resp);
                return false;
            }
        }
        return true;
    }

    /**
     * 校验角色权限
     *
     * @param response
     * @param handlerMethod
     * @return
     */
    private boolean validRole(HttpServletResponse response, HandlerMethod handlerMethod) {
        PermissionRole permissionRole = handlerMethod.getMethodAnnotation(PermissionRole.class);
        if (permissionRole != null) {
            RoleService roleService = SpringUtils.getBean(RoleService.class);
            List<String> roleCodes = Collections3.extractToList(roleService.findUserRoles(AuthUtil.currentUsername()), "code");
            if (!hasPermission(roleCodes, permissionRole.value())) {
                Response resp = Response.getFailureResponse(Resp.PERMISSION_DENIED.getRespCo(), Resp.PERMISSION_DENIED.getRespMsg());
                writeResponse(response, resp);
                return false;
            }
        }
        return true;
    }

    /**
     * 校验用户权限
     *
     * @param response
     * @param handlerMethod
     * @return
     */
    private boolean validUser(HttpServletResponse response, HandlerMethod handlerMethod) {
        PermissionUser permissionUser = handlerMethod.getMethodAnnotation(PermissionUser.class);
        if (permissionUser != null) {
            List<String> username = new ArrayList<>();
            username.add(AuthUtil.currentUsername());
            if (!hasPermission(username, permissionUser.value())) {
                Response resp = Response.getFailureResponse(Resp.PERMISSION_DENIED.getRespCo(), Resp.PERMISSION_DENIED.getRespMsg());
                writeResponse(response, resp);
                return false;
            }
        }
        return true;
    }

    /**
     * 写响应
     *
     * @param response
     * @param resp
     */
    private void writeResponse(HttpServletResponse response, Response resp) {
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(JSONObject.toJSONString(resp));
            writer.flush();
        } catch (Exception e) {
            log.error("写响应异常", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 判断是否有权限
     *
     * @param ownCodes 用户拥有的权限
     * @param reqCodes 请求资源需要的权限
     * @return
     */
    public static boolean hasPermission(List<String> ownCodes, String[] reqCodes) {
        for (String code : reqCodes) {
            if (ownCodes.contains(code)) {
                return true;
            }
        }
        return false;
    }
}
