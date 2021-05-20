package com.tyf.community.dao;

import com.tyf.community.entity.UserCircle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserCircleMapper {

    List<Integer> selectCircleIdByUser(@Param("stuNum")String stuNum);

    List<String> selectUserIdByCircleId(@Param("circleId") Integer circleId);

    int insertUserCircle(@Param("stuNum")String stuNum, @Param("circleId") Integer circleId);

    UserCircle selectByCircleIdAndUserId(@Param("userId") String userId, @Param("circleId") Integer circleId);

}
