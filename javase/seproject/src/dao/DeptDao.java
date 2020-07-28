package dao;

import entity.Dept;
import util.JDBCUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeptDao {
    private PreparedStatement preparedStatement;
    private JDBCUtil util;
    private ResultSet resultSet;

    public DeptDao() {
        this.preparedStatement = null;
        this.util = new JDBCUtil();
        this.resultSet = null;
    }

    //增（增加部门的方法）
    public int insert(int deptno, String dname, String loc){
        String sql = "insert into dept(deptno, dname, loc) values(?,?,?)";
        preparedStatement = util.creatPreparedStatement(sql);
        int result = 0;
        try {
            preparedStatement.setInt(1, deptno);//替换第一个占位符
            preparedStatement.setString(2, dname);//替换第二个占位符
            preparedStatement.setString(3, loc);//替换第三个占位符
            result = preparedStatement.executeUpdate();//执行sql语句并返回更新的行数，1为成功
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            util.close();//关闭资源
        }
        return result;
    }

    //改（改动指定部门名称与地址的方法）
    public int update(int deptno, String dname, String loc){
        String sql = "update dept set dname = ?, loc = ? where deptno = ?";
        preparedStatement = util.creatPreparedStatement(sql);
        int result = 0;
        try {
            preparedStatement.setString(1, dname);
            preparedStatement.setString(2, loc);
            preparedStatement.setInt(3, deptno);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            util.close();
        }
        return result;
    }

    //删（删除指定部门的方法）
    public int delete(int deptno){
        String sql = "delete from dept where deptno = ?";
        preparedStatement = util.creatPreparedStatement(sql);
        int result = 0;
        try {
            preparedStatement.setInt(1, deptno);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            util.close();
        }
        return result;
    }

    //查（查询部门表信息）、返回的是一个表的实体类的集合
    public List<Dept> select(){
        String sql = "select * from dept";
        preparedStatement = util.creatPreparedStatement(sql);
        List<Dept> result = new ArrayList<>();
        try {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Dept dept = new Dept(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                result.add(dept);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            util.close(resultSet);
        }
        return result;
    }
}
