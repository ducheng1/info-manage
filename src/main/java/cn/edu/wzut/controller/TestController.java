package cn.edu.wzut.controller;


import cn.edu.wzut.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class TestController {
    @PostMapping("/login")
    public JsonResult<String> login(User user){
        return new JsonResult<>("测试成功");
    }
}
