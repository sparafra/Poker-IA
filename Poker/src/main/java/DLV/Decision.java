package DLV;
import Object.Card;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("finalDecision")

public class Decision{

	@Param(0)
	protected int mossa;
	//Face f;	
	
	@Param(1)
	protected int betInt;
	//Suite s;	

	float bet;
	
	public Decision(int mossa, int bet)
	{
		//super(f, s);
		this.mossa = mossa;
		this.betInt = bet;
	}
	public Decision()
	{
		
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
		return "Decision:" + String.valueOf(mossa)+"-"+String.valueOf(bet);
	}
	public int getMossa() {
		return mossa;
	}
	public void setMossa(int mossa) {
		this.mossa = mossa;
	}
	public int getBetInt() {
		return betInt;
	}
	public void setBetInt(int bet) {
		this.betInt = bet;
		this.bet = betInt/100;
	}
	public float getBet() {
		return bet;
	}
	public void setBet(float bet) {
		this.bet = bet;
	}
	
	
}
