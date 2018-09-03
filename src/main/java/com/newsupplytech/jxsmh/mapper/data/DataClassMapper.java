package com.newsupplytech.jxsmh.mapper.data;

import com.newsupplytech.jxsmh.entity.data.DataClass;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataClassMapper {
//    @Insert("insert into dataClass (pid,name,ename,imagerUrl) values (#{pid},#{name},#{ename},#{imagerUrl})")

    public int updateFlag(@Param("id") Integer id,@Param("flag") String flag);

    public int insertDataClass(DataClass dataClass);

    public List<DataClass> queryList();

    public List<DataClass> queryData(@Param("id") Integer id);

    public boolean update(@Param("id") Integer id,@Param("imageUrl")String imageUrl,@Param("name")String name,@Param("ename")String ename);

    public List<DataClass> queryOne(@Param("id") Integer id);

    public Boolean del(List list);

    public List<DataClass> queryImageUrl(List list);

    public Integer queryCount(@Param("id") Integer id);
}
