package com.zigaai.model.dto.query;

import com.zigaai.validation.QueryGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 页面权限表 Query
 * </p>
 *
 * @author zigaai
 * @since 2023-11-13
 */
@Getter
@Setter
@ToString
public class MenuQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "分页参数不可为空", groups = QueryGroup.class)
    private Integer current;

    @NotNull(message = "分页参数不可为空", groups = QueryGroup.class)
    private Integer size;

    /**
     * 父ID; 0: 根节点
     */
    private Long parentId;

    /**
     * 权限名: 	路由命名规则: AaBbCc, 	按钮权限命名规则: a-b-btn, 	特殊权限命名规则: a:b:c
     */
    private String name;

    /**
     * 标题
     */
    private String title;

    /**
     * 图标名
     */
    private String icon;

    /**
     * 权限类型: 	1: 页面 	2: 按钮 	3: 特殊权限 
     */
    private Byte permissionType;

    /**
     * 路由路径: 	/开头为父路径 	否则为子路径
     */
    private String path;

    /**
     * 路由重定向路径
     */
    private String redirect;

    /**
     * 路由组件路径: "@views/"对应数据库"/", 空串自动匹配
     */
    private String component;

    /**
     * 路由是否固定: 	0: 否 	1: 是 
     */
    private Byte affix;

    /**
     * 排序asc
     */
    private Integer sort;

    /**
     * 是否隐藏: 	0: 否 	1: 是 
     */
    private Byte hide;

    /**
     * 是否需要权限控制: 	0: 否; 	1: 是; 
     */
    private Byte requiresAuth;

    /**
     * 是否缓存页面: 	0: 否; 	1: 是; 
     */
    private Byte keepAlive;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态: 	0: 正常 	1: 删除 
     */
    private Boolean isDeleted;
}
