package com.newsupplytech.jxsmh.contrller.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.newsupplytech.jxsmh.entity.data.DataInfo;
import com.newsupplytech.jxsmh.service.data.DataInfoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

/*
* 资料管理
* */
@RestController
@RequestMapping("/data")
public class DataInfoContrller {
    @Resource
    private DataInfoService dataInfoService;

    @PostMapping("/add")
    public Boolean add(@RequestBody DataInfo[] array){
        return dataInfoService.add(array);
    }

    @PostMapping("/addFile")
    public String addFile(MultipartFile file){
        return dataInfoService.addFile(file);
    }

    @PostMapping("/list")
    public Object queryList(){
        List<DataInfo> list = dataInfoService.queryList();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","");
        jsonObject.put("code",0);
        jsonObject.put("count",list.size());
        jsonObject.put("data",list);
        return jsonObject;
    }

    @PostMapping("/update")
    public Boolean update(@RequestBody DataInfo dataInfo){

        return dataInfoService.update(dataInfo);
    }

    @GetMapping("/del/{ids}")
    public Boolean del(@PathVariable(name="ids") List ids){
        return dataInfoService.del(ids);
    }
}
