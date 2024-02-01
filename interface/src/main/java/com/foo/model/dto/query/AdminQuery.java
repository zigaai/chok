package com.foo.model.dto.query;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotNull;
import com.foo.model.validation.QueryGroup;

/**
 * <p>
 * 管理员表 Query
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
@Getter
@Setter
@ToString
public class AdminQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "分页参数不可为空", groups = QueryGroup.class)
    private Integer current;

    @NotNull(message = "分页参数不可为空", groups = QueryGroup.class)
    private Integer size;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 状态: 	0: 正常 	1: 删除 
     */
    private Byte state;

}
