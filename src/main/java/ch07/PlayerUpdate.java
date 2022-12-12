package ch07;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PlayerUpdate
 */
@WebServlet("/updatePlayer")
public class PlayerUpdate extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String backNo_ = request.getParameter("backNo");
		int backNo = Integer.parseInt(backNo_);
		
		PlayerDao dao = new PlayerDao();
		Player p = dao.getPlayer(backNo);
		
		response.setCharacterEncoding("utf-8");		// 굳이 안해도 인코딩 오류 발생하지 않음
		response.setContentType("text/html; charset=utf-8");	// 반드시 세팅해주어야 함
		PrintWriter out = response.getWriter();
		String data = "";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
