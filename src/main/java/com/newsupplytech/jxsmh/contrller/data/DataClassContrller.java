package com.newsupplytech.jxsmh.contrller.data;

import com.newsupplytech.jxsmh.entity.data.DataClass;
import com.newsupplytech.jxsmh.service.data.DataClassService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/dataClass")
public class DataClassContrller {
    @Resource
    private DataClassService dataClassService;


    @PostMapping("/query")
    public List<DataClass> queryList(){
        return dataClassService.queryList();
    }

    @GetMapping("/queryData")
    public List<DataClass> queryData(@RequestParam("id") String id){

        return dataClassService.queryData(Integer.parseInt(id));
    }

    @PostMapping("/add")
    public int insertDataClass(@RequestBody DataClass dataClass){
        int flag = dataClassService.insertDataClass(dataClass);
        return flag;
    }

    @PostMapping("/update")
    public Boolean update(@RequestParam("id") Integer id,@RequestParam(name="file",required=false) MultipartFile file,@RequestParam("imageUrl") String imageUrl,
                          @RequestParam(name="name",required=false) String name,@RequestParam(name="ename",required=false) String ename){

        return dataClassService.update(id,file,name,ename,imageUrl);
    }

    @GetMapping("/del/{ids}")
    public Boolean del(@PathVariable(name="ids") List ids){
        return dataClassService.del(ids);
    }

}
