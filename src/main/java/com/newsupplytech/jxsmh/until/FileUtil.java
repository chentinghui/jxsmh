package com.newsupplytech.jxsmh.until;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FileUtil {
    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);
    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName,String staticUrl) {
        File file = new File(staticUrl+fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                log.info("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                log.info("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            log.info("删除单个文件失败：" + fileName + "不存在！");
            return true;
        }
    }

    public static String createDir(MultipartFile file,String staticUrl) {

        if (null != file && !file.isEmpty()) {
            String newFileName = null;
            try {
                System.currentTimeMillis();
                //获取跟目录
                /**获取配置的本地路径* */
                File path = new File("");
                String fileName = file.getOriginalFilename();
                //UUIDUtils.getUUIDShort()
                newFileName = UUIDUtils.getUUIDShort()+System.currentTimeMillis()+fileName.substring(fileName.lastIndexOf("."));//新文件名
                String fileUrl = staticUrl+ File.separator +newFileName;//指定绝对路径

                File upload = new File(staticUrl);
                if(!upload.exists()) upload.mkdirs();//不存在则创建文件夹
                File f = new File(fileUrl);
                if (!f.exists())  f.createNewFile();//不存在则创建文件
                InputStream is = file.getInputStream();
                //使用输入流读取前台的file文件
//                InputStream is=new FileInputStream((File)file);
//                file.transferTo(new File(fileUrl));
                //循环读取输入流文件内容，通过输出流将内容写入新文件
                OutputStream os=new FileOutputStream(fileUrl);
                byte buffer[]=new byte[1024];
                int cnt=0;
                while((cnt=is.read(buffer))>0){
                    os.write(buffer, 0, cnt);
                }
                os.flush();
                //关闭输入输出流
                os.close();
                is.close();

//                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileUrl));
//                out.write(file.getBytes());
//                out.flush();
//                out.close();
                log.info("创建文件成功");
            } catch (FileNotFoundException e) {
                log.error(e.getMessage());
                e.printStackTrace();
                return "0";
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
                return "0";
            }
            return newFileName;

        }else{
            return "1";
        }
    }
}
