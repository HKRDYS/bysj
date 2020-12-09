package cn.mmsq.controller;

import com.jfinal.core.Controller;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * <p>
 * IndexController
 */
public class IndexController extends Controller {

    public void index() {
        setAttr("error_msg1", "");
        setAttr("error_msg2", "");
        render("pages/them5/login.html");
    }
     public void exit() {
        setAttr("error_msg1", "");
        setAttr("error_msg2", "");

        render("/pages/them5/login.html");
    }


    public void login() {
        render("pages/them5/login.html");

    }

}





