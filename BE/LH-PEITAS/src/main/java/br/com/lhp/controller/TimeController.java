package br.com.lhp.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.lhp.model.Time;
import br.com.lhp.service.TimeService;

@WebServlet("/listarTimes")
public class TimeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TimeService service = new TimeService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("application/json");
		ArrayList<Time> listDeTimes = new ArrayList<>();
		service.listaTimes().stream().
		forEach((k) -> {
				listDeTimes.add(k);
		});
		Gson gson = new Gson();
		String jsonTxt = gson.toJson(listDeTimes);
		response.getWriter().print(jsonTxt);
		
	}

}
