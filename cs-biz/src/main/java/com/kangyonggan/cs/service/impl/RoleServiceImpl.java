package com.kangyonggan.cs.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.util.StringUtil;
import com.kangyonggan.common.BaseService;
import com.kangyonggan.common.Params;
import com.kangyonggan.common.Status;
import com.kangyonggan.cs.mapper.RoleMapper;
import com.kangyonggan.cs.model.Role;
import com.kangyonggan.cs.service.RoleService;
import com.kangyonggan.extra.core.annotation.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2018/5/27 0027
 */
@Service
public class RoleServiceImpl extends BaseService<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Log
    public List<Role> findUserRoles(String username) {
        return roleMapper.selectRolesByUsername(username);
    }

    @Override
    @Log
    public List<Role> findAllRoles() {
        Role role = new Role();
        role.setStatus(Status.ENABLE.getCode());

        return myMapper.select(role);
    }

    @Override
    public List<Role> searchRoles(Params params) {
        Example example = new Example(Role.class);

        Example.Criteria criteria = example.createCriteria();
        String code = params.getQuery().getString("code");
        if (StringUtils.isNotEmpty(code)) {
            criteria.andLike("code", StringUtil.toLike(code));
        }
        String name = params.getQuery().getString("name");
        if (StringUtils.isNotEmpty(name)) {
            criteria.andLike("name", StringUtil.toLike(name));
        }
        Date startDate = params.getQuery().getDate("startDate");
        if (startDate != null) {
            criteria.andGreaterThanOrEqualTo("createdTime", startDate);
        }
        Date endDate = params.getQuery().getDate("endDate");
        if (endDate != null) {
            criteria.andLessThanOrEqualTo("createdTime", endDate);
        }

        String sort = params.getSort();
        String order = params.getOrder();
        if (!StringUtil.hasEmpty(sort, order)) {
            example.setOrderByClause(sort + " " + order.toUpperCase());
        } else {
            example.setOrderByClause("id desc");
        }

        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        return myMapper.selectByExample(example);
    }

    @Override
    @Log
    public void updateRole(Role role) {
        myMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    @Log
    public void deleteRole(Long id) {
        myMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Log
    public void saveRole(Role role) {
        myMapper.insertSelective(role);
    }

    @Override
    @Log
    public boolean existsRoleCode(String code) {
        Role role = new Role();
        role.setCode(code);
        return exists(role);
    }

    @Override
    @Log
    public void updateRoleMenus(String code, String menuCodes) {
        roleMapper.deleteRoleMenus(code);

        if (StringUtils.isNotEmpty(menuCodes)) {
            roleMapper.insertRoleMenus(code, Arrays.asList(menuCodes.split(",")));
        }
    }
}
