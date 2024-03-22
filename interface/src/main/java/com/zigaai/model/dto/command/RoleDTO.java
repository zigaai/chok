package com.zigaai.model.dto.command;

import com.zigaai.model.entity.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 角色表 DTO
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
@Getter
@Setter
@ToString
public class RoleDTO extends Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
