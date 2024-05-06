package com.deltavivo.findmusic.principal;

import com.deltavivo.findmusic.model.Artista;
import com.deltavivo.findmusic.model.Musica;
import com.deltavivo.findmusic.model.TipoArtista;
import com.deltavivo.findmusic.repository.ArtistaRepository;
import com.deltavivo.findmusic.service.ConsultaChatGPT;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private final ArtistaRepository repository;
    private Scanner leitura = new Scanner(System.in);
    private ConsultaChatGPT consultaChatGPT;

    public Principal(ArtistaRepository repository) {
        this.repository = repository;
    }


    public void exibeMenu() {
        var opcao = -1;

        while (opcao!= 9) {
            var menu = """
                    ==============================================
                    *** Find Music ***
                    ==============================================
                    1- Cadastrar artistas
                    2- Cadastrar músicas
                    3- Listar músicas
                    4- Buscar músicas por artistas
                    5- Pesquisar dados sobre um artista
                                    
                    9 - Sair
                    ==============================================
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicasPorArtista();
                    break;
                case 5:
                    pesquisarDadosDoArtista();
                    break;
                case 9:
                    System.out.println("Encerrando a aplicação!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarMusicas() {
        System.out.printf("Cadastrar musica de qual artista? ");
        var nome = leitura.nextLine();
        Optional<Artista> artista = repository.findByNomeContainingIgnoreCase(nome);

        if(artista.isPresent()){
            System.out.printf("Digite o titulo da musica: ");
            var nomeMusica = leitura.nextLine();
            Musica musica = new Musica(nomeMusica);
            musica.setArtista(artista.get());
            artista.get().getMusica().add(musica);
            repository.save(artista.get());
        } else {
            System.out.printf("Artista nao encontrado.");
        }

    }

    private void cadastrarArtistas() {

        var cadastrarNovo = "S";

        while(cadastrarNovo.equalsIgnoreCase("S")) {
            System.out.printf("Informe o nome do artista: ");
            var nome = leitura.nextLine();
            System.out.printf("Informe o tipo desse artista: (solo, dupla, banda)");
            var tipo = leitura.nextLine();

            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
            Artista artista = new Artista(nome, tipoArtista);

            repository.save(artista);

            System.out.printf("Deseja cadastrar novo artista? (S/N)");
            cadastrarNovo = leitura.nextLine();
        }
    }

    private void listarMusicas() {
        List<Artista> artistas = repository.findAll();
        artistas.forEach(a -> a.getMusica().forEach(System.out::println));
    }

    private void buscarMusicasPorArtista() {
        System.out.printf("Informe o nome do artista: ");
        var nome = leitura.nextLine();
        List<Musica> musicas = repository.buscaMusicaPorArtista(nome);
        musicas.forEach(System.out::println);
    }

    private void pesquisarDadosDoArtista() {
        System.out.println("Pesquisar dados sobre qual artista? ");
        var nome = leitura.nextLine();

        var resposta = ConsultaChatGPT.obterInformacao(nome);
        System.out.println(resposta.trim());
    }
}