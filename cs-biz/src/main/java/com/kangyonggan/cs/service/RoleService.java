package com.kangyonggan.cs.service;


import com.kangyonggan.common.Params;
import com.kangyonggan.cs.model.Role;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2018/5/27 0027
 */
public interface RoleService {

    /**
     * 查找用户角色
     *
     * @param username
     * @return
     */
    List<Role> findUserRoles(String username);

    /**
     * 查找所有角色
     *
     * @return
     */
    List<Role> findAllRoles();

    /**
     * 搜索角色
     *
     * @param params
     * @return
     */
    List<Role> searchRoles(Params params);

    /**
     * 更新角色
     *
     * @param role
     */
    void updateRole(Role role);

    /**
     * 删除角色
     *
     * @param id
     */
    void deleteRole(Long id);

    /**
     * 保存角色
     *
     * @param role
     */
    void saveRole(Role role);

    /**
     * 校验就是代码是否存在
     *
     * @param code
     * @return
     */
    boolean existsRoleCode(String code);

    /**
     * 更新角色菜单
     *
     * @param code
     * @param menuCodes
     */
    void updateRoleMenus(String code, String menuCodes);
}
