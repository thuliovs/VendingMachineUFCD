public abstract class Produto {
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
