package com.example.bookbackend.mapper;


import com.example.bookbackend.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 添加操作，返回新增元素的 ID
     *
     * @param user
     */
    @Insert("insert into user(name,pwd,email) values(#{name},#{pwd},#{email})")
    @Options(useGeneratedKeys = true, keyColumn = "user_id", keyProperty = "userId")
    void insert(User user);

    /**
     * 更新操作
     *
     * @param user
     * @return 受影响的行数
     */
    @Update("update person set name=#{name},age=#{age} where id=#{id}")
    Long update(User user);
    @Update("update user set state=1 where email=#{email}")
    Long activate(User user);


    /**
     * 删除操作
     *
     * @param id
     * @return 受影响的行数
     */
    @Delete("delete from person where id=#{id}")
    Long delete(@Param("id") Long id);

    /**
     * 查询所有
     *
     * @return
     */
    @Select("select * from user")
    List<User> selectAll();

    /**
     * 根据主键查询单个
     *
     * @param id
     * @return
     */
    @Select("select id,name,age from person where id=#{id}")
    User selectById(@Param("id") Long id);

    /**
     * 根据email查询
     *
     * @param email
     * @return
     */
    @Select("select email,pwd,state from user where email=#{email}")
    User selectByEmail(@Param("email") String email);

}