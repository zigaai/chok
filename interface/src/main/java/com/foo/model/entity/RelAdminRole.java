package com.foo.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
