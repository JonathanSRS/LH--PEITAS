package br.com.lhp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.lhp.model.Time;
import br.com.lhp.service.TimeService;

@WebServlet(urlPatterns = {"/listarTimes", "/base", "/postTime", "/buscarTime", "/excluir"})
public class TimeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TimeService service = new TimeService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String acao = request.getServletPath();
		switch (acao) {
		case "/listarTimes":
			lista(request, response);			
			break;
		case "/base":
			base(request, response);
			break;
		case "/buscarTime":
			buscarTime(request, response);
			break;
		default:
			break;
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String acao = request.getServletPath();
		switch (acao) {
		case "/postTime":
			criarTime(request, response);			
			break;
		default:
			break;
		}
		
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String acao = request.getServletPath();
		switch (acao) {
		case "/excluir":
			excluirTime(request, response);
			break;

		default:
			break;
		}
	}
	
	protected void lista(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
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
	
	protected void base(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		BufferedReader  data =  request.getReader();
		StringBuilder obj = new StringBuilder();
		String linha;
		ArrayList<Object> base = new ArrayList<>();
		while((linha = data.readLine()) != null ){
			obj.append(linha);
			System.out.println(linha);
		}
		
		JsonObject jsonTxt = new Gson().fromJson(obj.toString(), JsonObject.class);
		String time = jsonTxt.get("time").getAsString();
		
		response.setContentType("application/json");
		
		Gson gson = new Gson();
		service.BaseDeTimes(time).stream()
		.forEach((k) -> {
			base.add(k);
		});
		
		String resp = gson.toJson(base);
		response.getWriter().print(resp);
		
	}
	
	protected void buscarTime(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String time = request.getParameter("time");
		response.setContentType("application/json");
		Gson gson = new Gson();
		ArrayList<Object> times = new ArrayList<>();
		service.buscarPorTime(time).stream()
		.forEach((k) -> {
			times.add(k);
		});
		
		String resp = gson.toJson(times);
		response.getWriter().print(resp);
		
	}
	
	protected void criarTime(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		BufferedReader  data =  request.getReader();
		StringBuilder obj = new StringBuilder();
		String linha;
		while((linha = data.readLine()) != null ){
			obj.append(linha);
			System.out.println(linha);
		}
		JsonObject jsonTxt = new Gson().fromJson(obj.toString(), JsonObject.class);
		String nome = jsonTxt.get("nome").getAsString();
		String pais = jsonTxt.get("pais").getAsString();
		String liga = jsonTxt.get("liga").getAsString();
		Time time = new Time(nome, pais, liga);
		
		service.cadastrarTime(time);
	}
	
	protected void excluirTime(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException  {
		if(request.getContentType().equals("application/json")) {
			BufferedReader  data =  request.getReader();
			StringBuilder obj = new StringBuilder();
			String linha;
			while((linha = data.readLine()) != null ){
				obj.append(linha);
				System.out.println(linha);
			}
			JsonObject jsonTxt = new Gson().fromJson(obj.toString(), JsonObject.class);
			int id = jsonTxt.get("id_time").getAsInt();
			
			service.excluirTime(id);
			
			
		}else {
			response.setStatus(406);
			response.getWriter().print("Content-type Invalido");
		}
	}

}
