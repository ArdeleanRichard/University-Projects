package PT2017.Homework1;

import static org.junit.Assert.*;

import org.junit.Test;

public class PolynomialTest {

	@Test
	public void testAdd() {
		Polynomial a=new Polynomial();
		Polynomial b=new Polynomial();
		Polynomial c=new Polynomial();
		a.insertMonomial(new Monomial(1,1)); //inserting a random monomial in a
		a.insertMonomial(new Monomial(2,2)); //inserting a random monomial in a
		b.insertMonomial(new Monomial(1,2)); //inserting a random monomial in b
		c.insertMonomial(new Monomial(3,2)); //inserting a monomial of the result in c
		c.insertMonomial(new Monomial(1,1)); //inserting a monomial of the result in c
		a.add(b); // adds the 2 polynomials and puts the result in a
		assertEquals(c,a); //compare a and c
		
		a=new Polynomial();
		b=new Polynomial();
		c=new Polynomial();
		a.insertMonomial(new Monomial(2,1));
		a.insertMonomial(new Monomial(2,3));
		c.insertMonomial(new Monomial(2,3));
		c.insertMonomial(new Monomial(2,1));
		a.add(b);
		assertEquals(a,c);
		/*
		a=new Polynomial();
		b=new Polynomial();
		c=new Polynomial();
		a.insertMonomial(new Monomial(4,1));
		a.insertMonomial(new Monomial(2,3));
		b.insertMonomial(new Monomial(2,5));
		b.insertMonomial(new Monomial(2,2));
		a.add(b);
		assertEquals(a,c);*/
	}
	
	@Test
	public void testSub() {
		Polynomial a=new Polynomial();
		Polynomial b=new Polynomial();
		Polynomial c=new Polynomial();
		a.insertMonomial(new Monomial(1,1));
		a.insertMonomial(new Monomial(2,2));
		b.insertMonomial(new Monomial(1,2));
		c.insertMonomial(new Monomial(1,2));
		c.insertMonomial(new Monomial(1,1));
		a.sub(b);
		assertEquals(a,c);
		
		a=new Polynomial();
		b=new Polynomial();
		c=new Polynomial();
		a.insertMonomial(new Monomial(2,1));
		a.insertMonomial(new Monomial(2,3));
		c.insertMonomial(new Monomial(2,3));
		c.insertMonomial(new Monomial(2,1));
		a.sub(b);
		assertEquals(a,c);
		
		a=new Polynomial();
		b=new Polynomial();
		c=new Polynomial();
		a.insertMonomial(new Monomial(4,1));
		a.insertMonomial(new Monomial(2,3));
		b.insertMonomial(new Monomial(2,5));
		b.insertMonomial(new Monomial(2,2));
		c.insertMonomial(new Monomial(4,1));
		c.insertMonomial(new Monomial(2,3));
		c.insertMonomial(new Monomial(-2,5));
		c.insertMonomial(new Monomial(-2,2));
		a.sub(b);
		assertEquals(a,c);
		/*
		a=new Polynomial();
		b=new Polynomial();
		c=new Polynomial();
		a.insertMonomial(new Monomial(4,1));
		a.insertMonomial(new Monomial(2,3));
		b.insertMonomial(new Monomial(2,5));
		b.insertMonomial(new Monomial(2,2));
		a.sub(b);
		assertEquals(a,c);*/
	}
	
	@Test
	public void testMul() {
		Polynomial a=new Polynomial();
		Polynomial b=new Polynomial();
		Polynomial c=new Polynomial();
		Polynomial d=new Polynomial();
		a.insertMonomial(new Monomial(3,2));
		b.insertMonomial(new Monomial(2,1));
		c.insertMonomial(new Monomial(6,3));
		d.mul(a,b);
		assertEquals(d,c);
		/*
		a=new Polynomial();
		b=new Polynomial();
		c=new Polynomial();
		d=new Polynomial();
		a.insertMonomial(new Monomial(1,2));
		a.insertMonomial(new Monomial(5,1));
		a.insertMonomial(new Monomial(1,0));
		b.insertMonomial(new Monomial(3,2));
		b.insertMonomial(new Monomial(10,1));
		b.insertMonomial(new Monomial(15,0));
		c.insertMonomial(new Monomial(3,4));
		c.insertMonomial(new Monomial(25,3));
		c.insertMonomial(new Monomial(68,2));
		c.insertMonomial(new Monomial(85,1));
		c.insertMonomial(new Monomial(15,0));
		d.mul(a,b);
		assertEquals(d,c);
		
		a=new Polynomial();
		b=new Polynomial();
		c=new Polynomial();
		a.insertMonomial(new Monomial(4,1));
		a.insertMonomial(new Monomial(2,3));
		b.insertMonomial(new Monomial(2,5));
		b.insertMonomial(new Monomial(2,2));
		d.mul(a,b);
		assertEquals(a,c);*/
	}
	
	@Test
	public void testDiv() {
		Polynomial a=new Polynomial();
		Polynomial b=new Polynomial();
		Polynomial c=new Polynomial();
		Polynomial rem=new Polynomial();
		a.insertMonomial(new Monomial(3,2));
		b.insertMonomial(new Monomial(1,2));
		c.insertMonomial(new Monomial(3,0));
		a.div(b,rem);
		assertEquals(a,c);
		/*
		a=new Polynomial();
		b=new Polynomial();
		c=new Polynomial();
		rem=new Polynomial();
		Polynomial rem2=new Polynomial();
		a.insertMonomial(new Monomial(1,1));
		a.insertMonomial(new Monomial(2,3));
		rem.insertMonomial(new Monomial(2,3));
		a.div(b,rem);
		assertEquals(a,c);
		assertEquals(rem, rem2);*/
		
	}
	
	@Test
	public void testDer() {
		Polynomial a=new Polynomial();
		Polynomial b=new Polynomial();
		a.insertMonomial(new Monomial(1,1));
		a.insertMonomial(new Monomial(2,2));
		b.insertMonomial(new Monomial(4,1));
		b.insertMonomial(new Monomial(1,0));
		a.deriv();
		assertEquals(a,b);
		
		a=new Polynomial();
		b=new Polynomial();
		a.insertMonomial(new Monomial(1,0));
		a.deriv();
		assertEquals(a,b);
	}
	
	@Test
	public void testInt() {
		Polynomial a=new Polynomial();
		Polynomial b=new Polynomial();
		a.insertMonomial(new Monomial(2,1));
		a.insertMonomial(new Monomial(3,2));
		b.insertMonomial(new Monomial(1,3));
		b.insertMonomial(new Monomial(1,2));
		a.integ();
		assertEquals(a,b);
		
		a=new Polynomial();
		b=new Polynomial();
		a.insertMonomial(new Monomial(4,2));
		b.insertMonomial(new Monomial(1,3));
		a.integ();
		assertEquals(a,b);
	}
	
	
}
