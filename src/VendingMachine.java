import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;

public class VendingMachine {
    private final Map<String, Produto> stock;
    private final List<Produto> historicoVendas;
    private double totalVendas;
    public Scanner scanner = new Scanner(System.in);
    private static final String STOCK_FILE = "stock.data";
    private static final String HISTORICO_FILE = "historico.data";
    private static final String TOTAL_VENDAS_FILE = "totalVendas.data";
    int count_chocolate = 0, count_refrigerantes = 0, count_sandes = 0;

    public VendingMachine() {
        stock = new HashMap<>();
        historicoVendas = new ArrayList<>();
        totalVendas = 0.0;
        carregarDados();  // Carrega dados salvos ao iniciar a máquina

        // Contar quantos itens do tipo Chocolate estão no stock
        count_chocolate = (int) stock.values().stream()
                .filter(produto -> produto instanceof Chocolate)
                .count();
        // Contar quantos itens do tipo Refrigerante estão no stock
        count_refrigerantes = (int) stock.values().stream()
                .filter(produto -> produto instanceof Refrigerante)
                .count();
        // Contar quantos itens do tipo Sandes estão no stock
        count_sandes = (int) stock.values().stream()
                .filter(produto -> produto instanceof Sandes)
                .count();
    }
    // Carrega todos os dados do estado anterior (estoque, histórico e total de vendas)

    private void carregarDados() {
        carregarStock();
        carregarHistorico();
        carregarTotalVendas();
    }

    // Metodo para carregar o stock do arquivo
    private void carregarStock() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STOCK_FILE))) {
            Map<String, Produto> loadedStock = (Map<String, Produto>) ois.readObject();
            stock.putAll(loadedStock);
        } catch (IOException | ClassNotFoundException e) {
            limparTelaComTexto("Não foi possível carregar o stock. Inicializando com estoque padrão.", false);
            inicializarStockCompleto(); // Inicializa com estoque padrão se o arquivo não existir
        }
    }

    // Metodo para carregar o histórico de vendas do arquivo
    private void carregarHistorico() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(HISTORICO_FILE))) {
            List<Produto> loadedHistorico = (List<Produto>) ois.readObject();
            historicoVendas.addAll(loadedHistorico);
        } catch (IOException | ClassNotFoundException e) {
            limparTelaComTexto("Não foi possível carregar o histórico de vendas. Inicializando vazio.", false);
        }
    }

    // Metodo para carregar o total de vendas do arquivo
    private void carregarTotalVendas() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(TOTAL_VENDAS_FILE))) {
            totalVendas = dis.readDouble();
        } catch (IOException e) {
            limparTelaComTexto("Não foi possível carregar o total de vendas. Inicializando com zero.", false);
            totalVendas = 0.0;
        }
    }

    // Salva todos os dados ao encerrar (estoque, histórico e total de vendas)
    public void salvarDados() {
        salvarStock();
        salvarHistorico();
        salvarTotalVendas();
    }

    // Metodo para salvar o stock no arquivo
    private void salvarStock() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STOCK_FILE))) {
            oos.writeObject(stock);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o stock: " + e.getMessage());
        }
    }

    // Metodo para salvar o histórico de vendas no arquivo
    private void salvarHistorico() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HISTORICO_FILE))) {
            oos.writeObject(historicoVendas);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o histórico de vendas: " + e.getMessage());
        }
    }

    // Metodo para salvar o total de vendas no arquivo
    private void salvarTotalVendas() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(TOTAL_VENDAS_FILE))) {
            dos.writeDouble(totalVendas);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o total de vendas: " + e.getMessage());
        }
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
            Produto sandes = switch (i % 3) {
                case 0 -> new Sandes("Sandes Mista", 3.00, referencia, "2024-09-10", "mista", "Panificadora Central");
                case 1 ->
                        new Sandes("Sandes de Queijo", 2.80, referencia, "2024-08-22", "queijo", "Panificadora Central");
                default ->
                        new Sandes("Sandes de Fiambre", 3.20, referencia, "2024-09-15", "fiambre", "Panificadora Central");
            };
            stock.put(sandes.getReferencia(), sandes);
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
        if((produto instanceof Chocolate && count_chocolate < 20) ||
                (produto instanceof Refrigerante && count_refrigerantes < 15) ||
                (produto instanceof Sandes && count_sandes < 10)){
            stock.put(produto.getReferencia(), produto);
            if(produto instanceof Chocolate)
                count_chocolate ++;
            else if (produto instanceof  Refrigerante)
                count_refrigerantes ++;
            else
                count_sandes ++;
            limparTelaComTexto("Produto adicionado com sucesso!!", false);
        }
        else {
            limparTelaComTexto("Este tipo de produto ja esta na capacidade maxima!!", true);
        }
    }

    public int venderProduto(double valorInserido) {
        System.out.print("Insira a referência do produto: ");
        String referencia = scanner.nextLine();
        Produto produto = stock.get(referencia);
        if (produto == null) {
            limparTelaComTexto("Produto não encontrado!", true);
            return(1);
        }

        if (valorInserido < produto.getPreco()) {
            try {
                System.out.println("\n".repeat(75) + "Valor insuficiente. Precisa de mais "
                        + (produto.getPreco() - valorInserido) + "€" + "\n\n\nInsira o nessesario na proxima...");
                Thread.sleep(5000); // 1000 milissegundos equivalem a 1 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            devolverValor(valorInserido, false);
            return(2);
        }

        // decrementa a instacia do produto
        if(produto instanceof Chocolate)
            count_chocolate --;
        else if (produto instanceof  Refrigerante)
            count_refrigerantes --;
        else
            count_sandes --;

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
        devolverValor(troco, true);
        return (3);
    }

    public boolean removerProduto(String referencia) {
        Produto produto = stock.get(referencia);
        if (produto == null) {
            try {
                System.out.println("\n".repeat(75) + "Produto não encontrado no stock.\n\nTente Novamente...\n\n");
                Thread.sleep(5000); // 1000 milissegundos equivalem a 1 segundos
                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                // remove o produto
                stock.remove(referencia);

                // decrementa a instancia do produto
                if(produto instanceof Chocolate)
                    count_chocolate --;
                else if (produto instanceof  Refrigerante)
                    count_refrigerantes --;
                else
                    count_sandes --;

                System.out.println("\n".repeat(75) + "Produto " + produto.getNome() + " removido com sucesso.\n\n\n\n");
                Thread.sleep(5000); // 1000 milissegundos equivalem a 1 segundos
                return false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void visualizarHistorico() {
        System.out.print("\n".repeat(75));
        for (Produto p : historicoVendas) {
            System.out.println("Produto: " + p.getNome() + " - Preço pago: " + p.getPreco());
        }
        System.out.print("\nPressione ENTER para continuar" + "\n");
        scanner.nextLine();
    }

    public boolean limparHistoricoVendas(String senhacolaborador) {
        System.out.print("\n".repeat(75) + "Insira a senha do colaborador: ");
        String senhacolocada = scanner.nextLine();
        if (senhacolocada.equals(senhacolaborador)) {
            historicoVendas.clear(); // Limpa todos os itens da lista
            limparTelaComTexto("Histórico de vendas limpo com sucesso.", false);
            return true;
        }
        else if (senhacolocada.equals("sair")) {
            return true;
        }
        else {
            limparTelaComTexto("Senha Invalida!!", true);
            return false;
        }
    }

    public double getTotalVendas() {
        return totalVendas;
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

    public boolean validarData(String data) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yy");
        formato.setLenient(false); // Não permite datas inválidas (ex: 32/13/99)
        try {
            formato.parse(data); // Tenta fazer o parsing da data
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public void devolverValor(double valorInserido, boolean isTroco){
        if(!isTroco) {
            System.out.println("\n".repeat(75) + "Devolvendo os " + valorInserido + "€ inseridos...");
            try {
                for (int i = 1; i <= 8; i++) {
                    Thread.sleep(1000); // 1000 milissegundos equivalem a 1 segundos
                    System.out.println("Devolvendo os " + valorInserido + "€ inseridos...");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("\n".repeat(75) + "Devolvendo os " + valorInserido + "€ de troco...");
            try {
                for (int i = 1; i <= 8; i++) {
                    Thread.sleep(1000); // 1000 milissegundos equivalem a 1 segundos
                    System.out.println("Devolvendo os " + valorInserido + "€ de troco...");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}