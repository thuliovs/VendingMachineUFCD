import java.io.Serializable;

public abstract class Produto implements Serializable {
    private static final long serialVersionUID = 1L;  // Adiciona um serialVersionUID para controle de versão
    private String nome;
    private double preco;
    private String referencia;
    private String prazoValidade;

    public Produto(String nome, double preco, String referencia, String prazoValidade) {
        this.nome = nome;
        this.preco = preco;
        this.referencia = referencia;
        this.prazoValidade = prazoValidade;
    }

    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public String getReferencia() { return referencia; }
    public String getPrazoValidade() { return prazoValidade; }
    public abstract String getDetalhes();
}
