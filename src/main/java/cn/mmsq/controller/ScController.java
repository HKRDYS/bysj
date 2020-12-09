package cn.mmsq.controller;

import cn.mmsq.entity.Sc;
import cn.mmsq.entity.Talk;
import cn.mmsq.entity.Xx;
import cn.mmsq.entity.common.MsgData;
import com.jfinal.kit.JsonKit;

public class ScController extends BaseController<Sc> {
    public void getsc() {
        renderJson(new MsgData("200", "", JsonKit.toJson(Talk.dao.find("select * from tb_talk where id in(select tid from tb_sc where uid=?)", getPara("uid")))));
    }

    @Override
    public void mdelete() throws Exception {
        Sc s = Sc.dao.findFirst("select * from tb_sc where uid=? and tid=?", getPara("uid"), getPara("tid"));
        if (s != null) {
            s.delete();
        } else {

        }
        renderJson(new MsgData("200", "", ""));

    }
}
