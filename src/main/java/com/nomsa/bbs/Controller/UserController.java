package com.nomsa.bbs.Controller;

import com.nomsa.bbs.Model.UserModel;
import com.nomsa.bbs.Service.UserService;
import com.nomsa.bbs.Util.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private JWT jwtService;

    @RequestMapping(value = "/users/information", method = RequestMethod.GET)
    public UserModel usable(@RequestHeader(value = "infranics", defaultValue = "") String jwt) {
        jwtService.isUsable(jwt);

        return userService.getUser(jwtService.getUser());
    }

    @RequestMapping(value = "/users/signIn", method = RequestMethod.POST)
    public String signIn (@RequestParam("identification") String identification,
                          @RequestParam("password") String password) {
        UserModel userModel = new UserModel();
        userModel.setIdentification(identification);
        userModel.setPassword(password);

        String token = jwtService.create(userService.signIn(userModel));

        return token;
    }

    @RequestMapping(value = "/users/signUp", method = RequestMethod.POST)
    public void signUp (@RequestParam("identification") String identification,
                        @RequestParam("password") String password,
                        @RequestParam("name") String name) {
        UserModel userModel = new UserModel();
        userModel.setIdentification(identification);
        userModel.setPassword(password);
        userModel.setName(name);

        userService.signUp(userModel);
    }



}
