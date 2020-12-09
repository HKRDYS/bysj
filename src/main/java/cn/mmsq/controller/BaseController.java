package cn.mmsq.controller;

import cn.mmsq.entity.common.MsgData;
import cn.mmsq.util.*;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.UUID;

/**
 * @ClassName: BaseController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author xsg xsg2006@126.com
 * @date 2019年2月6日 上午11:02:59
 * @param <T>
 */

/**
 * 继承此类的子类具备基本的CRUD
 *
 * @param <M> Model
 * @author hexin
 */
public class BaseController<M extends Model<M>> extends Controller {
    public BaseController() {
        // 把class的变量保存起来，不用每次去取
        this.setModelClass(getClazz());
    }

    /**
     * 获取M的class
     *
     * @return M
     */
    @SuppressWarnings("unchecked")
    public Class<M> getClazz() {
        Type t = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) t).getActualTypeArguments();
        return (Class<M>) params[0];
    }

    protected Class<M> modelClass;

    public Class<M> getModelClass() {
        return modelClass;
    }

    public void setModelClass(Class<M> modelClass) {
        this.modelClass = modelClass;
    }

    //-------------------------------------------手机接口分割线-------------------------------------------
    /**
     * 通用分页查找
     */
    public void getByPage() {
        Page<Record> list = Db
                .paginate(getParaToInt("pageNumber"), getParaToInt("pageSize"),
                        "select *", "from tb_" + getModelClass().getSimpleName()
                                + " order by id desc");
        renderJson(list);
    }

    /**
     * 通用查找全部
     */
    public void mgetAll() {
        try {
            renderJson(new MsgData("200", "", JsonKit.toJson(Db.find("select * from tb_" + getModelClass().getSimpleName()
                    + " order by id asc;"))));
        } catch (Exception e) {
            System.out.println(e.toString());
            renderJson(new MsgData("500", "", ""));
        }

    }

    public void mgetByUid() {
        try {
            renderJson(new MsgData("200", "", JsonKit.toJson(Db.find("select * from tb_" + getModelClass().getSimpleName() + " where uid=?" + getPara("uid")
                    + " order by id asc;"))));
        } catch (Exception e) {
            System.out.println(e.toString());
            renderJson(new MsgData("500", "", ""));
        }
    }

    public void mgetByTid() {
        try {
            renderJson(new MsgData("200", "", JsonKit.toJson(Db.find("select * from tb_" + getModelClass().getSimpleName() + " where tid='" + getPara("tid")
                    + "' order by id asc;"))));
        } catch (Exception e) {
            System.out.println(e.toString());
            renderJson(new MsgData("500", "", ""));
        }
    }

    /**
     * 通用根据id查找
     */
    public void mgetById() {
        try{
            renderJson(new MsgData("200", "", JsonKit.toJson(Db.findById(getModelClass().getSimpleName(),
                    getPara("id")))));
        }catch (Exception e){
            System.out.println(e.toString());
            renderJson(new MsgData("500", "", ""));
        }

    }

    /**
     * 通用新增
     *
     * @throws Exception
     */
    public void msave() throws Exception {
        try {

                getModel(getModelClass(), "", true)
                        .set("id", UUID.randomUUID().toString().replace("-", ""))
                        .set("time", Time.getDate())
                        .save();
                renderJson(new MsgData("200", "成功", ""));

        } catch (Exception e) {
            System.out.println(e.toString());
            renderJson(new MsgData("500", "失败", ""));
        }
    }

    /**
     * 通用修改
     *
     * @throws Exception
     */
    public void mupdate() throws Exception {
        try {
            getModel(getModelClass(),"").update();
            renderJson(new MsgData("200", "成功", ""));
        } catch (Exception e) {
            System.out.println(e.toString());
            renderJson(new MsgData("500", "失败", ""));
        }
    }

    /**
     * 通用删除
     *
     * @throws Exception
     */
    public void mdelete() throws Exception {
        try {
            getModel(getModelClass(),"").delete();
            renderJson(new MsgData("200", "成功", ""));
        } catch (Exception e) {
            System.out.println(e.toString());
            renderJson(new MsgData("500", "失败", ""));
        }
    }

//-------------------------------后台接口分割线-----------------------------------------
    // 添加
    public void add() {
        try {
            getModel(getModelClass(), "", true)
                    .set("id", UUID.randomUUID().toString().replace("-", ""))
                    .set("time", Time.getDate())
                    .save();

            JSONObject js = new JSONObject();
            js.put("code", "200");
            renderJson(js.toJSONString());
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.toString());
            JSONObject js = new JSONObject();
            js.put("code", 500);
            js.put("msg", e.toString());
            renderJson(js.toJSONString());
        }

    }

    public void toadd() {
        setAttr("tid", getPara("tid"));
        render("/pages/" + getModelClass().getSimpleName() + "/add.html");
    }

    // 列表
    public void tolist() {
        setAttr("tid", getPara("tid"));
        render("/pages/" + getModelClass().getSimpleName() + "/list.html");
    }

    public void list() {
        String tid = getPara("tid");
        ParamUtil param = new ParamUtil(getRequest());
        Page<Record> page;
        if (tid == null)
            page = Db
                    .paginate(getParaToInt("page", 1), getParaToInt("limit", 30),
                            "select *", "from tb_" + getModelClass().getSimpleName()
                                    + " order by id desc");
        else
            page = Db
                    .paginate(getParaToInt("page", 1), getParaToInt("limit", 30),
                            "select *", "from tb_" + getModelClass().getSimpleName() + " where tid='" + tid + "' order by id desc");
        renderJson(JsonKit.toJson(new PageJson<Record>("0", "", page)));
    }

    public void alllist() {
        if (getPara("tid") == null)
            renderJson(Db.find("select * from tb_" + getModelClass().getSimpleName()
                    + " order by id asc;"));
        else
            renderJson(Db.find("select * from tb_" + getModelClass().getSimpleName() + " where tid='" + getPara("tid")
                    + " order by id asc;"));
    }

    // 删除
    public void del() {
        try {
            String[] ids = getParaValues("id");
            for (String id : ids) {
                getModel(getModelClass()).deleteById(id);
            }
            //一定要及时返回200，告诉已结束
            renderJson(JsonKit.toJson(new StatusJson("200", "suc", true)));
        } catch (Exception e) {
            // TODO: handle exception
            renderJson(JsonKit.toJson(new StatusJson("500", "fail", true)));
        }
    }

    // 查看
    public void toget() {
        setAttr("id", getPara("id"));
        setAttr("tid", getPara("tid"));
        render("/pages/" + getModelClass().getSimpleName() + "/add.html");
    }

    public void get() {
        Record info = Db.findById("tb_" + getModelClass().getSimpleName(), getPara("id"));
        renderJson(JsonKit.toJson(new RecordJson("200", "suc", info)));
    }

    // 更新
    public void update() {
        try {
            getModel(getModelClass(), "", true).update();
            JSONObject js = new JSONObject();
            js.put("code", "200");
            renderJson(js.toJSONString());
        } catch (Exception e) {
            // TODO: handle exception
            JSONObject js = new JSONObject();
            js.put("code", 500);
            js.put("msg", e.toString());
            renderJson(js.toJSONString());
        }
    }


}