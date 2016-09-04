package org.ext.action;


import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.ext.entity.Emp;
import org.ext.service.EmpService;
import org.ext.util.ExtHelper;
import org.ext.util.Pager;

import net.sf.json.JSONArray;



public class EmpAction extends BaseAction {
	private static final long serialVersionUID = 6558439163716847431L;

	@Resource(name = "empServiceImpl")
	private EmpService empService;

	private Emp emp;

	private int id;

	private Integer[] ids;

	// 显示校区
	public String index() {
		return INDEX;
	}

	// 获取校区数据
	public String getSchoolInfo() {
		int pageNo = Integer.parseInt(getRequest().getParameter("start"));
		int pageSize = Integer.parseInt(getRequest().getParameter("limit"));
		String hql = "from Emp emp";
		Pager<Emp> pager = empService.findByPage(pageNo, pageSize, hql);
		List<Emp> empList = pager.getPageList();
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("results:" + pager.getTotalNum() + ",");
		sb.append("rows:");
		JSONArray jsonObject = JSONArray.fromObject(empList);
		sb.append(jsonObject.toString());
		sb.append("}");
		getResponse().setContentType("text/json,charset=UTF-8");
		try {
			System.out.println(sb.toString());
			getResponse().getWriter().write(sb.toString());
		} catch (Exception e) {
		}
		return null;
	}

	// 添加校区
	public String add() {
		empService.save(emp);
		boolean success = true;
		getResponse().setContentType("text/json,charset=UTF-8");
		try {
			getResponse().getWriter().write("{success:" + success + "}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 修改校区
	public String update() {
		empService.merge(emp);
		boolean success = true;
		getResponse().setContentType("text/json,charset=UTF-8");
		try {
			getResponse().getWriter().write("{success:" + success + "}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 删除校区
	public String delete() {
		empService.delete(ids);
		return null;
	}

	// 获取对应的校区
	public String getSchoolById() {
		Emp emp = empService.findById(id);
		String json = null;
		if (emp != null) {
			json = "{success:true,data:" + ExtHelper.getJsonFromBean(emp)
					+ "}";
		} else {
			json = "{success:false}";
		}
		getResponse().setContentType("text/json,charset=UTF-8");
		try {
			getResponse().getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}



	public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

}
