package br.com.lhp.configuracao;



import java.util.Set;

import br.com.lhp.model.Imagem;
import br.com.lhp.model.Uniforme;
import br.com.lhp.service.ImagemService;
import br.com.lhp.service.TimeService;
import br.com.lhp.service.UniformeService;

public class LhpApplication {
	private static TimeService service = new TimeService();
	private static UniformeService serviceUni = new UniformeService();
	private static ImagemService seriviceIm = new ImagemService();
	
	static String time = "al-nassr";
	static String pais = "espanha";
	static String liga = "la liga";
//	static Date temporada = new Date(2024, 05,10);
	static String temporada = "2024/05";
	public static void main(String[] args) {
		
//		System.out.println("Teste de Cadastro de times");
//		service.cadastrarTime(new Time(time, pais, liga));
//		System.out.println("Time cadastrado com sucesso");
		
//		System.out.println("Teste de Cadastro de uniforme");
//		serviceUni.cadastrarUniforme(new Uniforme("primeiro uniforme", temporada, "amarelo"), time);
		
//		serviceUni.atualizarInformativo(24, "uniforme de Goleiro");
//		System.out.println("Atualizar Informativo");
		
		
//		System.out.println("Lista de Times");
//		Set<Time> times = service.listaTimes();
//		times.stream().forEach(System.out::println);
//		
//		int cod = service.buscarPorTime("america".toLowerCase());
//		System.out.println("Resultado Busca por time: " + cod);
//		System.out.println("Teste Query");
//		Set<Object> base = service.BaseDeTimes("al-nassr");
//		base.stream().forEach(System.out::println);
		
//		System.out.println("Adicionar Imagem");
//		seriviceIm.cadastrarImagem(new Imagem("","https://www.futebolreligiao.com.br/image/cache/catalog/al-nassr/camisa-i-al-nassr-2023-2024-home-1-900x900.webp?1688595021"), 41);
//		System.out.println("Excluir Imagem");
//		seriviceIm.excluirImagem(21);
	}

}
