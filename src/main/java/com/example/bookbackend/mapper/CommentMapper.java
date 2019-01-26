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
    @Results({
            @Result(column = "comment_id", property = "commentId", id = true),
            @Result(column = "writer_email", property = "writerEmail"),
            @Result(property = "user", column = "writer_email",
                    one = @One(select = "com.example.bookbackend.mapper.UserMapper.selectWithName"))
    })
    List<Comment> selectByBookId(@Param("bookId") Integer bookId);

    @Select("select * from comment where writer_email=#{email}")
    @Results({
            @Result(column = "comment_id", property = "commentId", id = true),
            @Result(column = "book_id", property = "bookId"),
            @Result(property = "book", column = "book_id",
                    one = @One(select = "com.example.bookbackend.mapper.BookMapper.selectById"))
    })
    List<Comment> selectMyComments(String email);
}
