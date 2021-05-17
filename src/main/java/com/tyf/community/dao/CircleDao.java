package com.tyf.community.dao;

import com.tyf.community.entity.Circle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CircleDao {

    int insertCircle(Circle circle);

    Circle selectCircleById(@Param("id") Integer id);

    List<Circle> selectCircleByName(@Param("name")String name);

    List<Circle> selectCircleByManager(@Param("manager")String manager);

    int updateCircleName(@Param("id") Integer id, @Param("name") String name);

    int updateCircleManager(@Param("id") Integer id, @Param("manager") String manager);
}
