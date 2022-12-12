package ch07;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PlayerDelete
 */
@WebServlet("/ch07/deletePlayer")
public class PlayerDelete extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String backNo_ = request.getParameter("backNo");
		int backNo = Integer.parseInt(backNo_);
		PlayerDao dao = new PlayerDao();
		dao.deletePlayer(backNo);
		
		response.sendRedirect("/jw/ch07/listPlayer");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
