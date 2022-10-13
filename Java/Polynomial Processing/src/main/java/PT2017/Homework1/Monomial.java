package PT2017.Homework1;

public class Monomial {
	private Number coeff;
	private Integer deg;

	Monomial() {
		this(0, 0);
	}
	
	Monomial(Number coeff, Integer deg)
	{
		this.coeff=coeff;
		this.deg=deg;
	}
	
	public Number getCoeff() {
		return coeff;
	}	

	public void setCoeff(Number coeff) {
		this.coeff = coeff;
	}

	public Integer getDeg() {
		return deg;
	}

	public void setDeg(int deg) {
		this.deg = deg;
	}
	
	public Monomial add(Monomial m)
	{
		if(deg == m.getDeg())
			coeff = coeff.intValue() + m.getCoeff().intValue();
		return this;
	}
	public Monomial sub(Monomial m)
	{
		if(deg==m.getDeg())
			coeff = coeff.floatValue() - m.getCoeff().floatValue();
		return this;
	}
	
	public Monomial mul(Monomial m1, Monomial m2)
	{
		this.setDeg(m1.getDeg()+m2.getDeg());
		this.setCoeff(m1.getCoeff().floatValue()*m2.getCoeff().floatValue());
		return this;
	}
	
	public Monomial deriv()
	{
		if(this.getDeg()==0)
		{
			this.setDeg(0);
			this.setCoeff(0);
			return this;
		}
		this.setCoeff(this.getCoeff().intValue()*this.getDeg().intValue());
		this.setDeg(this.getDeg()-1);
		return this;
	}
	public void integ()
	{
		this.setDeg(this.getDeg()+1);
		this.setCoeff(this.getCoeff().floatValue()/this.getDeg());
		
	}
	
	@Override
	public String toString() {
			return coeff + "x^" + deg;
	}
}
