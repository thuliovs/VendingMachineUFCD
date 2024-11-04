public class Chocolate extends Produto {
    private final String tipoCacau;
    private final String marca;

    public Chocolate(String nome, double preco, String referencia, String prazoValidade, String tipoCacau, String marca) {
        super(nome, preco, referencia, prazoValidade);
        this.tipoCacau = tipoCacau;
        this.marca = marca;
    }

    @Override
    public String getDetalhes() {
        return "Chocolate " + tipoCacau + " - Marca : " + marca;
    }
}
