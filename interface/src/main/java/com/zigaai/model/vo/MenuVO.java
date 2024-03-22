package com.zigaai.model.vo;

import com.zigaai.model.entity.Menu;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 页面权限表 VO
 * </p>
 *
 * @author zigaai
 * @since 2023-11-13
 */
@Getter
@Setter
@ToString
public class MenuVO extends Menu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
