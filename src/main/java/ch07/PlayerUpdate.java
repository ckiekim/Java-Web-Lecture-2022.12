package ch07;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PlayerUpdate
 */
@WebServlet("/ch07/updatePlayer")
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
		String data = "<!DOCTYPE html>"
				+ "<html lang=\"ko\">"
				+ "<head>"
				+ "    <meta charset=\"UTF-8\">"
				+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
				+ "    <title>LG Twins 야구선수</title>"
				+ "    <style>"
				+ "        td { text-align: center; padding: 3px;}"
				+ "    </style>"
				+ "</head>"
				+ "<body style=\"margin: 40px;\">"
				+ "    <h1>LG Twins 야구선수 수정</h1>"
				+ "    <hr>"
				+ "    <form action=\"/jw/ch07/updatePlayer\" method=\"post\">";
		data += "<input type=\"hidden\" name=\"backNo\" value=\"" + p.getBackNo() + "\">";
		data += "        <table>"
				+ "            <tr>"
				+ "                <td>백 넘버</td>";
		data += "<td><input type=\"text\" name=\"backNo\" value=\"" + p.getBackNo() + "\" disabled></td>";
		data += "            </tr>"
				+ "            <tr>"
				+ "                <td>선수명</td>";
		data += "<td><input type=\"text\" name=\"name\" value=\"" + p.getName() + "\"></td>";
		data += "            </tr>"
				+ "            <tr>"
				+ "                <td>포지션</td>  <td>";
		p.getPosition();
		for (Position pos: Position.values()) {
			data += "<input type=\"radio\" name=\"position\" value=\"" + pos.toString() + "\"";
			if (pos.equals(p.getPosition()))
				data += " checked";
			data += ">" + pos.toString();
		}
		data += "                </td>"
				+ "            </tr>"
				+ "            <tr>"
				+ "                <td>생년월일</td>";
		data += "<td><input type=\"date\" name=\"birthDay\" value=\"" + p.getBirthDay() + "\"></td>";
		data += "            </tr>"
				+ "            <tr>"
				+ "                <td>신장(cm)</td>";
		data += "<td><input type=\"number\" name=\"height\" value=\"" + p.getHeight() + "\"></td>";
		data += "            </tr>"
				+ "            <tr>"
				+ "                <td colspan=\"2\"><input type=\"submit\" value=\"수정\"></td>"
				+ "            </tr>"
				+ "        </table>"
				+ "    </form>"
				+ "</body>"
				+ "</html>";
		out.print(data);
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
		dao.updatePlayer(p);
		
		response.sendRedirect("/jw/ch07/listPlayer");
	}

}
