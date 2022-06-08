package cn.edu.wzut.controller;

import cn.edu.wzut.mbp.entity.SysUser;
import cn.edu.wzut.mbp.entity.SysUserExtend;
import cn.edu.wzut.mbp.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ISysUserService userService;

    @PostMapping
    public JsonResult<Object> save(@RequestBody SysUser sysUser) throws Exception {
        boolean res = userService.saveOrUpdate(sysUser);
        if (res) {
            return new JsonResult<>(0, "更新成功");
        } else {
            throw new MyException("更新失败");
        }
    }

    @GetMapping
    public JsonResult<List<SysUserExtend>> getAll() {
        List<SysUserExtend> userList = userService.getUserList();
        return new JsonResult<>(userList);
    }

    @GetMapping("/{id}")
    public JsonResult<SysUser> getOne(@PathVariable("id") BigDecimal id) {
        SysUser sysUser = userService.getById(id);
        return new JsonResult<SysUser>(sysUser);
    }

    @DeleteMapping("/{id}")
    public JsonResult<Object> delete(@PathVariable("id") BigDecimal id) throws Exception {
        boolean res = userService.removeById(id);
        if (res) {
            return new JsonResult<>(0, "删除成功");
        } else {
            throw new MyException("删除失败");
        }
    }
}
