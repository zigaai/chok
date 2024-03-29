package com.zigaai.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.zigaai.model.security.AuthMenu;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 页面权限表
 * </p>
 *
 * @author zigaai
 * @since 2023-11-13
 */
@Getter
@Setter
@ToString
@TableName("menu")
public class Menu extends AuthMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父ID; 0: 根节点
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 权限名: 	路由命名规则: AaBbCc, 	按钮权限命名规则: a-b-btn, 	特殊权限命名规则: a:b:c
     */
    @TableField("`name`")
    private String name;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 图标名
     */
    @TableField("icon")
    private String icon;

    /**
     * 权限类型: 	1: 页面 	2: 按钮 	3: 特殊权限
     */
    @TableField("permission_type")
    private Byte permissionType;

    /**
     * 路由路径: 	/开头为父路径 	否则为子路径
     */
    @TableField("`path`")
    private String path;

    /**
     * 路由重定向路径
     */
    @TableField("redirect")
    private String redirect;

    /**
     * 路由组件路径: "@views/"对应数据库"/", 空串自动匹配
     */
    @TableField("`component`")
    private String component;

    /**
     * 路由是否固定: 	0: 否 	1: 是
     */
    @TableField("affix")
    private Byte affix;

    /**
     * 排序asc
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 是否隐藏: 	0: 否 	1: 是
     */
    @TableField("hide")
    private Byte hide;

    /**
     * 是否需要权限控制: 	0: 否; 	1: 是;
     */
    @TableField("requires_auth")
    private Byte requiresAuth;

    /**
     * 是否缓存页面: 	0: 否; 	1: 是;
     */
    @TableField("keep_alive")
    private Byte keepAlive;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 状态: 	0: 正常 	1: 删除
     */
    @TableField("deleted")
    private Boolean deleted;

    @Getter
    @ToString
    @RequiredArgsConstructor
    public enum PermissionType {
        /**
         * 菜单
         */
        MENU((byte) 1),

        /**
         * 按钮
         */
        BUTTON((byte) 2),

        /**
         * 特殊权限
         */
        SPECIAL((byte) 3);

        private final byte val;

        private static final Map<Byte, PermissionType> MAP = Collections.unmodifiableMap(Arrays.stream(values()).collect(Collectors.toMap(PermissionType::getVal, Function.identity())));

        public static boolean contains(Byte val) {
            return MAP.containsKey(val);
        }

        public static PermissionType getByVal(Byte val) {
            if (!contains(val)) {
                throw new IllegalArgumentException("非法的权限类型: " + val);
            }
            return MAP.get(val);
        }
    }
}
