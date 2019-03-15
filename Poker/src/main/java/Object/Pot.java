package Object;

public class Pot {
	
	float pot;
	float bet;
	

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
	}
	public void add(float val) {this.pot += val;}
}
