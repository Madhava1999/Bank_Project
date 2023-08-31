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
@WebServlet("/Withdraw")
public class Withdraw extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String tamount=req.getParameter("amount");
	double amount=Double.parseDouble(tamount);
	HttpSession session=req.getSession();
	String mobile=(String)session.getAttribute("mobilenumber");
	String password=(String)session.getAttribute("password");
	PrintWriter write=resp.getWriter();
	resp.setContentType("text/html");
	System.out.println("a");
	String url="jdbc:mysql://localhost:3306/teca41?user=root&password=12345";
	String update="update bank set amount=? where moblienumber=? and password=?";
	System.out.println("b");
	Double old_amount=(Double)session.getAttribute("mainamount");
	
	System.out.println("oldamount "+old_amount);
	if (amount>0) {
		System.out.println("c");
		if (password !=null) {
		double new_amount=old_amount-amount;
		System.out.println("d");
		if (new_amount>0) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection=DriverManager.getConnection(url);
				PreparedStatement ps=connection.prepareStatement(update);
				ps.setDouble(1, new_amount);
				ps.setString(2, mobile);
				ps.setString(3, password);
				int num=ps.executeUpdate();
				if (num!=0) {
					RequestDispatcher rd=req.getRequestDispatcher("Homepage.html");
					rd.include(req, resp);
					write.println("<center><h1>Withdraw down</h1></center>");
				}else {
					RequestDispatcher rd=req.getRequestDispatcher("Login.html");
					rd.include(req, resp);
					write.println("<center><h1>Seccion time_Out=2</h1></center>");
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			RequestDispatcher rd=req.getRequestDispatcher("Withdraw.html");
			rd.include(req, resp);
			write.println("<center><h1>insufficient Balance</h1></center>");
		}
	}else {
		RequestDispatcher rd=req.getRequestDispatcher("Login.html");
		rd.include(req, resp);
		write.println("<center><h1>Seccion time out=1</h1></center>");
	}
	}else {
		RequestDispatcher rd=req.getRequestDispatcher("Withdraw.html");
		rd.include(req, resp);
		write.println("<center><h1>invaild data </h1></center>");
	}
	
}
}
