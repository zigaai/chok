package com.zigaai.model.dto.command;

import com.zigaai.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表 DTO
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
@Getter
@Setter
@ToString
public class UserDTO extends User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
