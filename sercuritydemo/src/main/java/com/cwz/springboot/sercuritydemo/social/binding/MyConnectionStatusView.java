package com.cwz.springboot.sercuritydemo.social.binding;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 这里可以配置一个视图来调用spring social查看第三方是否绑定的数据
 * */
@Component("connect/status")
public class MyConnectionStatusView extends AbstractView {

    // 把返回的数转化为json
    @Autowired
    private ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        // 告诉前台 第三方应用（微信，qq）有哪些连接信息
        Map<String, List<Connection<?>>> connections =
                (Map<String, List<Connection<?>>>) model.get("connectionMap");

        logger.info("告诉前台 第三方应用（微信，qq）有哪些连接信息：" + connections);

        // 这里告诉前台 第三方应用（微信，qq） 绑没绑定
        Map<String, Boolean> result = new HashMap<>();

        for (String key : connections.keySet()) {
            // 看一下第三方应用（微信，qq） 是否有被绑定 有为true，没有为false
            result.put(key, CollectionUtils.isNotEmpty(connections.get(key)));
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

}
