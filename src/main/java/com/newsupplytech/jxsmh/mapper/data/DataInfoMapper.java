package com.newsupplytech.jxsmh.mapper.data;

import com.newsupplytech.jxsmh.entity.data.DataInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataInfoMapper {

    public List<DataInfo> queryList();

    public Boolean add(DataInfo[] array);

    public Boolean update(DataInfo dataInfo);

    public Boolean delData(List list);

    public Boolean del(List list);

    public List<DataInfo> queryImageUrl(List list);

    public DataInfo queryUrl(DataInfo dataInfo);

    public Integer queryCount(@Param("id") Integer id);
}
