package com.fpt.config;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fpt.entity.Users;
import com.fpt.repository.UserRepository;
import com.fpt.repository.UserRolesRepository;
import com.fpt.service.UserRoleService;
import com.fpt.service.UserService;
import com.fpt.utils.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.dto.GooglePojoDTO;

@Component
public class GoogleUtils {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRolesService;
    @Autowired
    private Environment env;
    public String getToken(final String code) throws ClientProtocolException, IOException {
        String link = env.getProperty("google.link.get.token");
        String response = Request.Post(link)
                .bodyForm(Form.form().add("client_id", env.getProperty("google.app.id"))
                        .add("client_secret", env.getProperty("google.app.secret"))
                        .add("redirect_uri", env.getProperty("google.redirect.uri")).add("code", code)
                        .add("grant_type", "authorization_code").build())
                .execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response).get("access_token");
        return node.textValue();
    }
    public String getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = env.getProperty("google.link.get.user_info") + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(response);// response will be the json String
        GooglePojoDTO googlePojo = gson.fromJson(object, GooglePojoDTO.class);
        if(googlePojo.isVerified_email()){
            return googlePojo.getEmail();
        }
        return null;

    }
    public UserDetails buildUser(String email,Users appUser) {
       // Users appUser = this.userService.findByEmail(email);

        if (appUser == null) {
            System.out.println("User not found! " + email);
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }


        List<String> roleNames = this.userRolesService.getRoleNamesByEmail(email);

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (!roleNames.isEmpty()) {
            for (String role : roleNames) {
                switch (role) {
                    case Constant.ROLE_HEAD_DB:
                        role = Constant.ROLE_HEAD_AUTHEN;
                        break;
                    case Constant.ROLE_LECTURERS_DB:
                        role = Constant.ROLE_LECTURERS_AUTHEN;
                        break;
                    case Constant.ROLE_STUDENT_LEADER_DB:
                        role = Constant.ROLE_STUDENT_LEADER_AUTHEN;
                        break;
                    case Constant.ROLE_STUDENT_MEMBER_DB:
                        role = Constant.ROLE_STUDENT_MEMBER_AUTHEN;
                        break;
                    case Constant.ROLE_TRAINING_DEP_DB:
                        role = Constant.ROLE_TRAINING_DEP_AUTHEN;
                        break;
                    default:
                        break;
                }
                System.out.println(role);
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }
        UserDetails userDetail =(UserDetails) new User(email,
                "", grantList);
        return userDetail;
    }
}