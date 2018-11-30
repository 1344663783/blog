package com.blog.base.controller;

import common.CommonResultCode;
import io.swagger.annotations.Api;
import model.Result;
import model.ServiceException;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName BaseController
 * @Description TODO
 * @Author zhangxiaoxiong
 * @Date 2018/11/26 23:18
 * @Version 1.0
 **/
@RestController
@CrossOrigin //跨域
@RequestMapping("/label")
@Api(tags = {"BaseController"} ,value = "base查询",description = "base查询")
public class BaseController {

    @RequestMapping(value = "findLabelList",method = RequestMethod.GET)
    public Result findLabelList(){
        try{

        }catch (Exception e){
           throw new ServiceException(CommonResultCode.系统错误);
        }
        return new Result<>("");

    }

    @RequestMapping(value = "findLabel",method = RequestMethod.GET)
    public Result findById(@RequestParam(name = "labelId") Integer labelId){

        return new Result<>("");
    }

}
