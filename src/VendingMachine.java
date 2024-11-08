import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

public class VendingMachine {
    public Scanner scanner = new Scanner(System.in);
    private final Map<String, Produto> stock;
    private final List<Produto> historicoVendas;
    private double totalVendas;
    int count_chocolate = 0, count_refrigerantes = 0, count_sandes = 0;

    public VendingMachine() {
        stock = new HashMap<>();
        historicoVendas = new ArrayList<>();
        totalVendas = 0.0;
        inicializarStockCompleto();  // Carrega do arquivo ou inicializa com o stock completo
    }

    public void inicializarStockCompleto() {
        // Chocolates - capacidade máxima de 20
        for (int i = 1; i <= 20; i++) {
            String referencia = "CHOC" + i;
            Produto chocolate = switch (i % 3) {
                case 0 -> new Chocolate("Chocolate Preto Lindt", 2.50, referencia, "2025-12-31", "negro", "Lindt");
                case 1 -> new Chocolate("Chocolate ao Leite Nestlé", 1.80, referencia, "2025-06-30", "leite", "Nestlé");
                default -> new Chocolate("Chocolate Branco Milka", 2.00, referencia, "2025-08-15", "branco", "Milka");
            };
            stock.put(chocolate.getReferencia(), chocolate);
        }

        // Refrigerantes - capacidade máxima de 15
        for (int i = 1; i <= 15; i++) {
            String referencia = "REFR" + i;
            Produto refrigerante = switch (i % 3) {
                case 0 -> new Refrigerante("Pepsi", 1.50, referencia, "2024-11-15", false, "Pepsi");
                case 1 -> new Refrigerante("Sumol Laranja sem açúcar", 1.70, referencia, "2024-12-10", true, "Sumol");
                default -> new Refrigerante("Lipton Chá Verde", 1.80, referencia, "2024-10-05", false, "Lipton");
            };
            stock.put(refrigerante.getReferencia(), refrigerante);
        }

        // Sandes - capacidade máxima de 10
        for (int i = 1; i <= 10; i++) {
            String referencia = "SANDE" + i;
            Produto sande = switch (i % 3) {
                case 0 -> new Sandes("Sande Mista", 3.00, referencia, "2024-09-10", "mista", "Panificadora Central");
                case 1 ->
                        new Sandes("Sande de Queijo", 2.80, referencia, "2024-08-22", "queijo", "Panificadora Central");
                default ->
                        new Sandes("Sande de Fiambre", 3.20, referencia, "2024-09-15", "fiambre", "Panificadora Central");
            };
            stock.put(sande.getReferencia(), sande);
        }
    }

    // Metodo para listar todos os produtos no stock
    public void listarProdutos() {
        // Cabeçalho da máquina de vendas
        System.out.print("\n".repeat(75) + "-".repeat(94)
                + " MAQUINA DE VENDAS " + "-".repeat(93) + "\n||"
                + " ".repeat(202) + "||\n");

        // Definindo as dimensões da tabela
        int linhas = 12;
        int colunas = 4;
        int totalCelulas = linhas * colunas;
        int count = 0;

        // Iterando sobre os produtos e imprimindo-os na tabela
        for (Produto produto : stock.values()) {
            count++;
            if (count % colunas == 0) {
                System.out.printf("||     %-10s %-30s||%n", produto.getReferencia(), produto.getNome());
            } else {
                System.out.printf("||     %-10s %-30s    ", produto.getReferencia(), produto.getNome());
            }
        }

        // Preenchendo as células restantes com espaços vazios, para manter o layout da tabela
        while (count < totalCelulas) {
            count++;
            if (count % colunas == 0) {
                System.out.printf("||     %-10s %-30s||%n", "", "");
            } else {
                System.out.printf("||     %-10s %-30s    ", "", "");
            }
        }

        // Rodapé da máquina de vendas
        System.out.print("||" + " ".repeat(202) + "||\n");
        System.out.println("-".repeat(206) + "\n");
    }

    // Metodo para listar produtos por categoria
    public void listarProdutosPorCategoria(String categoria) {
        // Cabeçalho da máquina de vendas para a categoria
        if (categoria.equals("Chocolate"))
            System.out.print("\n".repeat(75) + "-".repeat(61)
                + " MAQUINA DE VENDAS - " + categoria.toUpperCase() + " " + "-".repeat(62) + "\n||"
                + " ".repeat(150) + "||\n");
        else if (categoria.equals("Refrigerante"))
            System.out.print("\n".repeat(75) + "-".repeat(60)
                    + " MAQUINA DE VENDAS - " + categoria.toUpperCase() + " " + "-".repeat(60) + "\n||"
                    + " ".repeat(150) + "||\n");
        else
            System.out.print("\n".repeat(75) + "-".repeat(63)
                    + " MAQUINA DE VENDAS - " + categoria.toUpperCase() + " " + "-".repeat(63) + "\n||"
                    + " ".repeat(150) + "||\n");

        // Definindo as dimensões da tabela
        int linhas = 7;
        int colunas = 3;
        int totalCelulas = linhas * colunas;
        int count = 0;

        // Iterando sobre os produtos e imprimindo-os na tabela se forem da categoria especificada
        for (Produto produto : stock.values()) {
            if (produto.getClass().getSimpleName().equalsIgnoreCase(categoria)) {
                count++;
                if (count % colunas == 0) {
                    System.out.printf("||     %-10s %-30s||%n", produto.getReferencia(), produto.getNome());
                } else {
                    System.out.printf("||     %-10s %-30s    ", produto.getReferencia(), produto.getNome());
                }
            }
        }

        // Preenchendo as células restantes com espaços vazios, para manter o layout da tabela
        while (count < totalCelulas) {
            count++;
            if (count % colunas == 0) {
                System.out.printf("||     %-10s %-30s||%n", "", "");
            } else {
                System.out.printf("||     %-10s %-30s    ", "", "");
            }
        }

        // Rodapé da máquina de vendas
        System.out.print("||" + " ".repeat(150) + "||\n");
        System.out.println("-".repeat(154) + "\n");
    }

    public void adicionarProduto(Produto produto) {
        stock.put(produto.getReferencia(), produto);
    }

    public void venderProduto(double valorInserido) {
        System.out.print("Insira a referência do produto: ");
        String referencia = scanner.nextLine();
        Produto produto = stock.get(referencia);
        if (produto == null) {
            System.out.println("Produto não encontrado!");
            return;
        }

        if (valorInserido < produto.getPreco()) {
            System.out.println("Valor insuficiente. Precisa de mais " + (produto.getPreco() - valorInserido));
            return;
        }

        stock.remove(referencia);
        historicoVendas.add(produto);
        totalVendas += produto.getPreco();

        double troco = valorInserido - produto.getPreco();
        System.out.println("\n".repeat(75) + "--COMPRA CONCLUIDA, RETIRE SUA FATURA--");
        System.out.println("||" + " ".repeat(35) + "||");
        System.out.printf("||     %-30s||%n", produto.getNome());
        System.out.printf("||     Troco: %-23s||%n", troco + "€");
        System.out.println("||" + " ".repeat(35) + "||");
        System.out.println("-".repeat(39) + "\n".repeat(5));
        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    public void removerProduto(String referencia) {
        Produto removido = stock.remove(referencia);
        if (removido == null) {
            System.out.println("Produto não encontrado no stock.");
        } else {
            try {
                System.out.println("\n".repeat(75) + "Produto " + removido.getNome() + " removido com sucesso.\n\n\n\n");
                Thread.sleep(5000); // 1000 milissegundos equivalem a 1 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void visualizarHistorico() {
        System.out.print("\n".repeat(75));
        for (Produto p : historicoVendas) {
            System.out.println("Produto: " + p.getNome() + " - Preço pago: " + p.getPreco());
        }
        System.out.print("\nPressione ENTER para continuar" + "\n");
        scanner.nextLine();
    }

    public double getTotalVendas() {
        return totalVendas;
    }

    public void salvarStock() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("stock.dat"))) {
            out.writeObject(stock);
            out.writeObject(historicoVendas);
            out.writeDouble(totalVendas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isDouble(String str) {
        try {
            str = str.replace(",", ".");
            Double.parseDouble(str);
            return true; // Se não houver exceção, é um double válido
        } catch (NumberFormatException e) {
            return false; // Se lançar exceção, não é um double
        }
    }

    public void limparTelaComTexto(String textodoerro, Boolean isErro) {
        try {
            if (isErro)
                System.out.println("\n".repeat(75) + textodoerro + "\n\nTente Novamente...");
            else
                System.out.println("\n".repeat(75) + textodoerro + "\n\n\n\n");
            Thread.sleep(5000); // 1000 milissegundos equivalem a 1 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}