package Models;

public class Relatorio {
	private int id;
	private double totalGasto;
	private double lucroTotal;
	private int quantidadePizzas;
	private double espacoEstoqueAtualmente;
	private int codEstoque;
	private String dataEmissao;

	public Relatorio(int id, double totalGasto, double lucroTotal, int quantidadePizzas, double espacoEstoqueAtualmente,
			int codEstoque, String dataEmissao) {
		this.id = id;
		this.totalGasto = totalGasto;
		this.lucroTotal = lucroTotal;
		this.quantidadePizzas = quantidadePizzas;
		this.espacoEstoqueAtualmente = espacoEstoqueAtualmente;
		this.codEstoque = codEstoque;
		this.dataEmissao = dataEmissao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTotalGasto() {
		return totalGasto;
	}

	public void setTotalGasto(double totalGasto) {
		this.totalGasto = totalGasto;
	}

	public double getLucroTotal() {
		return lucroTotal;
	}

	public void setLucroTotal(double lucroTotal) {
		this.lucroTotal = lucroTotal;
	}

	public int getQuantidadePizzas() {
		return quantidadePizzas;
	}

	public void setQuantidadePizzas(int quantidadePizzas) {
		this.quantidadePizzas = quantidadePizzas;
	}

	public double getEspacoEstoqueAtualmente() {
		return espacoEstoqueAtualmente;
	}

	public void setEspacoEstoqueAtualmente(double espacoEstoqueAtualmente) {
		this.espacoEstoqueAtualmente = espacoEstoqueAtualmente;
	}

	public int getCodEstoque() {
		return codEstoque;
	}

	public void setCodEstoque(int codEstoque) {
		this.codEstoque = codEstoque;
	}

	public String getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

}
