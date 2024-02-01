package com.foo.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import com.foo.model.entity.User;
import com.foo.model.dto.command.UserDTO;
import com.foo.model.vo.UserVO;
import com.foo.model.dto.query.UserQuery;
import com.foo.model.convertor.UserConvertor;
import com.foo.mapper.UserMapper;
import com.foo.service.UserService;

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
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public IPage<UserVO> page(UserQuery params) {
        Page<UserVO> page = new Page<>();
        List<UserVO> records = userMapper.listByCondition(page, params);
        page.setRecords(records);
        return page;
    }

    @Override
    public int add(UserDTO data) {
        User entity = UserConvertor.INSTANCE.toEntity(data);
        return userMapper.insert(entity);
    }

    @Override
    public int update(UserDTO data) {
        User entity = UserConvertor.INSTANCE.toEntity(data);
        return userMapper.updateById(entity);
    }

    @Override
    public int deleteById(Long id) {
        return userMapper.deleteById(id);
    }

}
