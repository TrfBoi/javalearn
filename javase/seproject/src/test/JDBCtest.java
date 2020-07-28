package test;

import dao.DeptDao;
import entity.Dept;
import util.JDBCUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JDBCtest {
    public static void main(String[] args) {
        //要求使用者输入的相关变量
        Scanner scanner = new Scanner(System.in);
        String usrname, password;//用户登录名，用户登录密码
        int deptno;//部门编号
        String loc, dname;//部门地址与部门名称

        DeptDao dao = new DeptDao();//dao对象
        JDBCUtil util = new JDBCUtil();//工具对象

        //登录实现
        System.out.println("****欢迎来到部门管理界面****");
        System.out.println("****请输入用户名****");
        usrname = scanner.nextLine();
        System.out.println("****请输入用户密码****");
        password = scanner.nextLine();

        String sql = "select count(*) from user where uname = ? and upassword = ?";
        PreparedStatement preparedStatement;
        preparedStatement = util.creatPreparedStatement(sql);
        ResultSet resultSet = null;

        int result = -2;//储存各种输入与返回值
        try {
            preparedStatement.setString(1, usrname);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            util.close(resultSet);//关闭各种资源
        }
        if (result == 1){
            System.out.println("登录成功");
        }else{
            System.out.println("密码或用户名错误");
            return;
        }

        //增删改查功能实现
        while (true){
            System.out.println("****查询部门信息请输入：1****");
            System.out.println("****删除部门信息请输入：2****");
            System.out.println("****改动部门信息请输入：3****");
            System.out.println("****增加部门信息请输入：4****");
            System.out.println("****退出系统请输入：0****");
            int input = scanner.nextInt();

            if (input == 1) {
                List<Dept> depts = new ArrayList<>();
                depts = dao.select();
                for (Dept dept : depts){
                    System.out.println(dept);
                }
            } else if(input == 2){
                System.out.println("请输入要删除的部门");
                deptno = scanner.nextInt();
                result = dao.delete(deptno);
                if (result == 1) System.out.println("删除成功");
                else System.out.println("删除失败");
            }else if (input == 3){
                System.out.println("请输入要更新的部门");
                deptno = scanner.nextInt();
                System.out.println("请输入部门新名字");
                dname = scanner.next();
                System.out.println("请输入部门新地址");
                loc = scanner.next();
                result = dao.update(deptno, dname, loc);
                if (result == 1) System.out.println("修改成功");
                else System.out.println("修改失败");
            }else if (input == 4){
                System.out.println("请输入新部门编号");
                deptno = scanner.nextInt();
                System.out.println("请输入新部门名称");
                dname = scanner.next();
                System.out.println("请输入新部门地址");
                loc = scanner.next();
                result = dao.insert(deptno, dname, loc);
                if (result == 1) System.out.println("增加部门成功");
                else System.out.println("增加部门失败");
            }else if (input == 0){
                break;
            }else{
                System.out.println("输入有误");
            }
        }
    }
}
