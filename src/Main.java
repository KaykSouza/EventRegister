import br.com.Cadastro.modelos.OpcaoEscolhida;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        OpcaoEscolhida opcao = new OpcaoEscolhida();
        opcao.carregarEventos("event.data");

        Scanner input = new Scanner(System.in);
        boolean sair = false;

        while (!sair) {
            System.out.println("Bem Vindo!");
            System.out.println("\n1. Cadastrar novo usuário");
            System.out.println("2. Logar um usuário");
            System.out.println("3. Deslogar um usuário");
            System.out.println("4. Consultar usuários já cadastrados");
            System.out.println("5. Cadastrar novo evento");
            System.out.println("6. Consultar eventos já cadastrados");
            System.out.println("7. Partcipar ou cancelar a participação de um evento");
            System.out.println("8. Vizualizar eventos já participados");
            System.out.println("9. Sair");
            System.out.println("Escolha uma opção: ");
            int opcaoEscrita = input.nextInt();

            switch (opcaoEscrita) {
                case 1:
                    opcao.cadastrarUsuario();
                    break;
                case 2:
                    opcao.logarUsuario();
                    break;
                case 3:
                    opcao.deslogarUsuario();
                    break;
                case 4:
                    opcao.consultarUsuarios();
                    break;
                case 5:
                    opcao.cadastrarEvento();
                    break;
                case 6:
                    opcao.consultarEventos();
                    break;
                case 7:
                    opcao.confirmarOuCancelarParticipacao();
                    break;
                case 8:
                    opcao.consultarEventosParticipados();
                    break;
                case 9:
                    sair = true;
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        }
        opcao.salvarEventos("event.data");

        input.close();
    }
}
