package com.example.bookbackend.mapper;

import com.example.bookbackend.bean.Comment;
import com.example.bookbackend.provider.CommentSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @InsertProvider(type = CommentSqlProvider.class,method = "insert")
    @Options(useGeneratedKeys = true, keyColumn = "comment_id", keyProperty = "commentId")
    Long insert(Comment comment);

    @Select("select * from comment where book_id = #{bookId}")
    List<Comment> selectByBookId(@Param("bookId") Integer bookId);
}
