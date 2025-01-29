package Models;

public class Estoque {
	private double quantidadeDisponivel = 0;
	private double pizzasDisponiveis = 0;
	private double lucro = 0;
	private double totalGastoNoMes = 0;

	public Estoque(double quantidadeDisponivel, double pizzasDisponiveis, double lucro, double totalGastoNoMes) {
		this.quantidadeDisponivel = quantidadeDisponivel;
		this.pizzasDisponiveis = pizzasDisponiveis;
		this.lucro = lucro;
		this.totalGastoNoMes = totalGastoNoMes;
	}

	public double getQuantidadeDisponivel() {
		return quantidadeDisponivel;
	}

	public void setQuantidadeDisponivel(double quantidadeDisponivel) {
		this.quantidadeDisponivel = quantidadeDisponivel;
	}

	public double getPizzasDisponiveis() {
		return pizzasDisponiveis;
	}

	public void setPizzasDisponiveis(double pizzasDisponiveis) {
		this.pizzasDisponiveis = pizzasDisponiveis;
	}

	public double getLucro() {
		return lucro;
	}

	public void setLucro(double lucro) {
		this.lucro = lucro;
	}

	public double getTotalGastoNoMes() {
		return totalGastoNoMes;
	}

	public void setTotalGastoNoMes(double totalGastoNoMes) {
		this.totalGastoNoMes = totalGastoNoMes;
	}

}
