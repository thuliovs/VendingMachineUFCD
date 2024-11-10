import java.util.Scanner;

public class VendingMachineApp {
    public static void main(String[] args) {

        // Declarações iniciais

        VendingMachine vendingMachine = new VendingMachine();
        String senhacolaborador = "javanaopresta";
        Scanner scanner = new Scanner(System.in);
        boolean colaborador_usou = false;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            vendingMachine.limparTelaComTexto("Salvando dados antes de encerrar...", false);
            vendingMachine.salvarDados();
        }));

        while (true) {
            // Cabeçalho inicial + Pegar o dinheiro
            System.out.print(("-_".repeat(112) + "\n").repeat(75) +
                    "Olá, tudo bem?\nBem-vindo a maquina de vendas" +
                    " Thúlios & Alexandres\n\nInsira suas moedas: ");
            String textoInicial = scanner.nextLine();

            // Painel de opções de colaborador
            if (textoInicial.equals(senhacolaborador)) {
                colaborador_usou = false;
                while (colaborador_usou == false) {
                    System.out.print("\n".repeat(75));
                    System.out.print("Bem-vindo Colaborador,\no que pretende hoje? :)\n" +
                            "\n1. Adicionar Produto\n2. Remover Produto" +
                            "\n3. Consultar Total Vendas\n4. Ver Histórico de Vendas\n" +
                            "5. Remover Historico de Vendas\n6. Sair\nEscolha uma opção: ");
                    String opcao = scanner.nextLine();
                    switch (opcao) {
                        case "1":
                            System.out.print("\n".repeat(75));
                            System.out.print("Digite o nome do novo produto: ");
                            String nome = scanner.nextLine();
                            String preco;
                            while (true) {
                                System.out.print("Digite o preço (€): ");
                                preco = scanner.nextLine();
                                if (!(vendingMachine.isDouble(preco))) {
                                    vendingMachine.limparTelaComTexto("Valor Inválido", true);
                                    System.out.print("\n".repeat(75));
                                    continue;
                                }
                                break;
                            }
                            preco = preco.replace(",", ".");
                            double precodouble = Double.parseDouble(preco);
                            System.out.print("Digite a referência: ");
                            String referencia = scanner.nextLine().toUpperCase();
                            String validade;
                            while (true) {
                                System.out.print("Digite o prazo de validade (dd/mm/yy): ");
                                validade = scanner.nextLine();
                                if (vendingMachine.validarData(validade))
                                    break;
                                else {
                                    vendingMachine.limparTelaComTexto("Data Inválida", true);
                                    System.out.print("\n".repeat(75));
                                    continue;
                                }
                            }
                            Produto novoProduto = null;
                            while (true) {
                                System.out.print("\nTipo:\n1-Chocolate\n2-Refrigerante\n3-Sandes\nDigite o valor: ");
                                String categoria = scanner.nextLine();

                                switch (categoria) {
                                    case "1":
                                        String tipoCacau;
                                        while (true) {
                                            System.out.print("\nTipo de cacau:\n1-Negro\n2-Branco\n3-Ao Leite\nDigite o valor: ");
                                            tipoCacau = scanner.nextLine();
                                            switch (tipoCacau) {
                                                case "1":
                                                    tipoCacau = "Negro";
                                                    break;
                                                case "2":
                                                    tipoCacau = "Branco";
                                                    break;
                                                case "3":
                                                    tipoCacau = "Ao leite";
                                                    break;
                                                default:
                                                    vendingMachine.limparTelaComTexto("Tipo de cacau inválido!", true);
                                                    System.out.print("\n".repeat(75));
                                                    continue;
                                            }
                                            break;
                                        }
                                        System.out.print("\nMarca: ");
                                        String marcaChocolate = scanner.nextLine();
                                        novoProduto = new Chocolate(nome, precodouble, referencia, validade, tipoCacau, marcaChocolate);
                                        break;
                                    case "2":
                                        boolean semAcucarBoo;
                                        while (true) {
                                            System.out.print("\nRefrigerante sem açúcar? (S/N): ");
                                            String semAcucar = scanner.nextLine();
                                            switch (semAcucar) {
                                                case "S":
                                                    semAcucarBoo = true;
                                                    break;
                                                case "N":
                                                    semAcucarBoo = false;
                                                    break;
                                                default:
                                                    vendingMachine.limparTelaComTexto("Valor Inválido", true);
                                                    System.out.print("\n".repeat(75));
                                                    continue;
                                            }
                                            break;
                                        }
                                        System.out.print("\nMarca: ");
                                        String marcaRefrigerante = scanner.nextLine();
                                        novoProduto = new Refrigerante(nome, precodouble, referencia, validade, semAcucarBoo, marcaRefrigerante);
                                        break;
                                    case "3":
                                        String tipoSande;
                                        while (true) {
                                            System.out.print("\nTipo de sandes:\n1-Mista\n2-Fiambre\n3-Queijo\nDigite o valor: ");
                                            tipoSande = scanner.nextLine();
                                            switch (tipoSande) {
                                                case "1":
                                                    tipoSande = "Mista";
                                                    break;
                                                case "2":
                                                    tipoSande = "Fiambre";
                                                    break;
                                                case "3":
                                                    tipoSande = "Queijo";
                                                    break;
                                                default:
                                                    vendingMachine.limparTelaComTexto("Valor Inválido", true);
                                                    System.out.print("\n".repeat(75));
                                                    continue;
                                            }
                                            break;
                                        }
                                        System.out.print("\nProdutor: ");
                                        String produtor = scanner.nextLine();
                                        novoProduto = new Sandes(nome, precodouble, referencia, validade, tipoSande, produtor);
                                        break;
                                    default:
                                        vendingMachine.limparTelaComTexto("Tipo de produto inválido!", true);
                                        System.out.print("\n".repeat(75));
                                        continue;
                                }
                                break;
                            }
                            vendingMachine.adicionarProduto(novoProduto);
                            continue;
                        case "2":
                            while (true) {
                                vendingMachine.listarProdutos();
                                System.out.print("Digite a referência do produto a remover: ");
                                String refRemover = scanner.nextLine();
                                if (vendingMachine.removerProduto(refRemover))
                                    continue;
                                else
                                    break;
                            }
                            continue;
                        case "3":
                            System.out.println("\n".repeat(75) + "Total das vendas: " +
                                    vendingMachine.getTotalVendas() + "\n\nPressione ENTER para continuar...\n\n\n");
                            scanner.nextLine();
                            continue;
                        case "4":
                            vendingMachine.visualizarHistorico();
                            continue;
                        case "5":
                            while(true) {
                                if (vendingMachine.limparHistoricoVendas(senhacolaborador))
                                    break;
                            }
                            continue;
                        case "6":
                            try {
                                System.out.println("\n".repeat(75) + "Salvando e Saindo...\n\n\n");
                                Thread.sleep(5000); // 1000 milissegundos equivalem a 1 segundos
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            try {
                                System.out.println("\n".repeat(75) + "Opção Inválida!\n\nTente Novamente...");
                                Thread.sleep(5000); // 1000 milissegundos equivalem a 1 segundos
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            continue;
                    }
                    colaborador_usou = true; // declarando que o colaborador acabou de usar a maquina
                }
            }
            else if (vendingMachine.isDouble(textoInicial)) { // se for um double (dinheiro) ele seta como se o coloborador n�o estivesse acabado de usar
                colaborador_usou = false;
            }
            else { // caso n�o seja a senha nem um double
                try {
                    System.out.println("\n".repeat(75) + "Valor inválido!\n\nTente Novamente...");
                    Thread.sleep(5000); // 1000 milissegundos equivalem a 1 segundos
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (colaborador_usou){ // saindo do loop do colaborador caimos logo aqui, por isso queremos voltar ao inicio do loop geral pedindo o dinheiro
                continue;
            }

            // Texto inicial para Double
            textoInicial = textoInicial.replace(",", ".");
            double valorInserido = Double.parseDouble(textoInicial);

            while(true) {
                // Cabeçalho Maquina de Vendas para clientes

                vendingMachine.listarProdutos();


                // Opções

                System.out.print("Escolha o tipo de produto:\n1. Chocolate\n2. Refrigerante\n3. Sande\n4. Sair\nEscolha uma opção: ");
                String tipoProduto = scanner.nextLine();
                boolean isVendidoOUErro = false;
                switch (tipoProduto) {
                    case "1":
                        while (isVendidoOUErro == false) {
                            vendingMachine.listarProdutosPorCategoria("Chocolate");
                            switch (vendingMachine.venderProduto(valorInserido)) {
                                case 1:
                                    break;
                                case 2:
                                    isVendidoOUErro = true; // para voltar ao ecr� principal
                                    break;
                                case 3:
                                    isVendidoOUErro = true;
                                    break;
                            }
                        }
                        break;
                    case "2":
                        while (isVendidoOUErro == false) {
                            vendingMachine.listarProdutosPorCategoria("Refrigerante");
                            switch (vendingMachine.venderProduto(valorInserido)) {
                                case 1:
                                    break;
                                case 2:
                                    isVendidoOUErro = true; // para voltar ao ecr� principal
                                    break;
                                case 3:
                                    isVendidoOUErro = true;
                                    break;
                            }
                        }
                        break;
                    case "3":
                        while (isVendidoOUErro == false) {
                            vendingMachine.listarProdutosPorCategoria("Sandes");
                            switch (vendingMachine.venderProduto(valorInserido)) {
                                case 1:
                                    break;
                                case 2:
                                    isVendidoOUErro = true; // para voltar ao ecr� principal
                                    break;
                                case 3:
                                    isVendidoOUErro = true;
                                    break;
                            }
                        }
                        break;
                    case "4":
                        vendingMachine.devolverValor(valorInserido, false);
                        break;
                    default:
                        try {
                            System.out.println("\n".repeat(75) + "Tipo inválido!\n\nTente Novamente...");
                            Thread.sleep(5000); // 1000 milissegundos equivalem a 1 segundos
                            continue;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
                break; // sair da tela do usuario
            }
        }
    }
}