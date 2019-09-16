/**
 *
 */
package com.cwz.springboot.token_demo.social.qq.binding;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/*
 * 绑定结果视图
 * @author zhailiang
 *
 */
public class MyConnectView extends AbstractView {

    /*
     * 社交账号绑定成功视图
     */
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");

		/*
         * 有connection是绑定
         * 没有是解绑
         */
		if (model.get("connections") == null) {
			response.getWriter().write("<h3>解绑成功</h3>");
		} else {
			response.getWriter().write("<h3>绑定成功</h3>");
		}

	}

}
