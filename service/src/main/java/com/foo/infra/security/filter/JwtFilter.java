package com.foo.infra.security.filter;

import com.foo.enumeration.SysUserType;
import com.foo.infra.exception.JwtExpiredException;
import com.foo.infra.security.MultiAuthenticationUserDetailsService;
import com.foo.infra.security.SecurityUtil;
import com.foo.infra.security.model.CustomSecurityProperties;
import com.foo.infra.security.model.PayloadDTO;
import com.foo.infra.security.model.SystemUser;
import com.foo.infra.strategy.StrategyFactory;
import com.foo.infra.utils.JWTUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final StrategyFactory<SysUserType, MultiAuthenticationUserDetailsService> userDetailsServiceStrategy;

    private final CustomSecurityProperties securityProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (securityProperties.getIgnoreUrls().contains(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }
        String token = SecurityUtil.getTokenVal(request);
        if (StringUtils.isBlank(token)) {
            chain.doFilter(request, response);
            return;
        }
        SystemUser systemUser;
        try {
            Pair<JWSObject, PayloadDTO> pair = JWTUtil.parseUnverified(token);
            PayloadDTO payload = pair.getRight();
            systemUser = (SystemUser) userDetailsServiceStrategy.getStrategy(payload.getUserType()).loadUserByUsername(payload.getUsername());
            JWTUtil.check(pair.getLeft(), payload, systemUser.getSalt());
        } catch (ParseException | JOSEException | JwtExpiredException e) {
            log.info("解析token失败: ", e);
            chain.doFilter(request, response);
            return;
        }
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(systemUser, null, systemUser.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

}