package com.zigaai.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.zigaai.model.security.Oauth2RegisteredClientModel;
import com.zigaai.validation.AddGroup;
import com.zigaai.validation.DeleteGroup;
import com.zigaai.validation.UpdateGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 认证客户端
 * </p>
 *
 * @author zigaai
 * @since 2023-07-02
 */
@Slf4j
@Getter
@Setter
@ToString
@TableName("oauth2_registered_client")
public class Oauth2RegisteredClient implements Serializable, Oauth2RegisteredClientModel {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "client_id", type = IdType.INPUT)
    @NotBlank(message = "clientId不可为空", groups = AddGroup.class)
    private String clientId;

    @TableField("client_id_issued_at")
    private Date clientIdIssuedAt;

    @TableField("client_secret")
    private String clientSecret;

    @TableField("client_secret_expires_at")
    private Date clientSecretExpiresAt;

    @TableField("client_name")
    @NotBlank(message = "请填写客户端名字", groups = {AddGroup.class, UpdateGroup.class})
    private String clientName;

    @TableField("client_authentication_methods")
    private String clientAuthenticationMethods;

    @TableField("authorization_grant_types")
    private String authorizationGrantTypes;

    @TableField("redirect_uris")
    @NotBlank(message = "重定向路径(redirectUris)不可为空", groups = {AddGroup.class, UpdateGroup.class})
    private String redirectUris;

    @TableField("scopes")
    @NotBlank(message = "作用域(scopes)不可为空", groups = {AddGroup.class, UpdateGroup.class})
    private String scopes;

    @TableField("client_settings")
    private String clientSettings;

    @TableField("token_settings")
    private String tokenSettings;

    @TableField("post_logout_redirect_uris")
    private String postLogoutRedirectUris;

    @TableField("deleted")
    @TableLogic(value = "true", delval = "false")
    @NotNull(message = "标识(deleted)不可为空", groups = DeleteGroup.class)
    private Boolean deleted;

}
