package cn.mmsq.controller;

import cn.mmsq.util.CommonData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.upload.UploadFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用文件上传
 * @author Administrator
 *
 */
public class CommonController extends Controller{

	//文件上传
	public void upload(){
		try {
			UploadFile f=getFile();
			JSONObject js=new JSONObject();
			js.put("code",0);
			js.put("url", f.getFileName());
			renderJson(js.toJSONString());
		} catch (Exception e) {
			// TODO: handle exception
			JSONObject js=new JSONObject();
			js.put("code",500);
			renderJson(js.toJSONString());
		}
		
	}
	
	//手機文件上傳
	public void mobileuploadFile(){
		try {
			UploadFile f=getFile();
			renderJson(JsonKit.toJson(new CommonData("200",f.getFileName(),"成功")));
		} catch (Exception e) {
			// TODO: handle exception
			renderJson(JsonKit.toJson(new CommonData("500",null,e.toString())));
		}
	}
	public void mobileuploadFiles(){
		try {
			List<UploadFile> f=getFiles();
			List<String> list=new ArrayList<>();
			for(UploadFile s:f){
				list.add(s.getFileName());
			}
			System.out.println(list.size());
			System.out.println(JSON.toJSONString(list));

			renderJson(JsonKit.toJson(new CommonData("200", JSON.toJSONString(list),"成功")));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			renderJson(JsonKit.toJson(new CommonData("500",null,e.toString())));
		}
	}


	//测试专用

	public void saveOrupdate(){

	}
}
