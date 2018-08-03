package com.kangyonggan.cs.controller;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.common.Response;
import com.kangyonggan.cs.annotation.PermissionMenu;
import com.kangyonggan.cs.model.Role;
import com.kangyonggan.cs.service.MenuService;
import com.kangyonggan.cs.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kangyonggan
 * @date 2018/5/27 0027
 */
@RestController
@RequestMapping("system/role")
public class SystemRoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 查找所有角色
     *
     * @return
     */
    @GetMapping("all")
    @PermissionMenu({"role", "user"})
    public Response all() {
        Response response = Response.getSuccessResponse();

        response.put("roles", roleService.findAllRoles());
        return response;
    }

    /**
     * 角色列表
     *
     * @return
     */
    @GetMapping
    @PermissionMenu("role")
    public Response list() {
        Response response = Response.getSuccessResponse();

        List<Role> roles = roleService.searchRoles(getRequestParams());
        PageInfo pageInfo = new PageInfo<>(roles);

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
    @PermissionMenu("role")
    public Response status(@PathVariable("id") Long id, @PathVariable("status") byte status) {
        Role role = new Role();
        role.setId(id);
        role.setStatus(status);
        roleService.updateRole(role);
        return Response.getSuccessResponse();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping
    @PermissionMenu("role")
    public Response delete(@RequestParam("id") Long id) {
        roleService.deleteRole(id);
        return Response.getSuccessResponse();
    }

    /**
     * 保存
     *
     * @param role
     * @return
     */
    @PostMapping
    @PermissionMenu("role")
    public Response save(Role role) {
        roleService.saveRole(role);
        return Response.getSuccessResponse();
    }

    /**
     * 更新
     *
     * @param role
     * @return
     */
    @PutMapping
    @PermissionMenu("role")
    public Response update(Role role) {
        Role r = new Role();
        r.setId(role.getId());
        r.setName(role.getName());
        roleService.updateRole(r);
        return Response.getSuccessResponse();
    }

    /**
     * 获取角色菜单
     *
     * @param code
     * @return
     */
    @GetMapping("{code:[\\w]+}/menu")
    @PermissionMenu("role")
    public Response getRoleMenus(@PathVariable("code") String code) {
        Response response = Response.getSuccessResponse();
        List<String> codes = menuService.findMenuCodesByRoleCode(code);

        response.put("selectedCodes", codes);
        return response;
    }

    /**
     * 更新角色菜单
     *
     * @param code
     * @param menuCodes
     * @return
     */
    @PutMapping("{code:[\\w]+}/menu")
    @PermissionMenu("role")
    public Response updateRoleMenus(@PathVariable("code") String code, @RequestParam("menuCodes") String menuCodes) {
        roleService.updateRoleMenus(code, menuCodes);

        return Response.getSuccessResponse();
    }

}
