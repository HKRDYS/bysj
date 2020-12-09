package cn.mmsq.controller;


import cn.mmsq.entity.Manager;

/**
 * 用户
 */
public class ManagerController extends BaseController<Manager> {


    //管理员登录
       public void login() {
        String username=getPara("username");
        String pass=getPara("password");
        Manager m= Manager.dao.findFirst("select * from tb_manager where username=?",username);
        System.out.println("1");
        if(m!=null){
            if(m.getStr("pass").equals(pass)){
                System.out.println("2");
                setAttr("error_msg1","");
                setAttr("error_msg2","");
                setSessionAttr("m",m);

                    render("/pages/them5/index.html");

            }else{
                System.out.println("3");
                setAttr("error_msg2","");
                setAttr("error_msg1","密码不正确");
                render("/pages/them5/login.html");
            }
        }else{
            System.out.println("4");
            setAttr("error_msg2","");
            setAttr("error_msg1","账号不存在");
            render("/pages/them5/login.html");
        }
    }
}
