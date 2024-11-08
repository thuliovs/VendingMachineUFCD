import java.util.Scanner;

public class VendingMachineApp {
    public static void main(String[] args) {

        // Declarações iniciais
        VendingMachine vendingMachine = new VendingMachine();
        Scanner scanner = new Scanner(System.in);
        vendingMachine.inicializarStockCompleto();

        while (true) {
            // Cabeçalho inicial + Pegar o dinheiro
            System.out.print(("-_".repeat(112) + "\n").repeat(75) +
                    "Olá, tudo bem?\nBem-vindo a maquina de vendas" +
                    " Thúlios & Alexandres\n\nInsira suas moedas: ");
            String textoInicial = scanner.nextLine();

            // Painel de opções de colaborador
            if (textoInicial.equals("javanaopresta")) {
                while (true) {
                    System.out.print("\n".repeat(75));
                    System.out.print("Bem-vindo Colaborador,\no que pretende hoje? :)\n" +
                            "\n1. Adicionar Produto\n2. Remover Produto" +
                            "\n3. Consultar Total Vendas\n4. Ver Histórico de Vendas\n" +
                            "5. Sair\nEscolha uma opção: ");
                    String opcao = scanner.nextLine();
                    switch (opcao) {
                        case "1":
                            vendingMachine.adicionarProduto();
                            vendingMachine.limparTelaComTexto("Produto adicionado com sucesso!!", false);
                            break;
                        case "2":
                            vendingMachine.removerProduto();
                            break;
                        case "3":
                            System.out.println("\n".repeat(75) + "Total das vendas: " +
                                    vendingMachine.getTotalVendas() + "\n\nPressione ENTER para continuar...\n\n\n");
                            scanner.nextLine();
                            break;
                        case "4":
                            vendingMachine.visualizarHistorico();
                            break;
                        case "5":
                            // Sair do modo colaborador, voltando ao menu principal
                            System.out.println("\n".repeat(75) + "Saindo do modo colaborador...\n");
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            continue;  // Retorna ao loop principal
                        default:
                            vendingMachine.limparTelaComTexto("Opção Inválida! Tente Novamente...", true);
                    }
                    break; // Sai do loop colaborador para o menu principal
                }
                continue; // Continua no loop principal após sair do colaborador
            } else if (!vendingMachine.isDouble(textoInicial)) {
                // Se o valor inserido não for um número válido, exibe mensagem de erro e pede novamente
                vendingMachine.limparTelaComTexto("Valor inválido! Tente Novamente...", true);
                continue;
            }

            // Se o valor for um double, prossegue
            textoInicial = textoInicial.replace(",", ".");
            double valorInserido = Double.parseDouble(textoInicial);

            // Cabeçalho Maquina de Vendas para clientes
            vendingMachine.listarProdutos();

            // Opções para Cliente
            System.out.print("Escolha o tipo de produto:\n1. Chocolate\n2. Refrigerante\n3. Sande\n4. Sair\nEscolha uma opção: ");
            String tipoProduto = scanner.nextLine();
            switch (tipoProduto) {
                case "1":
                    vendingMachine.listarProdutosPorCategoria("Chocolate");
                    vendingMachine.venderProduto(valorInserido);
                    break;
                case "2":
                    vendingMachine.listarProdutosPorCategoria("Refrigerante");
                    vendingMachine.venderProduto(valorInserido);
                    break;
                case "3":
                    vendingMachine.listarProdutosPorCategoria("Sandes");
                    vendingMachine.venderProduto(valorInserido);
                    break;
                case "4":
                    System.out.println("\n".repeat(75) + "Devolvendo os " + valorInserido + "€ inseridos...");
                    try {
                        for (int i = 1; i <= 8; i++) {
                            Thread.sleep(1000); // 1000 milissegundos equivalem a 1 segundo
                            System.out.println("Devolvendo os " + valorInserido + "€ inseridos...");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    vendingMachine.limparTelaComTexto("Tipo inválido! Tente Novamente...", true);
                    break;
            }
        }
    }
}
