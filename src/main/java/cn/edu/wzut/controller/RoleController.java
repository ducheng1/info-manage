package cn.edu.wzut.controller;

import cn.edu.wzut.mbp.entity.SysRole;
import cn.edu.wzut.mbp.mapper.SysRoleMapper;
import cn.edu.wzut.mbp.service.ISysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @GetMapping
    public JsonResult<List<SysRole>> all() {
        List<SysRole> list = roleService.list();
        return new JsonResult<>(list);
    }

    @GetMapping("/{id}")
    public JsonResult<SysRole> getOne(@PathVariable("id") BigDecimal id) {
        SysRole sysRole = roleService.getById(id);
        return new JsonResult<>(sysRole);
    }

    @GetMapping("/search/{key}")
    public JsonResult<Object> search(@PathVariable("key") String text) {
        List<SysRole> sysRoleList = sysRoleMapper.selectList(new QueryWrapper<SysRole>().like("role_name", text));
        return new JsonResult<>(sysRoleList);
    }

    @PostMapping
    public JsonResult<Object> save(@RequestBody SysRole sysRole) throws Exception {
        boolean res = roleService.saveOrUpdate(sysRole);
        if (res) {
            return new JsonResult<>(0, "更新成功");
        } else {
            throw new MyException("更新失败");
        }
    }

    @DeleteMapping("/{id}")
    public JsonResult<Object> delete(@PathVariable("id") BigDecimal id) throws MyException {
        boolean res = roleService.removeById(id);
        if (res) {
            return new JsonResult<>(0, "删除成功");
        } else {
            throw new MyException("删除失败");
        }
    }

    @DeleteMapping
    public JsonResult<Object> deleteMany(@RequestBody BigDecimal[] ids) throws MyException {
        boolean b = roleService.removeByIds(Arrays.asList(ids));
        if (b)
            return new JsonResult<>(0, "删除成功");
        else
            throw new MyException("删除失败");
    }
}
