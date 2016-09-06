package org.ext.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ext.entity.Dep;
import org.ext.entity.School;
import org.ext.json.DepJson;
import org.ext.service.DepService;
import org.ext.service.SchoolService;
import org.ext.util.ExtHelper;
import org.ext.util.Pager;

import net.sf.json.JSONArray;


public class DepAction extends BaseAction {
	private static final long serialVersionUID = 6558439163716847431L;

	@Resource(name = "depServiceImpl")
	private DepService depService;

	@Resource(name = "schoolServiceImpl")
	private SchoolService schoolService;

	private Dep dep;

	private int sid;

	// 显示部门
	public String index() {
		return INDEX;
	}

	// 显示校区
	public String getSchools() {
		List<School> schoolList = schoolService.findAll("from School school");
		getResponse().setContentType("application/xml;charset=UTF-8");
		try {
			PrintWriter out = getResponse().getWriter();
			out.print(ExtHelper.getXmlFromList(schoolList));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 添加部门
	public String add() {
		School school = schoolService.findById(sid);
		dep.setSchool(school);
		depService.save(dep);
		boolean success = true;
		getResponse().setContentType("text/json,charset=UTF-8");
		try {
			getResponse().getWriter().write("{success:" + success + "}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获取数据
	public String getDepInfo() {
		int pageNo = Integer.parseInt(getRequest().getParameter("start"));
		int pageSize = Integer.parseInt(getRequest().getParameter("limit"));
		String hql = "from Dep dep";
		Pager<Dep> pager = depService.findByPage(pageNo, pageSize, hql);
		List<Dep> depList = pager.getPageList();
		List<DepJson> depJsonList = new ArrayList<DepJson>();
		for (Dep dep : depList) {
			DepJson depJson = new DepJson();
			depJson.setDepName(dep.getDepName());
			depJson.setDepTel(dep.getDepTel());
			depJson.setId(dep.getId());
			depJson.setSchoolName(dep.getSchool().getSchoolName());
			depJsonList.add(depJson);
		}
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("results:" + pager.getTotalNum() + ",");
		sb.append("rows:");
		JSONArray jsonObject = JSONArray.fromObject(depJsonList);
		sb.append(jsonObject.toString());
		sb.append("}");
		getResponse().setContentType("text/json,charset=UTF-8");
		try {
			getResponse().getWriter().write(sb.toString());
		} catch (Exception e) {
		}
		return null;
	}
	
	//修改部门
	public String getDepById() {
		Dep dep = depService.findById(id);
		DepJson depJson = new DepJson();
		depJson.setDepName(dep.getDepName());
		depJson.setDepTel(dep.getDepTel());
		depJson.setId(dep.getId());
		depJson.setSchoolName(dep.getSchool().getSchoolName());
		String json = "{success:true,data:" + ExtHelper.getJsonFromBean(depJson)
					+ "}";
		getResponse().setContentType("text/json,charset=UTF-8");
		try {
			getResponse().getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public Dep getDep() {
		return dep;
	}

	public void setDep(Dep dep) {
		this.dep = dep;
	}

}
