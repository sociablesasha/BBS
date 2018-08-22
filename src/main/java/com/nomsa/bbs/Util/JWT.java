package com.nomsa.bbs.Util;

import com.nomsa.bbs.Model.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JWT {

    private static final String SALT =  "luvookSecret";

    public <T> String create (UserModel userModel) {
        if (userValidation(userModel)) { throw new BadRequestException(); }
        String jwt = Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setSubject("user")
                .claim("user", userModel)
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();
        return jwt;
    }

    private byte[] generateKey () {
        byte[] key = null;
        try {
            key = SALT.getBytes("UTF-8");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return key;
    }

    public void isUsable (String jwt) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(this.generateKey())
                    .parseClaimsJws(jwt);
            UserModel userModel = getUser();
            if (userInfoValidation(userModel)) { throw new NotAuthorizedException("Toekn Error"); }
        } catch (Exception exception) {
            throw new NotAuthorizedException("No Token");
        }
    }

    public Map<String, Object> get(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader("infranics");
        Jws<Claims> claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(SALT.getBytes("UTF-8"))
                    .parseClaimsJws(jwt);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        Map<String, Object> value = (LinkedHashMap<String, Object>)claims.getBody().get(key);
        return value;
    }

    public UserModel getUser() {
        Map<String, Object> map = new JWT().get("user");
        UserModel userModel = new UserModel();
        userModel.setIdentification(map.get("identification").toString());
        userModel.setPassword(map.get("password").toString());
        userModel.setName(map.get("name").toString());
        return userModel;
    }

    public boolean notValuable (String string) {
        if (string == null || string.equals("")) return true;
        else return false;
    }

    public boolean userValidation (UserModel userModel) {
        boolean exception = false;
        if (userModel == null)
            exception = true;
        else if (notValuable(userModel.getIdentification()) || notValuable(userModel.getPassword()))
            exception = true;

        return exception;
    }

    public boolean userInfoValidation (UserModel userModel) {
        boolean exception = false;
        if (userModel == null)
            exception = true;
        else if (notValuable(userModel.getIdentification()) || notValuable(userModel.getPassword()) || notValuable(userModel.getName()))
            exception = true;

        return exception;
    }

}
