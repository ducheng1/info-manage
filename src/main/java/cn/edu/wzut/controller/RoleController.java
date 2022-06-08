package cn.edu.wzut.controller;

import cn.edu.wzut.mbp.entity.SysRole;
import cn.edu.wzut.mbp.service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private ISysRoleService roleService;

    @GetMapping
    public JsonResult<List<SysRole>> all() {
        List<SysRole> list = roleService.list();
        return new JsonResult<>(list);
    }
}
