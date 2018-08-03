package com.kangyonggan.cs.util;

import com.kangyonggan.common.web.ParamsInterceptor;
import com.kangyonggan.cs.model.User;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2018/6/2 0002
 */
public final class AuthUtil {

    /**
     * 用户登录信息
     */
    private static Map<String, User> users = new HashMap<>();

    private AuthUtil() {
    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    public static String saveLoginUser(User user) {
        String token = "sid" + String.valueOf(Math.random()).substring(2);
        users.put(token, user);
        return token;
    }

    /**
     * 判断是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        String token = ParamsInterceptor.getRequest().getHeader("x-access-token");
        if (StringUtils.isEmpty(token)) {
            return false;
        } else if (!users.containsKey(token)) {
            return false;
        }

        return true;
    }

    /**
     * 登出
     */
    public static void logout() {
        String token = ParamsInterceptor.getRequest().getHeader("x-access-token");
        users.remove(token);
    }

    /**
     * 获取当前登录的用户
     */
    public static User currentUser() {
        String token = ParamsInterceptor.getRequest().getHeader("x-access-token");
        return users.get(token);
    }

    /**
     * 获取当前登录的用户ID
     */
    public static Long currentUserId() {
        User user = currentUser();
        return user != null ? user.getId() : 0L;
    }

    /**
     * 获取当前登录的用户名
     */
    public static String currentUsername() {
        User user = currentUser();
        return user != null ? user.getUsername() : null;
    }
}
