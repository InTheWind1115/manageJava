package cn.edu.njtech.mapper;


import cn.edu.njtech.domain.SysRole;

import java.util.List;

public interface RoleMapper {
    public List<SysRole> findByUid(String userId);
}
