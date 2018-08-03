package com.kangyonggan.cs.service;

import com.kangyonggan.common.Params;
import com.kangyonggan.cs.model.User;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2018/5/27 0027
 */
public interface UserService {

    /**
     * 搜索用户
     *
     * @param params
     * @return
     */
    List<User> searchUsers(Params params);

    /**
     * 保存用户
     *
     * @param user
     */
    void saveUser(User user);

    /**
     * 更新用户
     *
     * @param user
     */
    void updateUser(User user);

    /**
     * 判断用户名是否存在
     *
     * @param username
     * @return
     */
    boolean existsUsername(String username);

    /**
     * 更新用户角色
     *
     * @param username
     * @param roleCodes
     */
    void updateUserRoles(String username, String roleCodes);

    /**
     * 删除用户
     *
     * @param id
     */
    void deleteUser(Long id);

    /**
     * 更新密码
     *
     * @param id
     * @param password
     */
    void updatePassword(Long id, String password);

    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 根据用户名查找用户
     *
     * @param id
     * @return
     */
    User findUserById(Long id);

    /**
     * 登录
     *
     * @param user
     * @return
     */
    User login(User user);
}
