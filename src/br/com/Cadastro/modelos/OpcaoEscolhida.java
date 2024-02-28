package br.com.Cadastro.modelos;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OpcaoEscolhida {
    Scanner input = new Scanner(System.in);
    private List<Evento> eventos = new ArrayList<>();
    private List<Pessoa> usuarios = new ArrayList<>();
    private Pessoa usuarioLogado;

    public void salvarEventos(String arquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(eventos);
            oos.writeObject(usuarios);
            System.out.println("Eventos e usuários salvos com sucesso no arquivo " + arquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar eventos e usuários no arquivo " + arquivo + ": " + e.getMessage());
        }
    }

    public void carregarEventos(String arquivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            eventos = (List<Evento>) ois.readObject();
            usuarios = (List<Pessoa>) ois.readObject();
            System.out.println("Eventos e usuários carregados com sucesso do arquivo " + arquivo);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar eventos e usuários do arquivo " + arquivo + ": " + e.getMessage());
        }
    }


    public void cadastrarUsuario() {
        System.out.println("Seja bem vindo ao ~~Cadastro de Usuários~~");
        System.out.println("\nDigite o seu nome de usuário: ");
        String nome = input.nextLine();

        System.out.println("Digite a sua cidade: ");
        String cidade = input.nextLine();

        System.out.println("Digite o seu endereço de email: ");
        String email = input.nextLine();

        System.out.println("Digite a sua senha: ");
        String senha = input.nextLine();

        Pessoa usuario = new Pessoa(nome, cidade, email, senha);

        usuarios.add(usuario);

        System.out.println("Usuário cadastrado com sucesso\n");
    }

    public void cadastrarEvento() {

        if (usuarioLogado == null) {
            System.out.println("Não há usuários logados");
            return;
        }
        Scanner input = new Scanner(System.in);

        System.out.println("Seja bem vindo ao ****Cadastro de Eventos****");
        System.out.println("\nDigite o nome do evento: ");
        String nome = input.nextLine();

        System.out.println("Digite o endereço do evento: ");
        String endereco = input.nextLine();

        String categoria;

        while (true) {
            System.out.println("Digite a categoria do evento (festa, evento esportivo, show): ");
            categoria = input.nextLine();

            if (categoria.equalsIgnoreCase("festa") || categoria.equalsIgnoreCase("evento esportivo") || categoria.equalsIgnoreCase("show")) {
                break;
            } else {
                System.out.println("Opção inválida, as categorias estão limitadas a festa, evento esportivo e show");
            }
        }

        System.out.println("Digite o dia e horário do evento (a estrutura é yyyy-MM-dd HH:mm:ss): ");
        String horarioStr = input.nextLine();

        LocalDateTime horario;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            horario = LocalDateTime.parse(horarioStr, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("A estrutura está incorreta! A estrutura é yyyy-MM-dd HH:mm:ss");
            return;
        }

        System.out.println("Digite a descrição do evento: ");
        String descricao = input.nextLine();

        Evento evento = new Evento(nome, endereco, categoria, horario, descricao);
        eventos.add(evento);
        System.out.println("Evento cadastrado com sucesso!\n");
    }

    public void consultarUsuarios() {
        System.out.println("****Usuários Cadastrados****\n");

        System.out.println("Informe o nome de usuário que deseja buscar: ");
        String nomeUsuario = input.nextLine();

        boolean usuarioEncontrado = false;
        for (Pessoa usuario : usuarios) {
            if (usuario.getNome().equals(nomeUsuario)) {
                System.out.println("\nNome: " +  usuario.getNome());
                System.out.println("Cidade: " +  usuario.getCidade());
                System.out.println("Email: " +  usuario.getEmail());
                System.out.println("Logado: " + usuario.getStatus());
                usuarioEncontrado = true;
                break;
            }
        }

        if (!usuarioEncontrado) {
            System.out.println("Usuário não encontrado!\n");
        }
    }

    public void logarUsuario() {
        System.out.println("Informe o nome do usuário que deseja logar: ");
        String nomeUsuario = input.nextLine();

       System.out.println("Informe a senha do usuário: ");
        String senhaUsuario = input.nextLine();

        for (Pessoa usuario : usuarios) {
            if (usuario.getNome().equals(nomeUsuario) && usuario.getSenha().equals(senhaUsuario)) {
                usuario.setStatus(true);
                usuarioLogado = usuario;
                System.out.println("Usuário Logado com sucesso!\n");
                return;
            }
        }
        System.out.println("Usuário ou senha inválidos!\n");
    }

    public void deslogarUsuario() {
        if (usuarioLogado == null) {
            System.out.println("Nenhum usuário está logado.");
        }
        System.out.println("Informe o nome do usuário que deseja logar: ");
        String nomeUsuario = input.nextLine();

        System.out.println("Informe a senha do usuário: ");
        String senhaUsuario = input.nextLine();

        for (Pessoa usuario : usuarios) {
            if (usuario.getNome().equals(nomeUsuario) && usuario.getSenha().equals(senhaUsuario)) {
                usuario.setStatus(false);
                usuarioLogado = null;
                System.out.println("Usuário deslogado com sucesso!\n");
                return;
            }
        }
    }

    public void consultarEventos() {
        eventos.sort((event1, event2) -> event1.getHorario().compareTo(event2.getHorario())); //Garante que a lista de eventos será ordenada em ordem crescente de horário
        System.out.println("****Eventos Cadastrados****");

        for (Evento evento : eventos) {
            LocalDateTime now = LocalDateTime.now();
            String participacao = "Não participa";

            if (usuarioLogado != null && evento.getParticipantes().contains(usuarioLogado)) {
                participacao = "Participa";
            }

            if (evento.getHorario().isBefore(now)) {
                System.out.println("\nNome: " + evento.getNome());
                System.out.println("Endereço: " + evento.getEndereco());
                System.out.println("Categoria: " + evento.getCategoria());
                System.out.println("Horário: " + evento.getHorario());
                System.out.println("Descrição: " + evento.getDescricao());
                System.out.println("Estado: O evento já ocorreu!");
                System.out.println("Participação: " + participacao + "\n");
            } else {
                System.out.println("\nNome: " + evento.getNome());
                System.out.println("Endereço: " + evento.getEndereco());
                System.out.println("Categoria: " + evento.getCategoria());
                System.out.println("Horário: " + evento.getHorario());
                System.out.println("Descrição: " + evento.getDescricao());
                System.out.println("Estado: O evento está agendado!");
                System.out.println("Participação: " + participacao + "\n");
            }
        }
    }


    public void confirmarOuCancelarParticipacao() {
        if (usuarioLogado == null) {
            System.out.println("Não há usuários logados no momento, faça o login e tente novamente");
            return;
        }

        Scanner input = new Scanner(System.in);

        System.out.println("****Confirmação ou cancelamento****");
        System.out.println("Digite o nome do evento no qual deseja confirmar a participação: ");
        String nomeDoEvento = input.nextLine();

        Evento eventoEscolhido = null;

        for (Evento evento : eventos) {
            if (evento.getNome().equalsIgnoreCase(nomeDoEvento)) {
                eventoEscolhido = evento;
                break;
            }
        }

        if (eventoEscolhido == null) {
            System.out.println("Evento não encontrado!");
            return;
        }

        if (eventoEscolhido.getParticipantes().contains(usuarioLogado)) {
            System.out.println("Ops, você já está participando do evento, deseja cancelar sua participação? (s/n)");
            String resposta = input.nextLine();

            if (resposta.equalsIgnoreCase("s")) {
                eventoEscolhido.removerParticipante(usuarioLogado);
                System.out.println("Sua participação no evento " + eventoEscolhido.getNome() + " foi cancelada!");
            } else {
                System.out.println("Operação cancelada, sua participação no evento contnua confirmada!");
            }
        } else {
            System.out.println("Deseja confirmar a sua participação nesse evento? (s/n)");
            String resposta = input.nextLine();

            if (resposta.equalsIgnoreCase("s")) {
                eventoEscolhido.adicionarUmParticipante(usuarioLogado);
                System.out.println("Sua participaçã foi confirmada com sucesso!");
            } else {
                System.out.println("Operação cancelada, você não esta participando desse evento!");
            }
        }
    }

    public void consultarEventosParticipados() {
        if (usuarioLogado == null) {
            System.out.println("Não há usuários logados no momento, faça o login e tente novamente!");
            return;
        }

        System.out.println("Eventos participados por " + usuarioLogado.getNome());

        boolean encontrouEventos = false;
        for (Evento evento : eventos) {
            if (evento.getParticipantes().contains(usuarioLogado)) {
                encontrouEventos = true;
                System.out.println("\nNome: " + evento.getNome());
                System.out.println("Endereço: " + evento.getEndereco());
                System.out.println("Categoria: " + evento.getCategoria());
                System.out.println("Horário: " + evento.getHorario());
                System.out.println("Descrição: " + evento.getDescricao());
            }
        }

        if (!encontrouEventos) {
            System.out.println("O usuário não está participando de nenhum evento!");
        }
    }
}
