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
import br.com.lhp.model.Uniforme;
import br.com.lhp.service.UniformeService;

@WebServlet(urlPatterns = {"/postUniforme", "/updateInfo", "/delUniforme", "/listUniformes"})
public class UniformeController extends HttpServlet implements BodyReader{
	private static final long serialVersionUID = 1L;
	
	UniformeService service = new UniformeService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getServletPath();
		switch (acao) {
		case "/listUniformes":
			listarUniformes(request, response);
			break;

		default:
			break;
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getServletPath();
		switch (acao) {
		case "/postUniforme":
			cadastrarUniforme(request, response);
			break;
			
		default:
			break;
		}
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getServletPath();
		switch (acao) {
		case "/updateInfo":
			atualizarInfo(request, response);
			break;
			
		default:
			break;
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getServletPath();
		switch (acao) {
		case "/delUniforme":
			excluirUniforme(request, response);
			break;
			
		default:
			break;
		}
	}
	
	
	protected void cadastrarUniforme(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getContentType().equals("application/json")) {
			BufferedReader  data =  request.getReader();
			StringBuilder obj = (StringBuilder) ler(data);
			
			JsonObject jsonTxt = new Gson().fromJson(obj.toString(), JsonObject.class);
			try {
				String nome = jsonTxt.get("nome").getAsString();
				String cor = jsonTxt.get("cor").getAsString();
				String temporada = jsonTxt.get("temporada").getAsString();
				String time = jsonTxt.get("time").getAsString();
				try {
					Uniforme uniforme = new Uniforme(nome, temporada ,cor);
					service.cadastrarUniforme(uniforme, time);
					response.setStatus(201);
					response.getWriter().print("Sucesso");
					
				}catch(RuntimeException e) {
					response.setStatus(500);
					response.getWriter().print("Problemas internos por favor contate o administrador do serviço");;
				}
				
			}catch(NullPointerException e) {
				response.setStatus(400);
				response.getWriter().print(e.getMessage());
			}
		}else {
			response.setStatus(406);
			response.getWriter().print("Content-type Invalido");
		}
	}
	
	protected void listarUniformes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("application/json");
		ArrayList<Uniforme> list = new ArrayList<>();
		service.listarUniforme().stream().
		forEach((k) -> {
			list.add(k);
		});
		if(list.isEmpty()) {
			response.setStatus(404);
			response.getWriter().print("Not Found");
		}else {
			Gson gson = new Gson();
			String jsonTxt = gson.toJson(list);
			response.getWriter().print(jsonTxt);
		}
	}
	
	protected void excluirUniforme(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		if(request.getContentType().equals("application/json")) {
			BufferedReader  data =  request.getReader();
			StringBuilder obj = (StringBuilder) ler(data);
			
			JsonObject jsonTxt = new Gson().fromJson(obj.toString(), JsonObject.class);
			int id = jsonTxt.get("id_uniforme").getAsInt();
			
			int result = service.excluir(id);
			if(result == 0) {
				response.setStatus(400);
				response.getWriter().print("Erro");
			}else {
				response.setStatus(200);
				response.getWriter().print("Sucesso");
			}
			
		}else {
			response.setStatus(406);
			response.getWriter().print("Content-type Invalido");
		}
	}
	
	protected void atualizarInfo(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		if(request.getContentType().equals("application/json")) {
			BufferedReader  data =  request.getReader();
			StringBuilder obj = (StringBuilder) ler(data);
			
			
			JsonObject jsonTxt = new Gson().fromJson(obj.toString(), JsonObject.class);
			int id = jsonTxt.get("cod_camisa").getAsInt();
			String descricao = jsonTxt.get("informativo").getAsString();
			
			int result = service.atualizarInformativo(id, descricao);
			
			if(result == 0) {
				response.setStatus(400);
				response.getWriter().print("Erro");
			}else {
				response.setStatus(200);
				response.getWriter().print("Sucesso");
			}
			
		}else {
			response.setStatus(406);
			response.getWriter().print("Content-type Invalido");
		}
	}
}
