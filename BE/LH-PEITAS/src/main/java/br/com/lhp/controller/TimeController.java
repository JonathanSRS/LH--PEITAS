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

import br.com.lhp.dao.BodyReader;
import br.com.lhp.exception.ValorNaoExiste;
import br.com.lhp.model.Time;
import br.com.lhp.service.TimeService;

@WebServlet(urlPatterns = {"/listarTimes", "/base", "/postTime", "/buscarTime", "/excluir"})
public class TimeController extends HttpServlet implements BodyReader{
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
		if(listDeTimes.isEmpty()) {
			response.setStatus(404);
			response.getWriter().print("Not found");
			
		}else {
			Gson gson = new Gson();
			String jsonTxt = gson.toJson(listDeTimes);
			response.getWriter().print(jsonTxt);
		}
		
	}
	
	protected void base(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		if(request.getContentType() != null && request.getContentType().contains("application/json")) {
			try {
				BufferedReader  data =  request.getReader();
				JsonObject jsonTxt = (JsonObject) ler(data);
				ArrayList<Object> base = new ArrayList<>();
				
				String time = jsonTxt.get("time").getAsString();
				
				response.setContentType("application/json");
				
				service.BaseDeTimes(time).stream()
				.forEach((k) -> {
					base.add(k);
				});
				
				if(base.isEmpty()) {
					response.setStatus(404);
					response.getWriter().print("Not found");
				}else {
					Gson gson = new Gson();
					String resp = gson.toJson(base);
					response.getWriter().print(resp);
				}
				
			}catch(NullPointerException e) {
				response.setStatus(400);
				response.getWriter().print("Erro");
			}
		}else {
			response.setStatus(406);
			response.getWriter().print("Content-type Invalido");
		}
		
	}
	
	protected void buscarTime(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String time = request.getParameter("time");
		response.setContentType("application/json");
		ArrayList<Object> times = new ArrayList<>();
		service.buscarPorTime(time).stream()
		.forEach((k) -> {
			times.add(k);
		});
		if(times.isEmpty()) {
			response.setStatus(404);
			response.getWriter().print("Not found");
		}else {
			Gson gson = new Gson();
			String resp = gson.toJson(times);
			response.getWriter().print(resp);
		}
		
	}
	
	protected void criarTime(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		if(request.getContentType() != null && request.getContentType().contains("application/json")) {
			try {
				BufferedReader  data =  request.getReader();
				JsonObject jsonTxt = (JsonObject) ler(data);
				String nome = jsonTxt.get("nome").getAsString();
				String pais = jsonTxt.get("pais").getAsString();
				String liga = jsonTxt.get("liga").getAsString();
				
				Time time = new Time(nome, pais, liga);
				service.cadastrarTime(time);
				response.setStatus(201);
				response.getWriter().print("Sucesso");
				
			}catch(NullPointerException e) {
				response.setStatus(400);
				response.getWriter().print(e.getMessage());
			}
		}else {
			response.setStatus(406);
			response.getWriter().print("Content-type Invalido");
		}
	}
	
	protected void excluirTime(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException  {
		if(request.getContentType() != null && request.getContentType().contains("application/json")) {
			try {
				BufferedReader  data =  request.getReader();
				JsonObject jsonTxt = (JsonObject) ler(data);
				
				int id = jsonTxt.get("id_time").getAsInt();
				
				int result = service.excluirTime(id);
				if(result == 0) throw new ValorNaoExiste();
				response.setStatus(201);
				response.getWriter().print("Sucesso");
				
			}catch(NullPointerException e) {
				response.setStatus(400);
				response.getWriter().print("Erro");
			}
			
		}else {
			response.setStatus(406);
			response.getWriter().print("Content-type Invalido");
		}
	}

}
