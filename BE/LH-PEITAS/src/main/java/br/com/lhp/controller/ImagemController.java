package br.com.lhp.controller;


import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import br.com.lhp.dao.BodyReader;
import br.com.lhp.model.Imagem;
import br.com.lhp.service.ImagemService;

@WebServlet(urlPatterns = {"/cadastrarImagem", "/imagem"})
public class ImagemController extends HttpServlet implements BodyReader{
	private static final long serialVersionUID = 1L;
	ImagemService service = new ImagemService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getContentType().equals("application/json")) {
			BufferedReader  data =  request.getReader();
			JsonObject jsonTxt = ler(data);
			
			try {
				Imagem img = new Imagem(jsonTxt.get("pasta").getAsString(), jsonTxt.get("link").getAsString());
				try {
					service.cadastrarImagem(img, Integer.parseInt(jsonTxt.get("cod").getAsString()));
					response.setStatus(201);
					response.getWriter().print("Sucesso");
				}catch(RuntimeException e) {
					response.setStatus(500);
					response.getWriter().print("Erro "+ e.getMessage());
					
				}
			}catch(NullPointerException e) {
				response.setStatus(404);
				response.getWriter().print("Not found");
			}
			
		}else {
			response.setStatus(406);
			response.getWriter().print("Content-type Invalido");
		}
		response.getStatus();
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if(request.getContentType().equals("application/json")) {
			BufferedReader  data =  request.getReader();
			JsonObject jsonTxt = ler(data);
			
			int id = jsonTxt.get("id_imagem").getAsInt();
			int result = service.excluirImagem(id);
			
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
