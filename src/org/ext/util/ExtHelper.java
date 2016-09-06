package org.ext.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 * Title: 辅助类
 * Description: 该类用于转换java对象为XML文件格式或JSON文件格式
 * @author special
 * @time: 2015.08.30
 */
public class ExtHelper {
	/**
	 * 通过List生成XML数据
	 * @param recordTotal 记录总数，不一定与beanList中的记录数相等
	 * @param beanList 包含bean对象的集合
	 * @return 生成的XML数据
	 */
	public static String getXmlFromList(long recordTotal , List beanList) {
		Total total = new Total();
		total.setResults(recordTotal);
		List results = new ArrayList();
		results.add(total);
		results.addAll(beanList);
		XStream sm = new XStream(new DomDriver());
		for (int i = 0; i < results.size(); i++) {
			Class c = results.get(i).getClass();
			String b = c.getName();
			String[] temp = b.split("\\.");
			sm.alias(temp[temp.length - 1], c);
		}
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"	+ sm.toXML(results);
		return xml;
	}
	/**
	 * 通过List生成XML数据
	 * @param beanList 包含bean对象的集合
	 * @return 生成的XML数据
	 */
	public static String getXmlFromList(List beanList){
		return getXmlFromList(beanList.size(),beanList);
	}
	/**
	 * 通过List生成JSON数据
	 * @param recordTotal 记录总数，不一定与beanList中的记录数相等
	 * @param beanList 包含bean对象的集合
	 * @return 生成的JSON数据
	 */
	public static String getJsonFromList(long recordTotal , List beanList){
		TotalJson total = new TotalJson();
		total.setResults(recordTotal);
		total.setItems(beanList);
		JSONObject JsonObject = JSONObject.fromObject(total);
		return JsonObject.toString();
	}
	/**
	 * 通过List生成JSON数据
	 * @param beanList 包含bean对象的集合
	 * @return 生成的JSON数据
	 */
	public static String getJsonFromList(List beanList){
		return getJsonFromList(beanList.size(),beanList);
	}
	/**
	 * 通过bean生成JSON数据
	 * @param bean bean对象
	 * @return 生成的JSON数据
	 */
	public static String getJsonFromBean(Object bean){
		JSONObject JsonObject = JSONObject.fromObject(bean);
		return JsonObject.toString();
	}
}