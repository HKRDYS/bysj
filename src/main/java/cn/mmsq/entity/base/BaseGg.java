package cn.mmsq.entity.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseGg<M extends BaseGg<M>> extends Model<M> implements IBean {

	public M setId(java.lang.String id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.String getId() {
		return getStr("id");
	}

	public M setTitle(java.lang.String title) {
		set("title", title);
		return (M)this;
	}

	public java.lang.String getTitle() {
		return getStr("title");
	}

	public M setMsg(java.lang.String msg) {
		set("msg", msg);
		return (M)this;
	}

	public java.lang.String getMsg() {
		return getStr("msg");
	}

	public M setTime(java.util.Date time) {
		set("time", time);
		return (M)this;
	}

	public java.util.Date getTime() {
		return get("time");
	}

}
