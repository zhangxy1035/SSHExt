package org.ext.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	private static final long serialVersionUID = -7625581910698119114L;

	public static final String ERROR = "error";
	public static final String INDEX = "index";
	public static final String SHOWADD = "showAdd";
	public static final String SHOWUPDATE = "showUpdate";
	
	protected int id;

	protected Integer[] ids;
	
	public String getParameter(String str) {
		return ServletActionContext.getRequest().getParameter(str);
	}

	public void setAttribute(String key, Object value) {
		ServletActionContext.getRequest().setAttribute(key, value);
	}

	public Object getAttribute(String key) {
		return ServletActionContext.getRequest().getAttribute(key);
	}

	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	

	
	
	

	
}
