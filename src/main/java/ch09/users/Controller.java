package ch09.users;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Servlet implementation class Controller
 */
@WebServlet(
		name = "UserController", 
		urlPatterns = { 
				"/ch09/users/list", 
				"/ch09/users/login", 
				"/ch09/users/logout",
				"/ch09/users/register"
		})
public class Controller extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String requestUri = request.getRequestURI();
		UserDao dao = new UserDao();
		HttpSession session = request.getSession();
		
		if (requestUri.contains("list")) {
			List<User> list = dao.listUsers();
			RequestDispatcher rd = request.getRequestDispatcher("/ch09/users/listView");
			request.setAttribute("userList", list);
			rd.forward(request, response);
		} else if (requestUri.contains("logout")) {
			System.out.println(session.getAttribute("uid"));
			System.out.println(session.getAttribute("uname"));
			session.invalidate();
			response.sendRedirect("/jw/ch09/users/list");
		} else {
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String requestUri = request.getRequestURI();
		UserDao dao = new UserDao();
		HttpSession session = request.getSession();
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		if (requestUri.contains("login")) {
			String uid = request.getParameter("uid");
			String pwd = request.getParameter("pwd");
			User u = dao.getUserInfo(uid);
			if (u.getUid() != null) {		// uid 가 존재
				if (BCrypt.checkpw(pwd, u.getPwd())) {
					// System.out.println(u.getUid() + ", " + u.getUname());
					session.setAttribute("uid", u.getUid());
					session.setAttribute("uname", u.getUname());
					
					// Welcome message
					out.print("<script>");
					out.print("alert('" + u.getUname() + "님 환영합니다." + "');");
					out.print("location.href = '" + "/jw/ch09/users/list" + "';");
					out.print("</script>");
				} else {
					// 재 로그인 페이지
					out.print("<script>");
					out.print("alert('잘못된 패스워드 입니다. 다시 입력하세요.');");
					out.print("location.href = '" + "/jw/ch09/users/login.html" + "';");
					out.print("</script>");
				}
			} else {				// uid 가 없음
				// 회원 가입 페이지로 안내
				out.print("<script>");
				out.print("alert('회원 가입 페이지로 이동합니다.');");
				out.print("location.href = '" + "/jw/ch09/users/register.html" + "';");
				out.print("</script>");
			}
		} else if (requestUri.contains("register")) {
			
		} else {
			
		}
	}

}
