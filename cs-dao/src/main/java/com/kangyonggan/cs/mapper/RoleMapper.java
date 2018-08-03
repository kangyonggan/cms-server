package com.kangyonggan.cs.mapper;

import com.kangyonggan.common.mybatis.MyMapper;
import com.kangyonggan.cs.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/4/18
 */
@Mapper
public interface RoleMapper extends MyMapper<Role> {

    /**
     * 查找用户角色
     *
     * @param username
     * @return
     */
    List<Role> selectRolesByUsername(@Param("username") String username);

    /**
     * 删除用户角色
     *
     * @param username
     */
    void deleteAllRolesByUsername(@Param("username") String username);

    /**
     * 删除角色菜单
     *
     * @param code
     */
    void deleteRoleMenus(@Param("code") String code);

    /**
     * 插入角色菜单
     *
     * @param code      角色代码
     * @param menuCodes 菜单代码
     */
    void insertRoleMenus(@Param("code") String code, @Param("menuCodes") List<String> menuCodes);
}