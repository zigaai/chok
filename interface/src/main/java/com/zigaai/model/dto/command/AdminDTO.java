package com.zigaai.model.dto.command;

import com.zigaai.model.entity.Admin;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 管理员表 DTO
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
@Getter
@Setter
@ToString
public class AdminDTO extends Admin implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
