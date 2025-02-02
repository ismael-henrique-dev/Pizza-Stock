package Models;

public class Receita {
	private double massa = 0;
	private double calabresa = 0;
	private double queijo = 0;
	
	public Receita(double massa, double calabresa, double queijo) {
		this.massa = massa;
		this.calabresa = calabresa;
		this.queijo = queijo;
	}

	public double getMassa() {
		return massa;
	}

	public void setMassa(double massa) {
		this.massa = massa;
	}

	public double getCalabresa() {
		return calabresa;
	}

	public void setCalabresa(double calabresa) {
		this.calabresa = calabresa;
	}

	public double getQueijo() {
		return queijo;
	}

	public void setQueijo(double queijo) {
		this.queijo = queijo;
	}

	
}
