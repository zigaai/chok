package com.foo.model.dto.command;

import com.foo.model.entity.Menu;
import com.zigaai.validation.AddGroup;
import com.zigaai.validation.EnumValue;
import com.zigaai.validation.UpdateGroup;
import com.zigaai.enumeration.BoolEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 页面权限表 DTO
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
@Getter
@Setter
@ToString
public class MenuDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "非法的ID值", groups = UpdateGroup.class)
    private Long id;

    /**
     * 父ID; 0: 根节点
     */
    @NotNull(message = "非法的父ID值", groups = {AddGroup.class, UpdateGroup.class})
    private Long parentId;

    /**
     * 权限名: 	路由命名规则: AaBbCc, 	按钮权限命名规则: a-b-btn, 	特殊权限命名规则: a:b:c
     */
    @NotBlank(message = "请输入权限名", groups = AddGroup.class)
    @Size(message = "权限名长度不可超过{max}", max = 100, groups = AddGroup.class)
    private String name;

    /**
     * 标题
     */
    @NotBlank(message = "请输入标题", groups = AddGroup.class)
    @Size(message = "标题长度不可超过{max}", max = 30, groups = AddGroup.class)
    private String title;

    /**
     * 图标名
     */
    @Size(message = "图标长度不可超过{max}", max = 100, groups = AddGroup.class)
    private String icon;

    /**
     * 权限类型: 	1: 页面 	2: 按钮 	3: 特殊权限
     */
    @EnumValue(message = "无效的权限类型", groups = AddGroup.class, enumClass = Menu.PermissionType.class)
    private Byte permissionType;

    /**
     * 路由路径: 	/开头为父路径 	否则为子路径
     */
    @NotBlank(message = "请输入路由路径", groups = AddGroup.class)
    @Size(message = "路由路径长度不可超过{max}", max = 100, groups = AddGroup.class)
    private String path;

    /**
     * 路由重定向路径
     */
    @Size(message = "重定向路径长度不可超过{max}", max = 100, groups = AddGroup.class)
    private String redirect;

    /**
     * 路由组件
     */
    @NotBlank(message = "请填写路由组件", groups = AddGroup.class)
    @Size(message = "路由组件长度不可超过{max}", max = 100, groups = AddGroup.class)
    private String component;

    /**
     * 路由是否固定: 	0: 否 	1: 是
     */
    @EnumValue(groups = AddGroup.class, enumClass = BoolEnum.class)
    private Byte affix;

    /**
     * 排序asc
     */
    @NotNull(message = "排序值不可为空", groups = AddGroup.class)
    private Integer sort;

    /**
     * 是否隐藏: 	0: 否 	1: 是
     */
    @EnumValue(groups = AddGroup.class, enumClass = BoolEnum.class)
    private Byte hide;

    /**
     * 是否缓存页面: 	0: 否; 	1: 是;
     */
    @EnumValue(groups = AddGroup.class, enumClass = BoolEnum.class)
    private Byte keepAlive;

    /**
     * 是否需要权限控制: 	0: 否; 	1: 是;
     */
    @EnumValue(groups = AddGroup.class, enumClass = BoolEnum.class)
    private Byte requiresAuth;
}
