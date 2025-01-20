package Models;

public class Categoria {
    private int id;
    private String nome;
    private double peso;
    private double preco;
    private int disponibilidade;

    public Categoria(int id, String nome, double peso, double preco, int disponibilidade) {
        this.id = id;
        this.nome = nome;
        this.peso = peso;
        this.preco = preco;
        this.disponibilidade = disponibilidade;
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

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(int disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

}
