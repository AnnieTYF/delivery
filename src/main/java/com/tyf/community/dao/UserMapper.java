package com.tyf.community.dao;

import com.tyf.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    User selectByStudentNumber(String studentNumber);

    User selectByPhone(String phone);

    int insertUser(User user);

    int updateHeader(@Param("studentNumber")String studentNumber, @Param("headerUrl")String headerUrl);

    int updatePassword(@Param("studentNumber")String studentNumber, @Param("password")String password);
}
