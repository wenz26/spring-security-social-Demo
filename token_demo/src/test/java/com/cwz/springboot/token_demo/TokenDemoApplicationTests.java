package com.cwz.springboot.token_demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import netscape.javascript.JSObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.ProviderSignInAttempt;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenDemoApplicationTests {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Test
    public void contextLoads() {
        Random random = new Random();
        System.out.println(random.nextLong());
    }

    @Test
    public void test01(){

        String jsonS = "{\"authentication\":{\"authenticated\":true,\"authorities\":[{\"authority\":\"ROLE_ADMIN\"},{\"authority\":\"ROLE_USER\"}],\"connection\":{\"displayName\":\"啦啦啦\",\"imageUrl\":\"http://thirdqq.qlogo.cn/g?b=oidb&k=icxb4Sv373nrlJ3ibLwqLWSA&s=40&t=1565944036\",\"key\":{\"providerId\":\"qq\",\"providerUserId\":\"256BAE4DDF9AD5AE525FE8F4DFE38BBC\"}},\"details\":{\"remoteAddress\":\"127.0.0.1\",\"sessionId\":\"289CE22E3B41700C1B0607274CFC2173\"},\"name\":\"啦啦啦\",\"principal\":{\"accountNonExpired\":true,\"accountNonLocked\":true,\"authorities\":[{\"$ref\":\"$.authentication.authorities[0]\"},{\"$ref\":\"$.authentication.authorities[1]\"}],\"credentialsNonExpired\":true,\"enabled\":true,\"userId\":\"啦啦啦\",\"username\":\"啦啦啦\"},\"providerAccountData\":{},\"providerId\":\"qq\"}}";

        JSONObject jsonObject = JSONObject.parseObject(jsonS);

        String s1 = jsonObject.getString("authentication");

        System.out.println(s1);

        JSONObject jsonObject1 = JSONObject.parseObject(s1);

        String s2 = jsonObject1.getString("connection");

        System.out.println(s2);

        JSONObject jsonObject2 = JSONObject.parseObject(s2);

        //jsonObject2.getJSONObject("key").getString()

       /* JSONArray jsonArray = JSONArray.parseArray(array2);

        System.out.println(jsonArray);

        Object[] strs = jsonArray.toArray();

        for (Object s : strs){
            System.out.println(s);
        }*/

    }

}
