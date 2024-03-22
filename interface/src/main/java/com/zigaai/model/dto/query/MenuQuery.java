package com.zigaai.model.dto.query;

import com.zigaai.model.entity.Menu;
import com.zigaai.validation.QueryGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

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
public class MenuQuery extends Menu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "分页参数不可为空", groups = QueryGroup.class)
    private Integer current;

    @NotNull(message = "分页参数不可为空", groups = QueryGroup.class)
    private Integer size;
}
