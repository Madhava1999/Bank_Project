package Bank;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/Otpnew")
public class Otpnew extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	HttpSession session=req.getSession();
    Integer	otp=(Integer) session.getAttribute("otp");
    String tuserotp=req.getParameter("otp");
    int userotp=Integer.parseInt(tuserotp);
    PrintWriter write=resp.getWriter();
    if (otp!=null) {
		if (otp==userotp) {
			RequestDispatcher rd=req.getRequestDispatcher("Newpassword.html");
			rd.include(req, resp);
			
		}else {
			RequestDispatcher rd=req.getRequestDispatcher("Forgot.html");
			rd.include(req, resp);
			write.println("<center><h1> Invaild Otp </h1></center>");
		}
	}else {
		RequestDispatcher rd=req.getRequestDispatcher("Forgot.html");
		rd.include(req, resp);
		write.println("<center><h1> Time Out </h1></center>");
	}
}
}
