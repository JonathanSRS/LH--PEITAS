package br.com.lhp.controller;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.lhp.model.Imagem;
import br.com.lhp.service.ImagemService;

@WebServlet("/cadastrarImagem")
public class ImagemController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	ImagemService service = new ImagemService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imagem = request.getParameter("link");
		String pasta = request.getParameter("pasta");
		String cod = request.getParameter("codigo");
		Imagem img = new Imagem(pasta, imagem);
		try {
			service.cadastrarImagem(img, Integer.parseInt(cod));
			response.setStatus(200);
			response.getWriter().print("Sucesso");
		}catch(RuntimeException e) {
			response.setStatus(500);
			response.getWriter().print("Erro "+ e.getMessage());

		}
		response.getStatus();
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
	}
}
