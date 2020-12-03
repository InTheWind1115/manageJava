package cn.edu.njtech.service.impl;

import cn.edu.njtech.domain.SysUser;
import cn.edu.njtech.mapper.RoleMapper;
import cn.edu.njtech.mapper.UserMapper;
import cn.edu.njtech.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    RoleMapper roleMapper;
    @Resource
    UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user = userMapper.findByUsername(s);
        user.setRoles(roleMapper.findByUid(user.getUsername()));
        return user;
    }
}
