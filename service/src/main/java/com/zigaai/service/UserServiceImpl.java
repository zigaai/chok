package com.zigaai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zigaai.mapper.UserMapper;
import com.zigaai.model.convertor.UserConvertor;
import com.zigaai.model.dto.command.UserDTO;
import com.zigaai.model.dto.query.UserQuery;
import com.zigaai.model.entity.User;
import com.zigaai.model.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserMapper userMapper;

    public IPage<UserVO> page(UserQuery params) {
        Page<UserVO> page = new Page<>(params.getCurrent(), params.getSize());
        List<UserVO> records = userMapper.listByCondition(page, params);
        page.setRecords(records);
        return page;
    }

    public int add(UserDTO data) {
        User entity = UserConvertor.INSTANCE.toEntity(data);
        return userMapper.insert(entity);
    }

    public int update(UserDTO data) {
        User entity = UserConvertor.INSTANCE.toEntity(data);
        return userMapper.updateById(entity);
    }

    public int deleteById(Long id) {
        return userMapper.deleteById(id);
    }

}
