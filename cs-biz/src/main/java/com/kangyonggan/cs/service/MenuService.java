package com.kangyonggan.cs.service;


import com.kangyonggan.cs.model.Menu;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2018/5/27 0027
 */
public interface MenuService {

    /**
     * 查找用户菜单
     *
     * @param username
     * @return
     */
    List<Menu> findMenusByUsername(String username);

    /**
     * 查找用户菜单代码
     *
     * @param username
     * @return
     */
    List<String> findMenuCodesByUsername(String username);

    /**
     * 查找所有菜单
     *
     * @return
     */
    List<Menu> findAllMenus();

    /**
     * 保存菜单
     *
     * @param menu
     */
    void saveMenu(Menu menu);

    /**
     * 校验菜单代码是否存在
     *
     * @param code
     * @return
     */
    boolean existsMenuCode(String code);

    /**
     * 删除菜单
     *
     * @param code
     */
    void deleteMenu(String code);

    /**
     * 更新菜单
     *
     * @param menu
     */
    void updateMenu(Menu menu);

    /**
     * 查找角色菜单
     *
     * @param code
     * @return
     */
    List<String> findMenuCodesByRoleCode(String code);

}
