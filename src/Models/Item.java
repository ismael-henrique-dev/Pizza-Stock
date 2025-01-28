package Models;

public class Item {
	private int itemId;
	private String nome;
	private int quantidadeOcupada, quantidadeMaxima;
	private double peso, preco;

	public Item(int itemId, String nome, double peso, double preco, int quantidadeMaxima, int quantidadeOcupada) {
		this.itemId = itemId;
		this.nome = nome;
		this.peso = peso;
		this.preco = preco;
		this.quantidadeOcupada = quantidadeOcupada;
		this.quantidadeMaxima = quantidadeMaxima;
	
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
