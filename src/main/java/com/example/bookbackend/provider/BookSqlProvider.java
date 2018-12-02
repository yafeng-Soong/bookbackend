package com.example.bookbackend.provider;

import com.example.bookbackend.bean.Book;
import org.apache.ibatis.jdbc.SQL;

public class BookSqlProvider {
    public String insert(Book book){
        return new SQL(){
            {
                INSERT_INTO("book");
                INTO_COLUMNS("email","name","type");
                INTO_VALUES("#{email}","#{name}","#{type}");
                if (book.getIntroduction() !=null ){
                    VALUES("introduction","#{introduction}");
                }
            }
        }.toString();
    }
}
