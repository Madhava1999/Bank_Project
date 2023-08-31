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
@WebServlet("/Mobile_Transition")
public class Mobile_Transition extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String mobile=req.getParameter("mobile");
	String url="jdbc:mysql://localhost:3306/teca41?user=root&password=12345";
	String select="select * from bank where moblienumber=?";
	PrintWriter write=resp.getWriter();
	resp.setContentType("text/html");
	HttpSession session=req.getSession();
	try {
		Connection connection=DriverManager.getConnection(url);
		PreparedStatement ps=connection.prepareStatement(select);
		ps.setString(1, mobile);
		ResultSet rs=ps.executeQuery();
		if (rs.next()) {
			session.setAttribute("usermobile", mobile);
			double res_amount=rs.getDouble(4);
			session.setAttribute("res_amount", res_amount);
			RequestDispatcher rd=req.getRequestDispatcher("Mobile_Money_Transition.html");
			rd.include(req, resp);
		}else {
			RequestDispatcher rd=req.getRequestDispatcher("Mobile_Transition.html");
			rd.include(req, resp);
			write.println("<center><h1>No Data Found :- "+mobile+"</h1></center>");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
