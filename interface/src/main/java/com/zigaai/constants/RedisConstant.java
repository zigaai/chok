package com.zigaai.constants;

@SuppressWarnings("squid:S100")
public final class RedisConstant {

    private RedisConstant() {
    }

    public static String SYS_USER_INFO(String userType, String username) {
        return userType.toLowerCase() + ":info:" + username;
    }

    public static String USER_SALT(String userType, String username) {
        return userType.toLowerCase() + ":salt:" + username;
    }

}
