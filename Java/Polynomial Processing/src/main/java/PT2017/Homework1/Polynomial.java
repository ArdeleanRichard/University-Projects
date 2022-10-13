package PT2017.Homework1;

import java.util.Comparator;
import java.util.TreeSet;

public class Polynomial 
{
	private TreeSet<Monomial> monoame;
	private Comparator<Monomial> comparator = (Monomial a, Monomial b) -> a.getDeg().compareTo(b.getDeg());
	
	public Polynomial()
	{
		monoame = new TreeSet<Monomial>(comparator.reversed());
	}	
	
	public TreeSet<Monomial> getMonoame() {
		return monoame;
	}
	
	private Monomial find(Integer degree) {
		for (Monomial Monomial: monoame) {
			if(Monomial.getDeg() == degree)
				return Monomial;
			if(Monomial.getDeg() < degree)
				return null;
		}
		return null;
	}
	
	public void insertMonomial(Monomial Monomial) {
		monoame.add(Monomial);
	}


	public Monomial inv(Monomial m)
	{
		m.setCoeff(-1*m.getCoeff().intValue());
		return m;
	}
	
	public Polynomial add(Polynomial other)
	{
		for(Monomial MonomialY: other.getMonoame()) {
			Monomial MonomialX = find(MonomialY.getDeg());
			if(MonomialX != null) {
				MonomialX.add(MonomialY);
			}
			else {
				insertMonomial(MonomialY);
			}
		}
		return this;
	}
	public Polynomial sub(Polynomial other)
	{
		for(Monomial MonomialY: other.getMonoame()) {
			Monomial MonomialX = find(MonomialY.getDeg());
			if(MonomialX != null) {
				MonomialX.sub(MonomialY);
			}
			else {
				insertMonomial(inv(MonomialY));
			}
		}
		return this;
	}
	
	public void insertMinP(Monomial Monomial, Polynomial Polynomial) {
		Polynomial.monoame.add(Monomial);
	}
	public Polynomial mul(Polynomial x,Polynomial y)
	{
		Polynomial z=new Polynomial();
		for(Monomial MonomialX: x.getMonoame())
		{
			for(Monomial MonomialY: y.getMonoame())
			{
				Monomial MonomialZ=new Monomial();
				MonomialZ.mul(MonomialX,MonomialY);
				insertMinP(MonomialZ,z);
				for(Monomial m: z.getMonoame())
					if(MonomialZ.getDeg()==m.getDeg() && MonomialZ.getCoeff()!=m.getCoeff())
					{
						m.add(MonomialZ);
					}
			}
		}
		for(Monomial MonomialZ: z.getMonoame())
			this.insertMonomial(MonomialZ);
		return this;
	}
	
	public Polynomial divConst(int nr)
	{
		for(Monomial m: this.getMonoame())
		{
			m.setCoeff(m.getCoeff().floatValue()/nr);
		}
		return this;
	}
	
	public int maxDeg()
	{
		int max=0;
		for(Monomial m: this.getMonoame())
		{
			if(max<m.getDeg())
				max=m.getDeg();
		}
		return max;
	}
	
	public Polynomial mulMonomial(Monomial x)
	{
		for(Monomial m: this.getMonoame())
			m.mul(m, x);
		return this;
	}
	
	
	public Polynomial div(Polynomial x, Polynomial remainder) //polynomial division function
	{
		Polynomial auxP = new Polynomial(); // auxiliary first Polynomial 
		Polynomial auxD = new Polynomial(); // auxiliary second Polynomial
		Polynomial prevD;
		for(Monomial MonomialX: this.getMonoame())
		{
			insertMinP(MonomialX, auxD); //copy the values from polynomial(current object, dividend) into auxD
		}
		if(x.maxDeg() == 0) //if the second polynomial has the highest degree 0 => second polynomial is a constant 
	    	for(Monomial m: this.getMonoame())
	    		this.divConst(m.getDeg()); // so you divide each coefficient of the first polynomial to the constant
		else
		{
			while(auxD.maxDeg() >= x.maxDeg() && x.find(x.maxDeg()).getCoeff().floatValue() != 0) // if the divisor is bigger than the dividend then dividend = remainder so
			{ //it goes out of the loop
				float coefD = auxD.find(auxD.maxDeg()).getCoeff().floatValue() / x.find(x.maxDeg()).getCoeff().floatValue(); //for each division create a coefficient
				int degD = auxD.maxDeg() - x.maxDeg(); //for each division create a degree 
				Monomial m = new Monomial(coefD, degD); // the new coefficient and degree create a Monomial
				auxP.insertMonomial(m);  //this Monomial is inserted in auxP
				prevD = new Polynomial(); // new polynomial
				for(Monomial MonomialX: x.getMonoame())
		    		{
		    			insertMinP(MonomialX, prevD); //initialize the new polynomial with the values of polynomial x by inserting in prevD the monomial
		    		}
				prevD = x.mulMonomial(m); // multiply prevD with the monomial created before
				auxD.sub(prevD); // subtract from auxD, prevD
			}
			this.monoame = auxP.monoame; //copy auxP-quotient into the current object
			if(auxD.maxDeg() > 0 || auxD.find(auxD.maxDeg()).getCoeff().floatValue() != 0)
			{
				remainder.monoame = auxD.monoame; //copy auxD-remainder into the remainder polynomial
			}
		}
		return this;
	}

	
	public Polynomial deriv()
	{
		for(Monomial Monomial: this.getMonoame())
			Monomial.deriv();
		return this;
	}
	
	public Polynomial integ()
	{
		for(Monomial Monomial: this.getMonoame())
			Monomial.integ();
		return this;
	}
	
	public boolean isEmpty()
	{
		int k=0;
		for(Monomial m: this.getMonoame())
		{
			k++;
		}
		if(k==0)
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		String str="";
		for(Monomial m: this.getMonoame())
		{
			int c= m.getCoeff().intValue();
			if(c!=m.getCoeff().floatValue())
    			if(m.getCoeff().floatValue()>=0)
    				str=str+m.getCoeff()+"x^"+m.getDeg()+"+";
    			else
    				str=str+"("+m.getCoeff()+")"+"x^"+m.getDeg()+"+";
			else
    			if(m.getCoeff().floatValue()>=0)
    				str=str+c+"x^"+m.getDeg()+"+";
    			else
    				str=str+"("+c+")"+"x^"+m.getDeg()+"+";
		}
		if(this.isEmpty()==true)
			return "0";
		str=str.substring(0, str.length()-1);
		return str;
	}
	public Polynomial simplify()
	{
		for(Monomial m: this.getMonoame())
			if(m.getCoeff().floatValue()==0)
				this.monoame.remove(m);
		return this;
	}
	
	public boolean equals(Object a) // we also override the equals methods
	{
		int ok=1;
		Polynomial b=new Polynomial();
		//copy this into b
		for(Monomial bla: this.getMonoame())
			b.insertMonomial(bla); 
		
		//subtract a from b and if you get 0 then its equal
		b.sub((Polynomial)(a));
		
		//sees if all coefficients are 0
		for(Monomial bla: b.getMonoame())
			if(bla.getCoeff().floatValue()!=0)
				ok=0; //signals if it has any coefficient different from 0
		
		if(ok==1)
			return true;
		return false;
	}
}
