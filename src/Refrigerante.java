import java.io.Serializable;

public class Refrigerante extends Produto {
    private static final long serialVersionUID = 1L;
    private boolean semAcucar;
    private String marca;

    public Refrigerante(String nome, double preco, String referencia, String prazoValidade, boolean semAcucar, String marca) {
        super(nome, preco, referencia, prazoValidade);
        this.semAcucar = semAcucar;
        this.marca = marca;
    }

    @Override
    public String getDetalhes() {
        return "Refrigerante " + (semAcucar ? "sem açúcar" : "normal") + " - Marca: " + marca;
    }
}
