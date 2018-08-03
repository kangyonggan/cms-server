package com.kangyonggan.cs.controller;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.app.util.Collections3;
import com.kangyonggan.common.Response;
import com.kangyonggan.cs.annotation.PermissionMenu;
import com.kangyonggan.cs.annotation.PermissionUser;
import com.kangyonggan.cs.model.Role;
import com.kangyonggan.cs.model.User;
import com.kangyonggan.cs.service.RoleService;
import com.kangyonggan.cs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kangyonggan
 * @date 2018/5/27 0027
 */
@RestController
@RequestMapping("system/user")
public class SystemUserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 用户列表
     *
     * @return
     */
    @GetMapping
    @PermissionMenu("user")
    public Response list() {
        Response response = Response.getSuccessResponse();

        List<User> users = userService.searchUsers(getRequestParams());
        PageInfo pageInfo = new PageInfo<>(users);

        response.put("pageInfo", pageInfo);
        return response;
    }

    /**
     * 恢复/禁用
     *
     * @param id
     * @param status
     * @return
     */
    @PutMapping(value = "{id:[\\d]+}/status/{status:\\b0\\b|\\b1\\b}")
    @PermissionMenu("user")
    public Response status(@PathVariable("id") Long id, @PathVariable("status") byte status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        userService.updateUser(user);
        return Response.getSuccessResponse();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping
    @PermissionMenu("user")
    @PermissionUser("admin")
    public Response delete(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return Response.getSuccessResponse();
    }

    /**
     * 保存
     *
     * @param user
     * @return
     */
    @PostMapping
    @PermissionMenu("user")
    public Response save(User user) {
        userService.saveUser(user);
        return Response.getSuccessResponse();
    }

    /**
     * 更新
     *
     * @param user
     * @return
     */
    @PutMapping
    @PermissionMenu("user")
    public Response update(User user) {
        User u = new User();
        u.setId(user.getId());
        u.setName(user.getName());
        userService.updateUser(u);
        return Response.getSuccessResponse();
    }

    /**
     * 查找用户角色
     *
     * @param username
     * @return
     */
    @GetMapping(value = "{username:[\\w]+}/role")
    @PermissionMenu("user")
    public Response role(@PathVariable("username") String username) {
        Response response = Response.getSuccessResponse();
        List<Role> roles = roleService.findUserRoles(username);

        response.put("roleCodes", Collections3.extractToList(roles, "code"));
        return response;
    }

    /**
     * 更新用户角色
     *
     * @param username
     * @param roleCodes
     * @return
     */
    @PutMapping(value = "{username:[\\w]+}/role")
    @PermissionMenu("user")
    public Response role(@PathVariable("username") String username,
                         @RequestParam(value = "roleCodes", required = false, defaultValue = "") String roleCodes) {
        userService.updateUserRoles(username, roleCodes);

        return Response.getSuccessResponse();
    }

    /**
     * 更新密码
     *
     * @param id
     * @param password
     * @return
     */
    @PutMapping(value = "{id:[\\d]+}/password")
    @PermissionMenu("user")
    @PermissionUser("admin")
    public Response role(@PathVariable("id") Long id, @RequestParam("password") String password) {
        userService.updatePassword(id, password);

        return Response.getSuccessResponse();
    }

}
