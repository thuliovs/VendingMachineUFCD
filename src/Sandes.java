public class Sandes extends Produto {
    private String tipoSande;
    private String produtor;

    public Sandes(String nome, double preco, String referencia, String prazoValidade, String tipoSande, String produtor) {
        super(nome, preco, referencia, prazoValidade);
        this.tipoSande = tipoSande;
        this.produtor = produtor;
    }

    @Override
    public String getDetalhes() {
        return "Sande " + tipoSande + " - Produtor: " + produtor;
    }
}
