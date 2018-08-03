package com.kangyonggan.cs.controller;

import com.kangyonggan.common.Resp;
import com.kangyonggan.common.Response;
import com.kangyonggan.cs.model.User;
import com.kangyonggan.cs.service.MenuService;
import com.kangyonggan.cs.service.RoleService;
import com.kangyonggan.cs.service.UserService;
import com.kangyonggan.cs.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kangyonggan
 * @since 5/29/18
 */
@RestController
@RequestMapping("validate")
public class ValidateController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 判断是否存在用户名
     *
     * @param username
     * @return
     */
    @GetMapping(value = "user")
    public Response user(@RequestParam("username") String username) {
        Response response = Response.getSuccessResponse();
        if (userService.existsUsername(username)) {
            response.failure();
        }
        return response;
    }

    /**
     * 判断是否存在角色代码
     *
     * @param code
     * @return
     */
    @GetMapping(value = "role")
    public Response role(@RequestParam("code") String code) {
        Response response = Response.getSuccessResponse();
        if (roleService.existsRoleCode(code)) {
            response.failure();
        }
        return response;
    }

    /**
     * 判断是否存在菜单代码
     *
     * @param code
     * @return
     */
    @GetMapping(value = "menu")
    public Response menu(@RequestParam("code") String code) {
        Response response = Response.getSuccessResponse();
        if (menuService.existsMenuCode(code)) {
            response.failure();
        }
        return response;
    }

    /**
     * 校验密码是否正确
     *
     * @param password
     * @return
     */
    @PostMapping(value = "password")
    public Response password(@RequestParam("password") String password) {
        Response response = Response.getSuccessResponse();
        User user = userService.findUserByUsername(AuthUtil.currentUsername());
        if (user == null) {
            response.failure(Resp.INVALID_LOGIN.getRespCo(), Resp.INVALID_LOGIN.getRespMsg());
            return response;
        }
        user.setPassword(password);
        user = userService.login(user);
        if (user == null) {
            response.failure(Resp.PASSWORD_ERROR.getRespCo(), Resp.PASSWORD_ERROR.getRespMsg());
        }
        return response;
    }

}
