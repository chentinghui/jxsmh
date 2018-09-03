package com.newsupplytech.jxsmh.service.data;

import com.newsupplytech.jxsmh.entity.data.DataClass;
import com.newsupplytech.jxsmh.entity.data.DataInfo;
import com.newsupplytech.jxsmh.mapper.data.DataClassMapper;
import com.newsupplytech.jxsmh.mapper.data.DataInfoMapper;
import com.newsupplytech.jxsmh.until.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataClassService {
    @Resource
    private DataClassMapper dataClassMapper;

    @Resource
    private DataInfoMapper dataInfoMapper;

    @Value("${jxsmh.filePath}")
    public String staticUrl;

    public int insertDataClass(DataClass dataClass){
        Integer id = dataClass.getId();
        if(dataInfoMapper.queryCount(id) == 0){
            dataClassMapper.insertDataClass(dataClass);
            if(0 == id){
                dataClass.setFlag("1");
            }else{
                dataClass.setFlag("2");
            }
            dataClassMapper.updateFlag(id,dataClass.getFlag());
            return dataClass.getId();
        }
        return 0;


    }

    public List<DataClass> queryList(){
        List<DataClass> dcList = dataClassMapper.queryList();

        DataClass dc = new DataClass();
        dc.setId(0);
        dc.setName("希美克");
        dc.setPid(0);
        dcList.add(dc);
        return dcList;
    }

    public List<DataClass> queryData(Integer id){
        return dataClassMapper.queryData(id);
    }

    public boolean update(Integer id,MultipartFile file,String name,String ename,String imageUrl){
        boolean inserTstate = false;
        String newFileName = FileUtil.createDir(file,staticUrl);

        if("1".equals(newFileName)){
            newFileName = null;//没有图片
        }else if("0".equals(newFileName)){
            return false;//图片上传失败
        }
        Boolean upstate = dataClassMapper.update(id,newFileName,name,ename);

        if(upstate && "0".equals(file)  && null != file ){
            upstate = FileUtil.deleteFile(imageUrl,staticUrl);
        }
        return upstate;
    }

    public Boolean del(List ids){
        Object pid = ids.remove(0);
        List<DataClass> dc = dataClassMapper.queryImageUrl(ids);
        List<DataInfo> di = dataInfoMapper.queryImageUrl(ids);
        for (int i=0;i<di.size();i++){
            FileUtil.deleteFile(di.get(i).getFile(),staticUrl);
            FileUtil.deleteFile(di.get(i).getFileImage(),staticUrl);
        }
        for (int i=0;i<dc.size();i++){
            FileUtil.deleteFile(dc.get(i).getImageUrl(),staticUrl);
        }
        dataClassMapper.del(ids);
        dataInfoMapper.delData(ids);
        Integer id = Integer.parseInt((String)pid);
        if(0 != id && dataClassMapper.queryCount(id) == 0){
            dataClassMapper.updateFlag(id,"1");
        }
        return true;
    }

}
