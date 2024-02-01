package com.foo.model.dto.query;

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
 * 角色表 Query
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
@Getter
@Setter
@ToString
public class RoleQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "分页参数不可为空", groups = QueryGroup.class)
    private Integer current;

    @NotNull(message = "分页参数不可为空", groups = QueryGroup.class)
    private Integer size;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDescription;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态: 	0: 正常 	1: 删除 
     */
    private Boolean isDeleted;
}
