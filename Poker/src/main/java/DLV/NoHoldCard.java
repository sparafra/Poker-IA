package DLV;
import Object.Card;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("cardNoHolded")

public class NoHoldCard{

	@Param(0)
	protected int f;
	//Face f;	
	
	@Param(1)
	protected String s;
	//Suite s;	

	public NoHoldCard(int f, String s)
	{
		//super(f, s);
		this.f = f;
		this.s = s;
	}
	public NoHoldCard()
	{
		super();
	}
	/*
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
	*/
	
	public String toString()
	{
		return f+s.toString();
	}
	public int getF() {
		return f;
	}
	public void setF(int f) {
		this.f = f;
	}
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	
	
}
