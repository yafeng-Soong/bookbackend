package com.example.bookbackend.provider;

import com.example.bookbackend.bean.User;
import org.apache.ibatis.jdbc.SQL;

public class UserSqlProvider {
    public String insert(User user){
        return new SQL(){
            {
                INSERT_INTO("user");
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
    public String updateInfo(User user){
        return new SQL(){
            {
                UPDATE("user");
                if (user.getPwd() != null) {
                    SET("pwd=#{pwd}");
                }
                if (user.getSignature() != null){
                    SET("signature=#{signature}");
                }
                if (user.getName() != null){
                    SET("name=#{name}");
                }
                WHERE("email=#{email}");
            }
        }.toString();
    }
}
