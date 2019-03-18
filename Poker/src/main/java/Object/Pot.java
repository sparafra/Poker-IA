package Object;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("debtforPlaying")

public class Pot {
	
	float pot;
	
	
	float bet;
	
	@Param(0)
	int betInt;

	public Pot()
	{
		
	}

	public float getPot() {
		return pot + bet;
	}

	public void setPot(float pot) {
		this.pot = pot;
	}
	public float getBet() {
		return bet;
	}

	public void setBet(float bet) {
		this.bet = bet;
		this.betInt = (int) (bet*100);
	}
	
	public void setBetInt(int betInt) {
		this.betInt = betInt;
	}

	public int getBetInt() {return betInt;}
	public void add(float val) {this.pot += val;}
}
