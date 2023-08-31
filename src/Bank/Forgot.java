package Bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.SeekableByteChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/Forgot")
public class Forgot extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String mobilenumber=req.getParameter("mobile");
	String url="jdbc:mysql://localhost:3306/teca41?user=root&password=12345";
	String select="select * from bank where moblienumber=?";
	HttpSession session=req.getSession();
	PrintWriter write=resp.getWriter();
	resp.setContentType("text/html");
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url);
		PreparedStatement ps=connection.prepareStatement(select);
		ps.setString(1, mobilenumber);
		ResultSet rs=ps.executeQuery();
		if (rs.next()) {
			int otp=otp();
			session.setAttribute("otp", otp);
			session.setMaxInactiveInterval(15);
			session.setAttribute("mobilenumber", mobilenumber);
			RequestDispatcher rd=req.getRequestDispatcher("Otpnew.html");
			rd.include(req, resp);
			write.println("<center><h1>OTP  :-  "+otp+"</h1></center>");
			
		}else {
			RequestDispatcher rd=req.getRequestDispatcher("Login.html");
			rd.include(req, resp);
			write.println("<center><h1>No Account Found On This Number</h1></center>");
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
public static int otp() {
	int num=0;
	Random r=new Random();
	while(true) {
		int otp=r.nextInt(1000000);
	  if(otp>100000 && otp<1000000) {
		num=otp;
		break;
	}
	}
	return num;
}
}
