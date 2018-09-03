package com.newsupplytech.jxsmh.contrller;


import com.alibaba.fastjson.JSONObject;
import com.newsupplytech.jxsmh.until.FileUtil;
import com.newsupplytech.jxsmh.until.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@Controller
public class ShowFile {

    @Value("${jxsmh.filePath}")
    public String staticUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerUtils.class);

    @RequestMapping("/show/{fileName}")
    public void showPhotos(@PathVariable("fileName")String fileName, HttpServletRequest request, HttpServletResponse response){
        String imgFile = fileName; //文件名
        String path= staticUrl;//这里是存放图片的文件夹地址
        FileInputStream fileIs=null;
        try {
            fileIs = new FileInputStream(path+"/"+imgFile);
            int i=fileIs.available(); //得到文件大小
            byte data[]=new byte[i];
            fileIs.read(data);  //读数据
            response.setContentType("image/*"); //设置返回的文件类型
            OutputStream outStream=response.getOutputStream(); //得到向客户端输出二进制数据的对象
            outStream.write(data);  //输出数据
            outStream.flush();
            outStream.close();
            fileIs.close();
        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtils.info(LOGGER,"系统找不到图像文件："+path+imgFile);
        }

    }


    @RequestMapping("/download/{fileName}")
    public void download(@PathVariable("fileName")String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        java.io.BufferedInputStream bis = null;
        java.io.BufferedOutputStream bos = null;

        String ctxPath = staticUrl;
        String downLoadPath = ctxPath + fileName;

        try {
            long fileLength = new File(downLoadPath).length();
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }

    }

    @PostMapping("/upload")
    @ResponseBody
    public Object update(@RequestParam("") MultipartFile file){
        String newFileName = new FileUtil().createDir(file,staticUrl);

        if("1".equals(newFileName)){
            newFileName = null;//没有图片
        }else if("0".equals(newFileName)){
            return false;//图片上传失败
        }
        JSONObject jsonObject = new JSONObject();
        JSONObject js = new JSONObject();
        js.put("src","/show/"+newFileName);
        jsonObject.put("msg","");
        jsonObject.put("code",0);
        jsonObject.put("data",js);
        return jsonObject;
    }

}
