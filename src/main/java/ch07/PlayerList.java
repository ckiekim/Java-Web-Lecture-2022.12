package ch07;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PlayerList
 */
@WebServlet("/ch07/listPlayer")
public class PlayerList extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PlayerDao dao = new PlayerDao();
		List<Player> list = dao.getPlayers();
		
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
				+ "    <h1>LG Twins 야구선수 리스트</h1>"
				+ "    <hr>"
				+ "    <table border=\"1\">"
				+ "        <tr>"
				+ "            <th>백 넘버</th><th>선수명</th><th>포지션</th><th>생년월일</th><th>신장</th><th>액션</th>"
				+ "        </tr>";
		for (Player p: list) {
			data += "<tr>";
			data += "  <td>" + p.getBackNo() + "</td>";
			data += "  <td>" + p.getName() + "</td>";
			data += "  <td>" + p.getPosition() + "</td>";
			data += "  <td>" + p.getBirthDay() + "</td>";
			data += "  <td>" + p.getHeight() + "</td>";
			data += "  <td><a href=\"/jw/ch07/updatePlayer?backNo=" + p.getBackNo() + "\">수정</a>";
			data += "    <a href=\"/jw/ch07/deletePlayer?backNo=" + p.getBackNo() + "\">삭제</a></td>";
			data += "</tr>";
		}
		data += "    </table>"
				+ "    <br>"
				+ "    <a href=\"/jw/ch07/registerPlayer.html\">선수 등록</a>"
				+ "</body>\r\n"
				+ "</html>";
		out.print(data);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
