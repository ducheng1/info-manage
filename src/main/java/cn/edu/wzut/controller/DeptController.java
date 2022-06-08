package cn.edu.wzut.controller;

import cn.edu.wzut.mbp.entity.SysDept;
import cn.edu.wzut.mbp.service.ISysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
