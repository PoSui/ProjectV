package com.birdboot.controller;

import com.birdboot.annotation.Controller;
import com.birdboot.annotation.RequestMapping;
import com.birdboot.entity.User;
import com.birdboot.http.HttpServletRequest;
import com.birdboot.http.HttpServletResponse;

import java.io.*;


/**
 * 模拟用户登录类的业务逻辑
 */

@Controller
public class UserController {
    private static File userDir;
    User user = new User();
    static{
        userDir = new File("./users");
        if(!userDir.exists()){
            userDir.mkdirs();
        }
    }
    @RequestMapping("/regUser")
    public void reg(HttpServletRequest request, HttpServletResponse response){
        System.out.println("开始处理用户注册");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");
        String age = request.getParameter("age");

        if(username == null || username.isEmpty() ||
           password == null || password.isEmpty() ||
           nickname == null || nickname.isEmpty() ||
           age == null || age.isEmpty() || age.matches("[0,9]{1,3}")//匹配三位数字0-9
        ){
            response.sendRedirect("/reg_info_error.html");
        }else{
//            user.setUsername(username);
//            user.setAge(age);
//            user.setNickname(nickname);
//            user.setPassword(password);
              user = new User(username,password,nickname,age);
              File file = new File(userDir,username+".obj");

              if(file.exists()){
                 response.sendRedirect("/static/have_user.html");
                 return;
              }
            try(
                    FileOutputStream fos = new FileOutputStream(file);//读文件的流 file为指定路径下的文件
                    ObjectOutputStream oos = new ObjectOutputStream(fos);//传入一个包含文件的流.将内容写入
            ) {
                    oos.writeObject(user);
                    response.sendRedirect("/reg_success.html");

                } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    @RequestMapping("/loginUser")
    public void login(HttpServletRequest request,HttpServletResponse response){
        System.out.println("处理用户登录请求!!!!!!!!!!!!!!!!!!!!!!!!");

        String un = request.getParameter("username");
        String pwd = request.getParameter("password");

        if(un == null || un.isEmpty() || pwd == null || pwd.isEmpty()){
            System.out.println("空!!!!!!!!!!!!!!!!!!");

            response.sendRedirect("/login_info_error.html");
            return;

        }

            File Uobj = new File(userDir,un+".obj");

            if(Uobj.exists()){
                try (FileInputStream fileInputStream = new FileInputStream(Uobj);
                     ObjectInputStream ois = new ObjectInputStream(fileInputStream))
                {
                    User user = (User)ois.readObject();
                    if (un.equals(user.getUsername()) && pwd.equals(user.getPassword())){
                        response.sendRedirect("/loginSuccess.html");
                    }else{
                        response.sendRedirect("/login_fail.html");
                    }

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }else{
                response.sendRedirect("/login_fail.html");
            }



    }

}
