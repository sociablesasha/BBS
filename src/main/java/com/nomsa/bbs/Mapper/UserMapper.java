package com.nomsa.bbs.Mapper;

import com.nomsa.bbs.Model.UserModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    UserModel getUser(UserModel userModel);

    UserModel signIn(UserModel userModel);

    boolean signUp(UserModel userModel);

}
