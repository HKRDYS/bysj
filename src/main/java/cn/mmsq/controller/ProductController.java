package cn.mmsq.controller;

import cn.mmsq.entity.Dic;
import cn.mmsq.entity.Product;
import cn.mmsq.entity.Sc;
import cn.mmsq.entity.common.MsgData;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;

public class ProductController extends BaseController<Product> {

    public void getsc() {
        renderJson(new MsgData("200", "", JsonKit.toJson(Product.dao.find("select * from tb_product where id in(select tid from tb_sc where uid=?)", getPara("uid")))));
    }

    @Override
    public void mgetAll() {
        try {
            if (getPara("key")==null||getPara("key").isEmpty()) {
                if (getPara("type").equals("全部"))
                    renderJson(new MsgData("200", "", JsonKit.toJson(Db.find("select * from tb_" + getModelClass().getSimpleName()
                            + " order by id asc;"))));
                else
                    renderJson(new MsgData("200", "", JsonKit.toJson(Db.find("select * from tb_" + getModelClass().getSimpleName()
                            + " where type=?", getPara("type")))));
            } else {
                if (getPara("type").equals("全部")) {
                    renderJson(new MsgData("200", "", JsonKit.toJson(Db.find("select * from tb_" + getModelClass().getSimpleName()
                            + " where name like'%" + getPara("key") + "%'"))));
                } else {
                    renderJson(new MsgData("200", "", JsonKit.toJson(Db.find("select * from tb_" + getModelClass().getSimpleName()
                            + " where name like'%" + getPara("key") + "%' and type=?", getPara("type")))));
                }

            }

        } catch (Exception e) {
            System.out.println(e.toString());
            renderJson(new MsgData("500", "", ""));
        }
    }

    public void getsy() {
        renderJson(new MsgData("200", "", JsonKit.toJson(Product.dao.find("select * from tb_product order by rand() limit 3"))));
    }

    public void gettj(){
        renderJson(new MsgData("200","",JsonKit.toJson(Product.dao.find("select * from tb_product where tj='推荐' limit 10"))));
    }

    public void getzx(){
        renderJson(new MsgData("200","",JsonKit.toJson(Product.dao.find("select * from tb_product order by time limit 10"))));
    }

    @Override
    public void toadd() {
        setAttr("list", Dic.dao.find("select * from tb_dic"));
        render("/pages/" + getModelClass().getSimpleName() + "/add.html");
    }

    @Override
    public void toget() {
        setAttr("id", getPara("id"));
        setAttr("list", Dic.dao.find("select * from tb_dic"));
        render("/pages/" + getModelClass().getSimpleName() + "/add.html");
    }
}
