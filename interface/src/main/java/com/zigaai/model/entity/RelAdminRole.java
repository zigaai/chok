package com.zigaai.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 管理员角色关联表
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
@Getter
@Setter
@ToString
@TableName("rel_admin_role")
public class RelAdminRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("admin_id")
    private Long adminId;

    @TableId("role_id")
    private Long roleId;

}
