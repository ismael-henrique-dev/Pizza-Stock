package Models;

public class Item {
	private int itemId;
	private String nome;
	private int quantidadeOcupada, quantidadeMaxima;
	private double peso, preco;

	public Item(int itemId, String nome, int quantidadeOcupada, int quantidadeMaxima, double peso, double preco) {
		this.itemId = itemId;
		this.nome = nome;
		this.quantidadeOcupada = quantidadeOcupada;
		this.quantidadeMaxima = quantidadeMaxima;
		this.peso = peso;
		this.preco = preco;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQuantidadeOcupada() {
		return quantidadeOcupada;
	}

	public void setQuantidadeOcupada(int quantidadeOcupada) {
		this.quantidadeOcupada = quantidadeOcupada;
	}

	public int getQuantidadeMaxima() {
		return quantidadeMaxima;
	}

	public void setQuantidadeMaxima(int quantidadeMaxima) {
		this.quantidadeMaxima = quantidadeMaxima;
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

	

}
