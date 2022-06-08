package cn.edu.wzut.mbp.service.impl;

import cn.edu.wzut.mbp.entity.SysUser;
import cn.edu.wzut.mbp.entity.SysUserExtend;
import cn.edu.wzut.mbp.mapper.SysUserMapper;
import cn.edu.wzut.mbp.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wzut
 * @since 2022-04-27
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUserExtend> getUserList() {
        return sysUserMapper.getUserList();
    }
}
