package ch14.users3;

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
 * Servlet implementation class UserServiceController
 */
@WebServlet({ "/ch14/users3/list", "/ch14/users3/login", "/ch14/users3/logout",
			  "/ch14/users3/register", "/ch14/users3/update", "/ch14/users3/delete" })
public class User3ServiceController extends HttpServlet {
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String[] uri = request.getRequestURI().split("/");
		String action = uri[uri.length - 1];
		UserDao dao = new UserDao();
		HttpSession session = request.getSession();
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		String uid = null, pwd = null, pwd2 = null, uname = null, email = null;
		User u = null;
		RequestDispatcher rd = null;
		switch(action) {
		case "list":
			List<User> list = dao.listUsers();
			request.setAttribute("userList", list);
			rd = request.getRequestDispatcher("/ch14/users3/list.jsp");
			rd.forward(request, response);
			break;
		case "login":
			if (request.getMethod().equals("GET")) {
				response.sendRedirect("/jw/ch14/users3/login.jsp");
			} else {
				uid = request.getParameter("uid");
				pwd = request.getParameter("pwd");
				u = dao.getUserInfo(uid);
				if (u.getUid() != null) {		// uid 가 존재
					if (BCrypt.checkpw(pwd, u.getPwd())) {
						// System.out.println(u.getUid() + ", " + u.getUname());
						session.setAttribute("uid", u.getUid());
						session.setAttribute("uname", u.getUname());
						
						// Welcome message
						request.setAttribute("msg", u.getUname() + "님 환영합니다.");
						request.setAttribute("url", "/jw/ch14/users3/list");
						rd = request.getRequestDispatcher("/ch14/users3/alertMsg.jsp");
						rd.forward(request, response);
					} else {
						// 재 로그인 페이지
						request.setAttribute("msg", "잘못된 패스워드 입니다. 다시 입력하세요.");
						request.setAttribute("url", "/jw/ch14/users3/login");
						rd = request.getRequestDispatcher("/ch14/users3/alertMsg.jsp");
						rd.forward(request, response);
					}
				} else {				// uid 가 없음
					// 회원 가입 페이지로 안내
					request.setAttribute("msg", "회원 가입 페이지로 이동합니다.");
					request.setAttribute("url", "/jw/ch14/users3/register");
					rd = request.getRequestDispatcher("/ch14/users3/alertMsg.jsp");
					rd.forward(request, response);
				}
			}
			break;
		case "logout":
			session.invalidate();
			response.sendRedirect("/jw/ch14/users3/list");
			break;
		case "register":
			if (request.getMethod().equals("GET")) {
				response.sendRedirect("/jw/ch14/users3/register.jsp");
			} else {
				uid = request.getParameter("uid");
				pwd = request.getParameter("pwd");
				pwd2 = request.getParameter("pwd2");
				uname = request.getParameter("uname");
				email = request.getParameter("email");
				if (pwd.equals(pwd2)) {
					u = new User(uid, pwd, uname, email);
					dao.registerUser(u);
					response.sendRedirect("/jw/ch14/users3/list");
				} else {
					request.setAttribute("msg", "패스워드 입력이 잘못되었습니다.");
					request.setAttribute("url", "/jw/ch14/users3/register");
					rd = request.getRequestDispatcher("/ch14/users3/alertMsg.jsp");
					rd.forward(request, response);
				}
			}
			break;
		case "update":
			if (request.getMethod().equals("GET")) {
				uid = request.getParameter("uid");
				u = dao.getUserInfo(uid);
				request.setAttribute("user", u);
				rd = request.getRequestDispatcher("/ch14/users3/update.jsp");
				rd.forward(request, response);
			} else {								// POST
				uid = request.getParameter("uid");
				uname = request.getParameter("uname");
				email = request.getParameter("email");
				u = new User(uid, uname, email);
				dao.updateUser(u);
				session.setAttribute("uname", uname);
				response.sendRedirect("/jw/ch14/users3/list");
			}
			break;
		case "delete":
			uid = request.getParameter("uid");
			dao.deleteUser(uid);
			response.sendRedirect("/jw/ch14/users3/list");
			break;
		default:
			System.out.println(request.getMethod() + " 잘못된 경로");
		}
	}

}
