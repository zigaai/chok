package com.zigaai.model.dto.query;

import com.zigaai.model.entity.Role;
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
public class RoleQuery extends Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "分页参数不可为空", groups = QueryGroup.class)
    private Integer current;

    @NotNull(message = "分页参数不可为空", groups = QueryGroup.class)
    private Integer size;

}
