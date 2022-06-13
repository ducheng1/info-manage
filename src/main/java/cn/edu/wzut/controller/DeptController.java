package cn.edu.wzut.controller;

import cn.edu.wzut.mbp.entity.SysDept;
import cn.edu.wzut.mbp.service.ISysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    private ISysDeptService deptService;

    @GetMapping("/tree")
    public JsonResult<List<SysDept>> listTree() {
        List<SysDept> list = deptService.listTree();
        return new JsonResult<>(list);
    }

    @GetMapping("/{id}")
    public JsonResult<SysDept> getOne(@PathVariable("id") BigDecimal id) {
        SysDept sysDept = deptService.getById(id);
        return new JsonResult<>(sysDept);
    }

    @PostMapping
    public JsonResult<Object> save(@RequestBody SysDept sysDept) throws MyException {
        boolean b = deptService.saveOrUpdate(sysDept);
        if (b)
            return new JsonResult<>(0, "保存成功");
        else
            throw new MyException("保存失败");
    }

    @DeleteMapping("/{id}")
    public JsonResult<Object> remove(@PathVariable("id") BigDecimal id) throws MyException {
        boolean b = deptService.removeById(id);
        if (b)
            return new JsonResult<>(0, "保存成功");
        else
            throw new MyException("删除失败");
    }
}
