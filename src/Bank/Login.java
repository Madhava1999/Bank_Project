package Bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/Login")
public class Login extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String mobilenumber=req.getParameter("mobilenumber");
	String password=req.getParameter("password");
	System.out.println("1");
	String url="jdbc:mysql://localhost:3306/teca41?user=root&password=12345";
	String select="select * from bank where moblienumber=? and password=?";
	System.out.println("2");
	HttpSession session=req.getSession();
	PrintWriter write=resp.getWriter();
	resp.setContentType("text/html");
	System.out.println("3");
	try {
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("4");
		Connection connection=DriverManager.getConnection(url);
		PreparedStatement ps=connection.prepareStatement(select);
		System.out.println("5");
		ps.setString(1, mobilenumber);
		ps.setString(2, password);
		ResultSet rs=ps.executeQuery();
		if (rs.next()) {
			System.out.println("6");
			double oldamount=rs.getDouble(4);
			session.setAttribute("mainamount", oldamount);
			session.setAttribute("mobilenumber", mobilenumber);
			session.setAttribute("password", password);
			session.setMaxInactiveInterval(15);
			System.out.println("7");
			RequestDispatcher rd=req.getRequestDispatcher("Homepage.html");
			rd.include(req, resp);
			System.out.println("8");
			
			
		}else
		{
			RequestDispatcher rd=req.getRequestDispatcher("Login.html");
			rd.include(req, resp);
			write.println("<center><h1>invaild data</h1></center>");
			write.println("<center><h1>try angin</h1></center>");
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
}
