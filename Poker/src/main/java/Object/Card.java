package Object;

public class Card {

	Face f;
	Suite s;
	public Card(Face f, Suite s)
	{
		this.f = f;
		this.s = s;
	}
	public Face getF() {
		return f;
	}
	public void setF(Face f) {
		this.f = f;
	}
	public Suite getS() {
		return s;
	}
	public void setS(Suite s) {
		this.s = s;
	}
	
	public String toString()
	{
		return f.toString()+s.toString();
	}
	
}
