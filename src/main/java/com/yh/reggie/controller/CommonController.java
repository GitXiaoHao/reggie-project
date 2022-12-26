package com.yh.reggie.controller;

import com.yh.reggie.common.Result;
import com.yh.reggie.controller.exception.CustomException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * date 2022/12/27
 *
 * @author yu
 * 文件上传下载
 */
@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;
    /**文件上传*/
    @SneakyThrows
    @PostMapping("/upload")
    public Result<String> upload(@RequestPart("file") MultipartFile file){
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
        //获得原始文件名
        String originalFilename = file.getOriginalFilename();
        //使用uuid重新生成文件名，防止文件名重复
        String uuid = UUID.randomUUID().toString();
        //截取最后一个文件格式
        assert originalFilename != null;
        String fileName = uuid + originalFilename.substring(originalFilename.lastIndexOf("."));
        //创建目录
        File dir = new File(basePath);
        //判断当前目录是否存在
        if(!dir.exists()){
            //不存在就创建
            boolean mkdirs = dir.mkdirs();
            if(!mkdirs){
                throw new CustomException("文件上传失败");
            }
        }
        //传输位置
        file.transferTo(new File(basePath + fileName));
        return Result.success(fileName);
    }
    /**
     * 文件下载
     */
    @SneakyThrows
    @GetMapping("/download")
    public void downLoad(String name, HttpServletResponse response){
        //设置响应的文件
        response.setContentType("image/jpeg");
        //输入流 通过输入流读取文件内容
        FileInputStream inputStream = new FileInputStream(basePath + name);
        //输出流 通过输出流将文件写回浏览器
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(bytes)) != -1){
            outputStream.write(bytes,0,len);
            outputStream.flush();
        }
        inputStream.close();
        outputStream.close();
    }
}
