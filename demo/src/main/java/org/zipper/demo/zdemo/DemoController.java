package org.zipper.demo.zdemo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.zipper.helper.web.response.ResponseEntity;

@Api
@RestController
@RequestMapping("demo")
public class DemoController {

    @ApiOperation(value = "GET方法测试", notes = "Get方法描述")
    @GetMapping("get")
    public ResponseEntity<String> getMethod(@RequestParam String parameter) {

        return ResponseEntity.success("");
    }

    @ApiOperation(value = "POST方法测试", notes = "POST方法描述")
    @PostMapping("get")
    public ResponseEntity<Demo> postMethod(@RequestBody Demo demo) {

        return ResponseEntity.success(new Demo());
    }
}
