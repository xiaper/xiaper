package org.bytedesk.rest.controller.v1;


import org.bytedesk.jpa.constant.BdConstants;
import org.bytedesk.jpa.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * 路由
 *
 * TODO: 二级域名路由
 *
 * @author bytedesk.com
 */
@Controller
public class RouteController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HttpServletRequest httpServletRequest;

    /**
     * 首页
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Principal principal, Model model) {

        if (BdConstants.IS_DEPLOY) {
            return "visitor/other/deploy";
        }

        return "index";
    }

    /**
     * 登录后台
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/login")
    public String adminLogin(Principal principal, Model model) {

        return "admin/index";
    }

    @GetMapping("/user/login")
    public String userLogin(Principal principal, Model model) {

        return "/login";
    }

    /**
     * 注册页面
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/register")
    public String adminRegister(Principal principal, Model model) {

        return "admin/index";
    }

    @GetMapping("/user/register")
    public String userRegister(Principal principal, Model model) {

        User user = new User();
        model.addAttribute("user", user);

        return "register";
    }

    /**
     * 客服管理后台
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/admin")
    public String admin(Principal principal, Model model) {

        return "admin/index";
    }

    /**
     * IM
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/im")
    public String im(Principal principal, Model model) {

        return "im/index";
    }


    /**
     * 403
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/403")
    public String error403(Principal principal,Model model) {

        return "error/403";
    }

    /**
     * 404
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/404")
    public String error404(Principal principal,Model model) {

        return "error/404";
    }

    /**
     * error
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/error")
    public String error(Principal principal,Model model) {

        return "error/403";
    }

    /**
     * freeswitch webrtc verto-communicator
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/verto")
    public String verto(Principal principal, Model model) {

        return "verto/index";
    }

    /**
     * 价格页面
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/price")
    public String price(Principal principal, Model model) {

        return "visitor/other/price";
    }

    /**
     * 下载sdk+客户端
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/download")
    public String download(Principal principal, Model model) {

        return "visitor/other/download";
    }

    /**
     * 招聘
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/job")
    public String job(Principal principal, Model model) {

        return "visitor/other/job";
    }

    /**
     * 电商解决方案
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/solution/shop")
    public String solutionShop(Principal principal, Model model) {

        return "visitor/solution/shop";
    }

    /**
     * 教育解决方案
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/solution/edu")
    public String solutionEdu(Principal principal, Model model) {

        return "visitor/solution/edu";
    }

    /**
     * 医疗解决方案
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/solution/medical")
    public String solutionMedical(Principal principal, Model model) {

        return "visitor/solution/medical";
    }

    /**
     * 金融行业解决方案
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/solution/money")
    public String solutionMoney(Principal principal, Model model) {

        return "visitor/solution/money";
    }

    /**
     * 所有客户案例
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/customer")
    public String customer(Principal principal, Model model) {

        return "visitor/customer/index";
    }

    /**
     * 关于，页面
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/about")
    public String about(Principal principal, Model model) {

        return "visitor/other/about";
    }

    /**
     * 检查更新
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/version")
    public String version(Principal principal, Model model) {

        return "visitor/other/version";
    }

    /**
     * 联系我们
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/contact")
    public String contact(Principal principal, Model model) {

        return "visitor/other/contact";
    }

    /**
     * 测试页面
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/test")
    public String test(Principal principal, Model model) {

        return "visitor/other/test";
    }

    /**
     * 集团定制
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/enterprise")
    public String enterprise(Principal principal, Model model) {

        return "visitor/other/enterprise";
    }

    /**
     * 套件 功能介绍页面
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/product/suite")
    public String productSuite(Principal principal, Model model) {

        return "visitor/product/suite";
    }

    /**
     * 企业内部IM
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/product/chat")
    public String productChat(Principal principal, Model model) {

        return "visitor/product/chat";
    }

    /**
     * 智能客服 功能介绍页面
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/product/robot")
    public String productRobot(Principal principal, Model model) {

        return "visitor/product/robot";
    }

    /**
     * 在线客服 功能介绍页面
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/product/agent")
    public String productAgent(Principal principal, Model model) {

        return "visitor/product/agent";
    }

    /**
     * 帮助文档 功能介绍页面
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/product/support")
    public String productSupport(Principal principal, Model model) {

        return "visitor/product/support";
    }

    /**
     * 意见反馈，功能介绍页面
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/product/feedback")
    public String productFeedback(Principal principal, Model model) {

        return "visitor/product/feedback";
    }

    /**
     * 提交工单，功能介绍页面
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/product/workOrder")
    public String productWorkOrder(Principal principal, Model model) {

        return "visitor/product/workOrder";
    }

    /**
     * 填写问卷，功能介绍页面
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/product/wenJuan")
    public String productWenJuan(Principal principal, Model model) {

        return "visitor/product/wenJuan";
    }

    /**
     * 即时通信云
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/product/imcloud")
    public String productImCloud(Principal principal, Model model) {

        return "visitor/product/imcloud";
    }

    /**
     * 微信/QQ管家:
     *
     * 微信聊天记录、云词分析、导出通讯录, 好友性别、所在城市分析、批量加好友
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/product/wechat")
    public String productWeChat(Principal principal, Model model) {

        return "visitor/product/wechat";
    }


    /**
     * 帮助中心
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/support")
    public String support(Principal principal, Model model) {

        return "support/index";
    }


    /**
     * 帮助中心
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/support/category")
    public String supportCategory(Principal principal, Model model) {

        return "support/category";
    }


    /**
     * 帮助文档详情页面
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/support/article")
    public String supportArticle(Principal principal, Model model) {

        return "support/article";
    }


    /**
     * 搜索帮助文档
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/support/search")
    public String supportSearch(Principal principal, Model model) {

        return "support/search";
    }


    /**
     * 讨论社区
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/community")
    public String community(Principal principal, Model model) {

        return "community/index";
    }

    /**
     * 单独聊天，页面
     * FIXME: 历史原因保留
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/visitor/chat")
    public String vchat(Principal principal, Model model) {

        return "visitor/chatvue";
    }

    /**
     * 基于 vuejs
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/chat")
    public String chat(Principal principal, Model model) {

        return "visitor/chatvue";
    }

    /**
     * 基于 vuejs
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/chatvue")
    public String chatvue(Principal principal, Model model) {

        return "visitor/chatvue";
    }

    /**
     * 兼容版本: jquery 1.9.1
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/chatjq")
    public String chatv(Principal principal, Model model) {

        return "visitor/chatjq";
    }

    /**
     * 视频客服
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/video")
    public String video(Principal principal, Model model) {

        return "visitor/video";
    }

    /**
     * 视频会议
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/conf")
    public String conf(Principal principal, Model model) {

        return "visitor/conf";
    }

    /**
     *
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/wss")
    public String webSocket(Principal principal, Model model) {

        return "visitor/webSocket";
    }

    /**
     * 意见反馈
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/feedback")
    public String feedback(Principal principal, Model model) {

        return "visitor/feedback";
    }

    /**
     * 提交工单
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/workOrder")
    public String workOrder(Principal principal, Model model) {

        return "visitor/workOrder";
    }

    /**
     * 填写问卷
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/wenJuan")
    public String wenJuan(Principal principal, Model model) {

        return "visitor/wenJuan";
    }

    /**
     * 微信开放平台域名验证
     *
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/6343969877.txt")
    public String weChat(Principal principal, Model model) {

        return "6343969877.txt";
    }

    /**
     * 小程序域名验证
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/5VnkcIveRB.txt")
    public String mini(Principal principal, Model model) {

        return "5VnkcIveRB.txt";
    }


    /**
     * 绑定微信公众号中介跳转页面:
     * https://wechat.bytedesk.com/redirect/{uid}
     *
     * uid 201808221551193, host: vip.kefudashi.cn, port: 8000
     *
     * @param uid
     * @param model
     * @return
     */
    @GetMapping("/redirect/{uid}")
    public String redirect(@PathVariable(value = "uid") String uid, Model model, HttpSession session) {

        logger.info("uid {}, host: {}, port: {}", uid, httpServletRequest.getServerName(), httpServletRequest.getServerPort());

        session.setAttribute("uid", uid);

        model.addAttribute("url", "https://" + httpServletRequest.getServerName() + "/wechat/mp/oauth/redirect/");

        // (request.getServerPort() == 8000 ? ":8000" : "") +
        // model.addAttribute("url", BdConstants.WECHAT_OPEN_PLATFORM_REDIRECT_URI);

        return "admin/wechat";
    }




}








