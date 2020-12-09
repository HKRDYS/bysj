package cn.mmsq.config;

import cn.mmsq.controller.*;
import cn.mmsq.entity._MappingKit;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal3.JFinal3BeetlRenderFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * api 引导配置
 * 因为必须通用，所以必须介入API!
 */
public class DemoConfig extends JFinalConfig {
    /**
     * 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM argument 可配置为：
     * -XX:PermSize=64M -XX:MaxPermSize=256M
     */
    public static void main(String[] args) {

        JFinal.start("src/main/webapp", 8080, "/");
    }

    /**
     * 配置常量
     */
    public void configConstant(Constants me) {
        // 加载少量必要配置，随后可用PropKit.get(...)获取值
        PropKit.use("config.properties");
        me.setDevMode(PropKit.getBoolean("devMode", false));
        JFinal3BeetlRenderFactory rf = new JFinal3BeetlRenderFactory();
        rf.config();
        me.setRenderFactory(rf);
        GroupTemplate gt = rf.groupTemplate;
        Map<String, Object> shard = new HashMap<String, Object>();// 共享变量
        shard.put("ctx", JFinal.me().getContextPath());// 添加共享变量上下文路径?
        gt.setSharedVars(shard);// 设置共享变量
        //post包最大值，过长可能导致多端报错,单位:B
        //me.setMaxPostSize(1100000000);
        //想了想还是这样吧
        me.setMaxPostSize(1024*1024*1024);
    }

    /**
     * 配置路由
     */
    public void configRoute(Routes me) {
         me.add("/", IndexController.class);
         me.add("/common", CommonController.class);
         me.add("/manager", ManagerController.class);
         me.add("/pl", PlController.class);
         me.add("/product", ProductController.class);
         me.add("/sc", ScController.class);
         me.add("/user", UserController.class);
         me.add("/dic", DicController.class);
    }

    public void configEngine(Engine me) {
    }

    public static DruidPlugin createDruidPlugin() {
        return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
    }

    /**
     * 配置插件
     */
    public void configPlugin(Plugins me) {
        // 配置临时数据库连接池插件
        DruidPlugin druidPlugin = createDruidPlugin();
        me.add(druidPlugin);
        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);

        //构建arp映射，监听事件
        arp.setShowSql(true);
      _MappingKit.mapping(arp);
        me.add(arp);
    }

    /**
     * 配置全局拦截器
     */
    public void configInterceptor(Interceptors me) {

    }

    /**
     * 配置处理器
     *
     */
    public void configHandler(Handlers me) {

    }
}
