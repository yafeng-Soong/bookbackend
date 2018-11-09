package com.example.bookbackend.provider;

import com.example.bookbackend.model.User;
import org.apache.ibatis.jdbc.SQL;

public class SqlProvider {
    public String insert(User user){
        return new SQL(){
            {
                INSERT_INTO("USER");
                INTO_COLUMNS("email","pwd","name");
                INTO_VALUES("#{email}","#{pwd}","#{name}");
                if (user.getSex() != null){
                    VALUES("sex","#{sex}");
                }
                if (user.getSignature() != null){
                    VALUES("signature","#{signature}");
                }
            }
        }.toString();
    }
}
