package cn.mmsq.util;


/**
 * 状�?Json
 * @Package cn.eninesoft.jfinal.api
 * @ClassName:RecordJson
 * @author 刘晓�?
 * @date 2015�?�?3�?下午5:52:06 
 * @version V1.0
 */
@SuppressWarnings("serial")
public class StatusJson  extends BaseJson{
	
	private boolean status;
	
	public StatusJson(String code,String info) {
		super(code, info);
	}
	public StatusJson(String code,String info,boolean status) {
		super(code, info);
		this.status = status;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
}
