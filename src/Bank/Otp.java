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
@WebServlet("/Otp")
public class Otp extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String totp=req.getParameter("otp");
	int userotp=Integer.parseInt(totp);
	
	HttpSession session=req.getSession();
	Integer otp=(Integer)session.getAttribute("otp");
//	String name=(String) session.getAttribute("name");
//	String gender=(String) session.getAttribute("gender");
//	String email=(String) session.getAttribute("email");
//	String mobile=(String) session.getAttribute("mobilenumber");
	PrintWriter write=resp.getWriter();
	resp.setContentType("text/html");
//	String url="jdbc:mysql://localhost:3306/teca41?user=root&password=12345";
//	String insert="insert into bank (moblienumber, name, gender, email) values(?,?,?,?)";

	if (otp!=null) {
		//otp checking
		if (otp==userotp) {
			
			RequestDispatcher rd=req.getRequestDispatcher("Password.html");
			rd.include(req, resp);
//			try {
//				Connection connection=DriverManager.getConnection(url);
//				PreparedStatement ps=connection.prepareStatement(insert);
//				ps.setString(1, mobile);
//				ps.setString(2, name);
//				ps.setString(3, gender);
//				ps.setString(4, email);
//			    int num=ps.executeUpdate();
//				if (num!=0) {
//					RequestDispatcher rd=req.getRequestDispatcher("Password.html");
//					rd.include(req, resp);
//					}else {
//						RequestDispatcher rd=req.getRequestDispatcher("Signuppage.html.html");
//						rd.include(req, resp);
//						write.println("<center><h1> 404 error </h1></center>");
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		  }else {
		RequestDispatcher rd=req.getRequestDispatcher("Signuppage.html");
			rd.include(req, resp);
			write.println("<center><h1> Invaild Otp </h1></center>");
		}
	}else {
		RequestDispatcher rd=req.getRequestDispatcher("Signuppage.html");
		rd.include(req, resp);
		write.println("<center><h1> Time Out </h1></center>");
	}
	
}
}
