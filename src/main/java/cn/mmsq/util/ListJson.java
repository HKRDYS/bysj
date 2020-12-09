package cn.mmsq.util;


import java.util.List;

/**
 * 集合Json
 * @Package cn.eninesoft.jfinal.api
 * @ClassName:ListJson
 * @author 刘晓�?
 * @date 2015�?�?3�?下午5:52:06 
 * @version V1.0
 */
@SuppressWarnings("serial")
public class ListJson extends BaseJson {
	
	private List<?> list;
	
	public ListJson() {
	}
	public ListJson(String code, String info) {
		super(code, info);
	}
	public ListJson(String code, String info, List<?> list) {
		super(code, info);
		this.list = list;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
}
