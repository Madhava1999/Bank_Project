package Bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/Newpassword")
public class Newpassword extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session=req.getSession();
    String password=req.getParameter("password");
    String cpassword=req.getParameter("cpassword");
    String	number=(String) session.getAttribute("mobilenumber");
	PrintWriter write=resp.getWriter();
	resp.setContentType("text/html");
	String url="jdbc:mysql://localhost:3306/teca41?user=root&password=12345";
	String update="update bank set password=? where moblienumber=?";
	if (password.equals(cpassword)) {
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(update);
			ps.setString(1, password);
			ps.setString(2, number);
			int num=ps.executeUpdate();
			if (num!=0) {
				RequestDispatcher rd=req.getRequestDispatcher("Login.html");
				rd.include(req, resp);
				write.println("<center><h1>Password change successfully</h1></center>");
			}else {
				RequestDispatcher rd=req.getRequestDispatcher("Forgot.html");
				rd.include(req, resp);
				write.println("<center><h1>404 ERROR</h1></center>");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	else {
		RequestDispatcher rd=req.getRequestDispatcher("Forgot.html");
		rd.include(req, resp);
		write.println("<center><h1>password not matching</h1></center>");
	}
}
}
