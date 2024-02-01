package com.foo.constants;

@SuppressWarnings("squid:S100")
public final class RedisConstant {

    private RedisConstant() {
    }

    public static String SYS_USER_INFO(String userType, String username) {
        return userType.toLowerCase() + ":info:" + username;
    }

    public static String REFRESH_TOKEN(String refreshToken) {
        return "refresh_token:" + refreshToken;
    }

    public static String USER_REFRESH_TOKEN(String userType, String username) {
        return userType.toLowerCase() + ":refresh_token:" + username;
    }

    public static String USER_SALT(String userType, String username) {
        return userType.toLowerCase() + ":salt:" + username;
    }

}
