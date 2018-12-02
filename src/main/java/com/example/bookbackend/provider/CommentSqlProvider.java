package com.example.bookbackend.provider;

import com.example.bookbackend.bean.Comment;
import org.apache.ibatis.jdbc.SQL;

public class CommentSqlProvider {
    public String insert(Comment comment){
        return new SQL(){
            {
                INSERT_INTO("comment");
                INTO_COLUMNS("writer_email","book_id","content");
                INTO_VALUES("#{writerEmail}","#{bookId}","#{content}");
            }
        }.toString();
    }
}
