package io.xiaper.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 帮助文档：静态化
 *
 * 博客：
 * @see <a href="https://www.jianshu.com/p/731f3fb414c4">url</a>
 * @see <a href="https://jgalacambra.wordpress.com/2016/06/08/writing-a-file-using-thymeleaf/">url</a>
 * @see <a href="https://www.programcreek.com/java-api-examples/?class=org.thymeleaf.TemplateEngine&method=process">url</a>
 *
 * @author bytedesk.com
 */
@Component
public class StaticizeTask {

//    private static Logger logger = LoggerFactory.getLogger(StaticizeTask.class);

    @Autowired
//    PostRepository postRepository;

    /**
     * @see <a href="http://localhost:9000/html/post/9123.html">url</a>
     * TODO: 每天生成一次，当天生成前一天的
     *
     * 定时生成html静态页面
     */
//    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void generatePostStaticHtml() {
//        logger.info("generateSupportStaticHtml");

//        // 构造模板引擎
//        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
//        // 模板所在目录，相对于当前classloader的classpath。
//        resolver.setPrefix("static/html/post/");
//        // 模板文件后缀
//        resolver.setSuffix(".html");
//        // HTML is the default value, added here for the sake of clarity.
//        resolver.setTemplateMode(TemplateMode.HTML);
//        resolver.setCacheable(true);
//        //
//        TemplateEngine templateEngine = new TemplateEngine();
//        templateEngine.setTemplateResolver(resolver);
//
//        // 构造上下文(Model)
//        Optional<Post> postOptional = postRepository.findById(Long.valueOf(9123));
//        Context context = new Context();
//        context.setVariable("post", postOptional.get());
//
//        // 渲染模板
//        FileWriter write = null;
//        try {
//            write = new FileWriter("src/main/resources/static/html/post/9123.html");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        templateEngine.process("template", context, write);
    }


}





