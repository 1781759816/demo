package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

public class BaseController {

	public void writerJson(HttpServletResponse response, Object obj) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.print(JSON.toJSONString(obj));
			writer.flush();
		} catch (IOException e) {
			//logger.error(e.getMessage());
		}
	}
	
	public void writerString(HttpServletResponse response, String str) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.print(str);
			writer.flush();
		} catch (IOException e) {
			//logger.error(e.getMessage());
		}
	}

	/**
	 * 过滤XSS安全问题
	 * 
	 * @param response
	 * @param obj
	 */
	public void writerJsonEscapeXSS(HttpServletResponse response, Object obj) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			String result = null;
			if (obj != null && obj instanceof String) {
				result = obj.toString();
			} else {
				result = JSON.toJSONString(obj);
			}
			// 对输出格式进行处理
			result = result.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
					.replaceAll("javascript", "ｊａｖａｓｃｒｉｐｔ");
			response.getWriter().print(result);
		} catch (IOException e) {
			//logger.error(e.getMessage());
		}
	}

	/**
	 * 从Session中获取用户信息
	 *
	 * @param request
	 * @return
	 */
//	@SuppressWarnings("unchecked")
//	public <T> T getSessionAccount(HttpServletRequest request) {
//		return (T) request.getSession().getAttribute(Constants.SESSION_ACCOUNT_KEY);
//	}

	/**
	 * 设置用户信息到Session中
	 * 
	 * @param request
	 */
	public void setSessionAccount(HttpServletRequest request, Object account) {
		//request.getSession().setAttribute(Constants.SESSION_ACCOUNT_KEY, account);
	}

	/**
	 * 移除授权了的Session中的用户信息
	 * 
	 * @param request
	 */
	public void removeSessionAccount(HttpServletRequest request) {
		//request.getSession().removeAttribute(Constants.SESSION_ACCOUNT_KEY);
	}

	/**
	 * 设置值到Session中
	 * 
	 * @param request
	 * @param key
	 * @param value
	 */
	public void setSession(HttpServletRequest request, String key, Object value) {
		request.getSession().setAttribute(key, value);
	}

	/**
	 * 获取Session中的值
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public Object getSession(HttpServletRequest request, String key) {
		return request.getSession().getAttribute(key);
	}

	
	/**
	 * 移除Session中的信息
	 * 
	 * @param request
	 * @param key
	 */
	public void removeSession(HttpServletRequest request, String key) {
		request.getSession().removeAttribute(key);
	}

	/**
	 * 重写日期解析
	 * 
	 * @author isay
	 * 
	 */
	public class MyCustomDateEditor extends PropertyEditorSupport {
		private final DateFormat[] dateFormats;

		private final boolean allowEmpty;

		private final int exactDateLength;

		public MyCustomDateEditor(DateFormat[] dateFormats, boolean allowEmpty) {
			this.dateFormats = dateFormats;
			this.allowEmpty = allowEmpty;
			this.exactDateLength = -1;
		}

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			if (this.allowEmpty && !StringUtils.hasText(text)) {
				setValue(null);
			} else if (text != null && this.exactDateLength >= 0
					&& text.length() != this.exactDateLength) {
				throw new IllegalArgumentException(
						"Could not parse date: it is not exactly"
								+ this.exactDateLength + "characters long");
			} else {
				for (DateFormat dateFormat : dateFormats) {
					try {
						setValue(dateFormat.parse(text));
						return;
					} catch (Exception e) {
						continue;
					}
				}
			}
		}

		@Override
		public String getAsText() {
			Date value = (Date) getValue();
			for (DateFormat dateFormat : dateFormats) {
				try {
					return (value != null ? dateFormat.format(value) : "");
				} catch (Exception e) {
					continue;
				}
			}
			return "";
		}
	}
}