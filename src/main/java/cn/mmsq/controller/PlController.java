package cn.mmsq.controller;

import cn.mmsq.entity.Pl;
import cn.mmsq.entity.Sc;
import cn.mmsq.entity.Talk;
import cn.mmsq.entity.Xx;
import cn.mmsq.entity.common.MsgData;
import cn.mmsq.util.Time;

import java.util.Date;
import java.util.UUID;

public class PlController extends BaseController<Pl> {
    @Override
    public void msave() throws Exception {
        try {

            getModel(getModelClass(), "", true)
                    .set("id", UUID.randomUUID().toString().replace("-", ""))
                    .set("time", Time.getDate())
                    .save();
            Talk t=Talk.dao.findById(getPara("tid"));
            new Xx().setId(UUID.randomUUID().toString().replace("-",""))
                    .setMsg(getPara("uname")+"发表评论:"+getPara("content"))
                    .setUid(t.getUid())
                    .setUname(t.getUname())
                    .setFromuname(getPara("uname"))
                    .setTime(new Date())
                    .save();
            renderJson(new MsgData("200", "成功", ""));
        } catch (Exception e) {
            System.out.println(e.toString());
            renderJson(new MsgData("500", "失败", ""));
        }
    }
}
