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
@WebServlet("/Mobile_Money_Transition")
public class Mobile_Money_Transition extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String tamount=req.getParameter("amount");
	int enter_Amount=Integer.parseInt(tamount);
	System.out.println("enter_Amount "+enter_Amount);
	String url="jdbc:mysql://localhost:3306/teca41?user=root&password=12345";
	String update="update bank set amount=? where moblienumber=?";
	PrintWriter write=resp.getWriter();
	resp.setContentType("text/html");
	HttpSession session=req.getSession();
	String re_Number=(String) session.getAttribute("usermobile");
	System.out.println("re_Number "+re_Number);
	Double res_Amount=(Double) session.getAttribute("res_amount");
	System.out.println("res_Amount "+res_Amount);
	Double sender_Amount=(Double)session.getAttribute("mainamount");
	System.out.println("sender_Amount "+sender_Amount);
	String sender_mobile=(String) session.getAttribute("mobilenumber");
	if (sender_Amount!=null) {
		if (sender_Amount>=enter_Amount) {
			double total_amount=enter_Amount+res_Amount;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection=DriverManager.getConnection(url);
				PreparedStatement ps=connection.prepareStatement(update);
				ps.setDouble(1, total_amount);
				ps.setString(2, re_Number);
				
				int num=ps.executeUpdate();
				if (num!=0) {
					double remain_Amount=sender_Amount-enter_Amount;
					String update1="update bank set amount=? where moblienumber=?";
					PreparedStatement ps1=connection.prepareStatement(update);
					ps1.setDouble(1, remain_Amount);
					ps1.setString(2, sender_mobile);
					int num1=ps1.executeUpdate();
					if (num1!=0) {
						RequestDispatcher rd=req.getRequestDispatcher("Homepage.html");
						rd.include(req, resp);
						write.println("<center><h1>Money As Been Credit On :- "+re_Number+"</h1></center>");
					}else {
						RequestDispatcher rd=req.getRequestDispatcher("Login.html");
						rd.include(req, resp);
						write.println("<center><h1>404 ERROR</h1></center>");
					}
					
					
				}else {
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			RequestDispatcher rd=req.getRequestDispatcher("Homepage.html");
			rd.include(req, resp);
			write.println("<center><h1>You Have Insufficient Balance</h1></center>");
		}
	}else {
		RequestDispatcher rd=req.getRequestDispatcher("Mobile_Money_Transition.html");
		rd.include(req, resp);
		write.println("<center><h1> Time Out </h1></center>");
	}
	
}
}
