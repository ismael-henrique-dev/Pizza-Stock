package Models;

public class Pizza extends Receita {
	private double precoBase = 50.0;

	public Pizza(double massa, double calabresa, double queijo, double precoBase) {
		super(massa, calabresa, queijo);
		this.precoBase = precoBase;
	}

	@Override
	public double getCalabresa() {
		// TODO Auto-generated method stub
		return super.getCalabresa();
	}

	@Override
	public double getMassa() {
		// TODO Auto-generated method stub
		return super.getMassa();
	}

	@Override
	public double getQueijo() {
		// TODO Auto-generated method stub
		return super.getQueijo();
	}

	@Override
	public void setCalabresa(final double calabresa) {
		// TODO Auto-generated method stub
		super.setCalabresa(calabresa);
	}

	@Override
	public void setMassa(final double massa) {
		// TODO Auto-generated method stub
		super.setMassa(massa);
	}

	@Override
	public void setQueijo(final double queijo) {
		// TODO Auto-generated method stub
		super.setQueijo(queijo);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(final Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public double getPrecoBase() {
		return precoBase;
	}

	public void setPrecoBase(double precoBase) {
		this.precoBase = precoBase;
	}

}
