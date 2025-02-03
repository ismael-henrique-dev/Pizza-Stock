package Models;

public class Pizza extends Receita {
	private double precoBase = 50.0;

	public Pizza(double massa, double calabresa, double queijo, double precoBase) {
		super(massa, calabresa, queijo);
		this.precoBase = precoBase;
	}

	@Override
	public double getCalabresa() {
		return super.getCalabresa();
	}

	@Override
	public double getMassa() {
		return super.getMassa();
	}

	@Override
	public double getQueijo() {
		return super.getQueijo();
	}

	@Override
	public void setCalabresa(final double calabresa) {
		super.setCalabresa(calabresa);
	}

	@Override
	public void setMassa(final double massa) {
		super.setMassa(massa);
	}

	@Override
	public void setQueijo(final double queijo) {
		super.setQueijo(queijo);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public boolean equals(final Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public double getPrecoBase() {
		return precoBase;
	}

	public void setPrecoBase(double precoBase) {
		this.precoBase = precoBase;
	}

}
