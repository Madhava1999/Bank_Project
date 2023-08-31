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
@WebServlet("/Amount")
public class Amount extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String tamount=req.getParameter("amount");
	System.out.println(tamount);
	double amount=Double.parseDouble(tamount);
	PrintWriter write=resp.getWriter();
	resp.setContentType("text/html");
	HttpSession session=req.getSession();
	String mobile=(String)session.getAttribute("mobilenumber");
	String password=(String)session.getAttribute("password");
	System.out.println("1");
	if (mobile!=null) {
		
	
	if (amount>99) {
		String url="jdbc:mysql://localhost:3306/teca41?user=root&password=12345";
		String update="update bank set amount=? where moblienumber=? and password=?";
		System.out.println("2");
		//String select="select * from bank where moblienumber=? and name=?";
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(update);
			System.out.println("3");
			double mainamount=(Double)session.getAttribute("mainamount");
			double newamount=mainamount+amount;
			System.out.println("4");
			if (amount<=100000) {
				ps.setDouble(1, newamount);
				ps.setString(2, mobile);
				ps.setString(3, password);
				int num=ps.executeUpdate();
				if (num!=0) {
					RequestDispatcher rd=req.getRequestDispatcher("Homepage.html");
					rd.include(req, resp);
					write.println("<center><h1>Amount creadited </h1></center>");
				}else {
					write.println("<center><h1> 404 error </h1></center>");
				}
				
			}
			else {
				RequestDispatcher rd=req.getRequestDispatcher("Amount.html");
				rd.include(req, resp);
				write.println("<center><h1>max amount only 100000/- </h1></center>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}else {
		RequestDispatcher rd=req.getRequestDispatcher("Amount.html");
		rd.include(req, resp);
		write.println("<center><h1> min creadit 100 /- </h1></center>");
	}
	}
	else {
		RequestDispatcher rd=req.getRequestDispatcher("Login.html");
		rd.include(req, resp);
		write.println("<center><h1>session Time out </h1></center>");	
	}
}
}
