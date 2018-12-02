package com.example.bookbackend.mapper;

import com.example.bookbackend.bean.Book;
import com.example.bookbackend.provider.BookSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface BookMapper {

    @InsertProvider(type = BookSqlProvider.class,method = "insert")
    @Options(useGeneratedKeys = true, keyColumn = "book_id", keyProperty = "bookId")
    Long insert(Book book);

    @Select("select * from book where email=#{email}")
    List<Book> selectByEmail(@Param("email") String email);
    @Select("select * from book where find_in_set(#{type},type)")
    List<Book> selectByType(@Param("type") String type);
    @Select("select * from book where name like #{name}")
    List<Book> selectByName(@Param("name") String name);
    @Select("select * from book limit 50")
    List<Book> selectAll();

    @Select("select * from book where book_id = #{bookId}")
    @Results({
            @Result(column="book_id", property="bookId",id=true),
            @Result(property = "comments", column = "book_id",
                    many = @Many(select = "com.example.bookbackend.mapper.CommentMapper.selectByBookId"))
    })
    Book selectWithComments(@Param("bookId") Integer bookId);

    @Update("update book set img_path=#{imgPath} where book_id=#{bookId}")
    Long updateImage(@Param("bookId") Integer bookId,@Param("imgPath") String imgPath);
}
