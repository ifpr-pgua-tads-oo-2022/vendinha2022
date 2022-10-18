package ifpr.pgua.eic.vendinha2022.model.entities;

public class Produto {
    

    private int id;
    private String nome;
    private String descricao;
    private double valor;
    private double quantidadeEstoque;

    

    public Produto(int id, String nome, String descricao, double valor, double quantidadeEstoque) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Produto(String nome, String descricao, double valor, double quantidadeEstoque) {
        this(-1,nome,descricao,valor,quantidadeEstoque);
    }
    

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public Double getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    public void setQuantidadeEstoque(double quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String toString(){
        return this.nome;
    }
    
}
