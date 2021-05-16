package com.tyf.community.dao;

import com.tyf.community.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface TaskMapper {

    Task selectTaskById(@Param("id")int id);

    List<Task> selectTasksByUserPost(@Param("userPost")String userPost);

    List<Task> selectTasksByUserGet(@Param("userGet")String userGet);

    int insertTask(Task task);

    int updateTaskUserGet(@Param("id")int id, @Param("userGet")String userGet);

    int updateTaskStatus(@Param("id")int id, @Param("status")Integer status);

    int updateTaskContent(@Param("id")int id, @Param("content")String content);

    int updateTaskTitle(@Param("id")int id, @Param("title")String title);
}
