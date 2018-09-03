package com.newsupplytech.jxsmh.service.data;

import com.newsupplytech.jxsmh.entity.data.DataInfo;
import com.newsupplytech.jxsmh.mapper.data.DataInfoMapper;
import com.newsupplytech.jxsmh.until.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataInfoService {
    @Resource
    private DataInfoMapper dataInfoMapper;

    @Value("${jxsmh.filePath}")
    private String staticUrl;

    public List<DataInfo> queryList(){
        return dataInfoMapper.queryList();
    }

    public String addFile(MultipartFile file){
        return   FileUtil.createDir(file,staticUrl);
    }

    public Boolean add(DataInfo[] array){

        return dataInfoMapper.add(array);
    }

    public Boolean update(DataInfo dataInfo){
        DataInfo data = dataInfoMapper.queryUrl(dataInfo);
        String oldFileImage = data.getFileImage();
        String oldFile = data.getFile();
        String file = dataInfo.getFile();
        String fileImage = dataInfo.getFileImage();
        Boolean flag = dataInfoMapper.update(dataInfo);
        if(null != oldFileImage && !oldFileImage.equals(fileImage)){
            FileUtil.deleteFile(oldFileImage,staticUrl);
        }
        if(null != oldFile && !oldFile.equals(file)){
            FileUtil.deleteFile(oldFile,staticUrl);
        }
        return flag;
    }

    public Boolean del(List list){
        return dataInfoMapper.del(list);
    }

}
