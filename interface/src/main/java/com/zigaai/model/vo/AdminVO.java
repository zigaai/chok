package com.zigaai.model.vo;

import com.zigaai.model.entity.Admin;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 管理员表 VO
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
@Getter
@Setter
@ToString
public class AdminVO extends Admin implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
