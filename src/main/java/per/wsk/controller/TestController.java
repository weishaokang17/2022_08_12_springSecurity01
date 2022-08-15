package per.wsk.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.wsk.entity.Users;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/hello")
    public String hello(){
        return "hello spring security";
    }


    @GetMapping("/index")
    public String index(){
        return "hello index";
    }

    @GetMapping("/update")
//    @Secured({"ROLE_sale","ROLE_manager"}) //有 sale或manager这两种角色的其中之一，就可以访问该接口
//    @PreAuthorize("hasAnyAuthority('admins')")  //表示有admins这个权限的，可以访问该接口
    @PostAuthorize("hasAnyAuthority('adminss')")    //该注解是：拥有adminss这个权限的，可以访问该接口，但该注解是在调用方法之后才校验。即如果一个用户没有adminss权限，
    //也可以访问该接口，下面的----也是输出到控制台上的，但没有返回hello,update，而是跳转到了403页面
    public String update(){
        System.out.println("-----------------");
        return "hello update";
    }



    @GetMapping("getAll")
    @PostAuthorize("hasAnyAuthority('admins')")
    @PostFilter("filterObject.username == 'admin1'") //对返回值进行过滤，控制台上只输出了admin1,没有输出admin2
    public List<Users> getAllUser(){
        ArrayList<Users> list = new ArrayList<>();
        list.add(new Users(11,"admin1","6666"));
        list.add(new Users(21,"admin2","888"));
        System.out.println(list);
        return list;
    }


}

