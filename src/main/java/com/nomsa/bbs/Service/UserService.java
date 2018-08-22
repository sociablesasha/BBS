package com.nomsa.bbs.Service;

import com.nomsa.bbs.Mapper.UserMapper;
import com.nomsa.bbs.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserMapper userMapper;

    public UserModel getUser (UserModel userModel) { return userMapper.getUser(userModel); }

    public UserModel signIn (UserModel userModel) {
        return userMapper.signIn(userModel);
    }

    public void signUp (UserModel userModel) {
        userMapper.signUp(userModel);
    }

}
