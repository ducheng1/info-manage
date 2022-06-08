package cn.edu.wzut.mbp.entity;

import lombok.Data;

@Data
public class SysUserExtend extends SysUser {
    private String roleName;
    private String deptName;
}
