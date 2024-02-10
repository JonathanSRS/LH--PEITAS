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

import br.com.lhp.model.Uniforme;
import br.com.lhp.service.UniformeService;

@WebServlet(urlPatterns = {"/postUniforme", "/updateInfo", "/delUniforme", "/listUniformes"})
public class UniformeController extends HttpServlet{
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
		BufferedReader  data =  request.getReader();
		StringBuilder obj = new StringBuilder();
		String linha;
		while((linha = data.readLine()) != null ){
			obj.append(linha);
			System.out.println(linha);
		}
		JsonObject jsonTxt = new Gson().fromJson(obj.toString(), JsonObject.class);
		String nome = jsonTxt.get("nome").getAsString();
		
		String cor = jsonTxt.get("cor").getAsString();
		String temporada = jsonTxt.get("temporada").getAsString();
		String time = jsonTxt.get("time").getAsString();
		
//		byte[] byteString = time.getBytes("UTF-8");
//		time = new String(byteString, "UTF-8");
		
		Uniforme uniforme = new Uniforme(nome, temporada ,cor);
		System.out.println(time);
		service.cadastrarUniforme(uniforme, time);
	}
	
	protected void listarUniformes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("application/json");
		ArrayList<Uniforme> list = new ArrayList<>();
		service.listarUniforme().stream().
		forEach((k) -> {
			list.add(k);
		});
		Gson gson = new Gson();
		String jsonTxt = gson.toJson(list);
		response.getWriter().print(jsonTxt);
	}
	
	protected void excluirUniforme(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		if(request.getContentType().equals("application/json")) {
			BufferedReader  data =  request.getReader();
			StringBuilder obj = new StringBuilder();
			String linha;
			while((linha = data.readLine()) != null ){
				obj.append(linha);
				System.out.println(linha);
			}
			JsonObject jsonTxt = new Gson().fromJson(obj.toString(), JsonObject.class);
			int id = jsonTxt.get("id_uniforme").getAsInt();
			
			service.excluir(id);
			
		}else {
			response.setStatus(406);
			response.getWriter().print("Content-type Invalido");
		}
	}
	
	protected void atualizarInfo(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		if(request.getContentType().equals("application/json")) {
			BufferedReader  data =  request.getReader();
			StringBuilder obj = new StringBuilder();
			String linha;
			while((linha = data.readLine()) != null ){
				obj.append(linha);
				System.out.println(linha);
			}
			JsonObject jsonTxt = new Gson().fromJson(obj.toString(), JsonObject.class);
			int id = jsonTxt.get("cod_camisa").getAsInt();
			String descricao = jsonTxt.get("informativo").getAsString();
			
			service.atualizarInformativo(id, descricao);
			
		}else {
			response.setStatus(406);
			response.getWriter().print("Content-type Invalido");
		}
	}
}
