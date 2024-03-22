package com.zigaai.model.dto.command;

import com.zigaai.model.entity.Oauth2RegisteredClient;
import com.zigaai.validation.DeleteGroup;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * DTO
 * </p>
 *
 * @author zigaai
 * created on 2024-03-18 11:34:08
 */
@Getter
@Setter
@ToString
public class Oauth2RegisteredClientDTO extends Oauth2RegisteredClient implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 客户端secret 明文
     */
    private String plainClientSecret;

    /**
     * access_token过期时间(s)
     */
    private Long accessTokenTimeToLive;

    /**
     * refresh_token过期时间(s)
     */
    private Long refreshTokenTimeToLive = 604800L;

    /**
     * 授权码过期时间(s)
     */
    private Long authorizationCodeTimeToLive = 300L;

    /**
     * 客户端ID列表
     */
    @NotEmpty(message = "请选择客户端", groups = DeleteGroup.class)
    private List<String> clientIds;

}