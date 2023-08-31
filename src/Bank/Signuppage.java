package Bank;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet("/Signuppage")
public class Signuppage extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String name=req.getParameter("name");
	System.out.println(name);
	String gender=req.getParameter("gender");
	System.out.println(gender);
	String email=req.getParameter("email");
	System.out.println(email);
	String  mobilenumber=req.getParameter("mobilenumber");
	System.out.println(mobilenumber);
	String url="jdbc:mysql://localhost:3306/teca41?user=root&password=12345";
	//String insert="insert into bank values(?,?,?,?,?,)";
	String select="select * from bank where moblienumber=?";
	PrintWriter write=resp.getWriter();
	resp.setContentType("text/html");
	HttpSession session=req.getSession();
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url);
		PreparedStatement ps=connection.prepareStatement(select);
		ps.setString(1, mobilenumber);
		ResultSet rs=ps.executeQuery();
		if (rs.next()) {
			RequestDispatcher rd=req.getRequestDispatcher("Login.html");
			rd.include(req, resp);
			write.println("<center><h1>Already account present on this number</h1></center>");
		}else
		{
			session.setAttribute("name", name);
			session.setAttribute("gender", gender);
			session.setAttribute("email", email);
			session.setAttribute("mobilenumber", mobilenumber);
			session.setMaxInactiveInterval(15);
			int otp=otp();
			session.setAttribute("otp", otp);
			RequestDispatcher rd=req.getRequestDispatcher("Otp.html");
			rd.include(req, resp);
			write.println("<center><h1>Enter OTP  :  " +otp+ "</h1></center>");
			
			
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
