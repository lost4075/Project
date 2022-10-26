import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "AjaxRequest", value = "/AjaxRequest")
public class AjaxRequest extends HttpServlet {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/project?&useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "lgd2518469511";

    public AjaxRequest() {
        super();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Connection conn = null;
        String sqlStr="select * from student";
        PrintWriter out=response.getWriter();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            //out.print("<font color='red'>连接数据库成功</font>");

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery( sqlStr );
            StringBuilder html=new StringBuilder();
            //制作表头
            html.append("<table width=\"50%\" border=\"1px\">");
            html.append("<tr>");
            html.append("<th>学号</th>");
            html.append("<th>姓名</th>");
            html.append("<th>第一志愿</th>");
            html.append("<th>第二志愿</th>");
            html.append("<th>第三志愿</th>");
            html.append("</tr>");

            while(rs.next()) {
                int id  = rs.getInt("学号");
                String name = rs.getString("姓名");
                String wish_1 = rs.getString("第一志愿");
                String wish_2 = rs.getString("第二志愿");
                String wish_3 = rs.getString("第三志愿");

                html.append("<tr>");
                html.append("<td>"+id+"</td>");
                html.append("<td>"+name+"</td>");
                html.append("<td>"+wish_1+"</td>");
                html.append("<td>"+wish_2+"</td>");
                html.append("<td>"+wish_3+"</td>");
                html.append("</tr>");
            }
            html.append("</table>");
            out.print(html.toString());

        }catch (ClassNotFoundException e1){
            out.print(e1+"驱动程序找不到");
        }catch(SQLException e2){
            out.println(e2);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
