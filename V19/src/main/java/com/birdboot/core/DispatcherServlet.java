package com.birdboot.core;

import com.birdboot.annotation.Controller;
import com.birdboot.annotation.RequestMapping;
import com.birdboot.http.HttpServletRequest;
import com.birdboot.http.HttpServletResponse;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * 该类实际上是SpringMVC框架的一个核心类,用于与服务器(Tomcat)整合,接手处理请求的工作
 */
public class DispatcherServlet {
    //饿汉式单例模式
    private DispatcherServlet(){}//私有构造器

    private static DispatcherServlet dispatcherServlet = new DispatcherServlet();//创建静态的对象

    public static DispatcherServlet getDispatcherServlet(){//创建公共的静态get方法让别的类调用
        return dispatcherServlet;
    }

    private static File staticDir;//类加载路径下的static目录
    static {
        try {
            //类加载路径
            File baseDir = new File(
                    Objects.requireNonNull(ClientHandler.class.getClassLoader().getResource(".")).toURI()
            );
            staticDir = new File(baseDir,"static");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public void service(HttpServletRequest request, HttpServletResponse response) throws URISyntaxException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String path = request.getRequestURI();//?前的纯请求部分 即/index
//        if ("/regUser".equals(path)) {
//            //System.out.println(path);
//            UserController userController = new UserController();
//            userController.reg(request, response);
//        } else if ("/loginUser".equals(path)) {
//            UserController userController = new UserController();
//            userController.login(request, response);
//        }else if("writeArticle".equals(path)){
//            ArticleController articleController = new ArticleController();
//            articleController.reg(request,response);
//        }


//        else {
        //首先判断请求路径是否为请求业务
        /*
            path:/regUser

            V17新增内容:
            当我们得到本次请求路径path的值后，我们首先要查看是否为请求业务:
            1:扫描controller包下的所有类
              //类加载路径
              File baseDir = new File(
                DispatcherServlet.class.getClassLoader().getResource(".").toURI()
              );
              File dir = new File(baseDir,"com/birdboot/controller");

            2:查看哪些被注解@Controller标注的过的类(只有被该注解标注的类才认可为业务处理类)
            3:遍历这些类，并获取他们的所有方法，并查看哪些时业务方法
              只有被注解@RequestMapping标注的方法才是业务方法
            4:遍历业务方法时比对该方法上@RequestMapping中传递的参数值是否与本次请求
              路径path值一致?如果一致则说明本次请求就应当由该方法进行处理
              因此利用反射机制调用该方法进行处理。
              提示:反射调用后要记得return,避免执行后续处理静态资源的操作
            5:如果扫描了所有的Controller中所有的业务方法，均未找到与本次请求匹配的路径
              则说明本次请求并非处理业务，那么执行下面请求静态资源的操作

            对于包而言我们只扫描com.birdboot.controller包
         */
//        File baseDir = new File(
//                Objects.requireNonNull(DispatcherServlet.class.getClassLoader().getResource(".")).toURI()
//        );
//        File dir = new File(baseDir,"com/birdboot/controller");
//        File[] fArr = dir.listFiles(f->f.getName().endsWith(".class"));
//        if(fArr != null){
//            for(File f : fArr){
//                String classname = f.getName().split("\\.")[0];
//                Class<?> cls = Class.forName("com.birdboot.controller."+classname);
//                if(cls.isAnnotationPresent(Controller.class)){
//                    Method[] methods = cls.getMethods();
//                    for(Method m : methods){
//                        if(m.isAnnotationPresent(RequestMapping.class)){
//                            if(path.equals(m.getAnnotation(RequestMapping.class).value())){
//                                Object obj = cls.newInstance();
//                                m.invoke(obj,request,response);
//                                return;
//                            }
//                        }
//                    }
//                }
//            }
//        }

                Method m = HandlerMapping.getMethod(path);
                if(m!=null){
                try {
                    //getDeclaringClass获取方法对应的类对象
                    //再通过类对象new实例
                    m.invoke(m.getDeclaringClass().newInstance(),request,response);
                }catch (Exception e){
                    e.printStackTrace();
                }
                }else{
                    File file = new File(staticDir, path);

                    if (file.isFile()) {//file表示的是一个实际存在的文件
                        response.setStatusCode(200);
                        response.setStatusReason("OK");

                    } else {
                        response.setStatusCode(404);
                        response.setStatusReason("NotFound");
                        file = new File(staticDir, "404.html");//确定为404后,先更改文件为指定404页面,然后用response处理

                    }
                    response.addHeaders("Server", "BirdServer");
                    response.setContentFile(file);
//        }
                }
                }


}
