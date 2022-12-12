package ch07;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PlayerInsert
 */
@WebServlet("/ch07/registerPlayer")
public class PlayerInsert extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String backNo_ = request.getParameter("backNo");
		String name = request.getParameter("name");
		String position = request.getParameter("position");
		String birthDay = request.getParameter("birthDay");
		String height_ = request.getParameter("height");
		int backNo = Integer.parseInt(backNo_);
		int height = Integer.parseInt(height_);
		
		Player p = new Player(backNo, name, Position.valueOf(position), LocalDate.parse(birthDay), height);
		PlayerDao dao = new PlayerDao();
		dao.insertPlayer(p);
		response.sendRedirect("/jw/ch07/listPlayer");
	}

}
