package com.thinkdifferent.tools.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 文件转 pdf 示例
 * @author Dawn
 */
@RestController
public class PageDemo {

    @RequestMapping("/index")
    public String toIndex(){
        return "hello";
    }

}
