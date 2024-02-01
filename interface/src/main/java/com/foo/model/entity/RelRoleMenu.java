package com.foo.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 角色页面权限关联表
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
@Getter
@Setter
@ToString
@TableName("rel_role_menu")
public class RelRoleMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("role_id")
    private Long roleId;

    @TableId("menu_id")
    private Long menuId;

}
