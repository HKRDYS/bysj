package cn.mmsq.controller;


import cn.mmsq.entity.User;
import cn.mmsq.entity.common.MsgData;
import cn.mmsq.util.PageJson;
import cn.mmsq.util.ParamUtil;
import cn.mmsq.util.Time;
import com.alibaba.fastjson.JSON;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.UUID;

public class UserController extends BaseController<User> {
    public void login() {
        String username = getPara("username");
        String pass = getPara("pass");
        User m = User.dao.findFirst("select * from tb_user where username=?", username);
        if (m != null) {
            if (m.getStr("pass").equals(pass)) {
                renderJson(new MsgData("200", "成功", JSON.toJSONString(m)));
            } else {
                renderJson(new MsgData("500", "失败", JSON.toJSONString(m)));
            }
        } else {
            renderJson(new MsgData("500", "账号不存在", JSON.toJSONString(m)));
        }
    }

    public void msave() throws Exception {
        try {
            if (User.dao.findFirst("select * from tb_user where username =?", getPara("username")) == null) {
                getModel(getModelClass(), "", true)
                        .set("id", UUID.randomUUID().toString().replace("-", ""))
                        .set("time", Time.getDate())
                        .save();
                renderJson(new MsgData("200", "成功", ""));
            } else {
                renderJson(new MsgData("500", "用户已经存在", ""));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            renderJson(new MsgData("500", "失败", ""));
        }
    }

    @Override
    public void list() {
        ParamUtil param = new ParamUtil(getRequest());
        Page<Record> page;

        page = Db
                .paginate(getParaToInt("page", 1), getParaToInt("limit", 30),
                        "select *", "from tb_user ");
        renderJson(JsonKit.toJson(new PageJson<Record>("0", "", page)));

    }
}
