package org.ext.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.ext.entity.School;
import org.ext.service.SchoolService;
import org.ext.util.ExtHelper;
import org.ext.util.Pager;

import net.sf.json.JSONArray;


public class SchoolAction extends BaseAction {
	private static final long serialVersionUID = 6558439163716847431L;

	@Resource(name = "schoolServiceImpl")
	private SchoolService schoolService;

	private School school;

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
		String hql = "from School school";
		Pager<School> pager = schoolService.findByPage(pageNo, pageSize, hql);
		List<School> schoolList = pager.getPageList();
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("results:" + pager.getTotalNum() + ",");
		sb.append("rows:");
		JSONArray jsonObject = JSONArray.fromObject(schoolList);
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
		schoolService.save(school);
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
		schoolService.merge(school);
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
		schoolService.delete(ids);
		return null;
	}

	// 获取对应的校�?
	public String getSchoolById() {
		School school = schoolService.findById(id);
		String json = null;
		if (school != null) {
			json = "{success:true,data:" + ExtHelper.getJsonFromBean(school)
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

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
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
