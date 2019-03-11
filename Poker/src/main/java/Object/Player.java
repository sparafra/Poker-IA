package Object;

public class Player {

	String Name;
	Stack stack;
	Hand actualHand;
	boolean Dealer;
	boolean myTurn;
	boolean checkedCall;
	
	public Player(String Name)
	{
		this.Name = Name;
		Dealer = false;
		myTurn = false;
	}
	
	public boolean isMyTurn() {
		return myTurn;
	}

	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Stack getStack() {
		return stack;
	}

	public void setStack(Stack stack) {
		this.stack = stack;
	}

	public Hand getActualHand() {
		return actualHand;
	}

	public void setActualHand(Hand actualHand) {
		this.actualHand = actualHand;
	}

	public boolean isDealer() {
		return Dealer;
	}

	public void setDealer(boolean dealer) {
		Dealer = dealer;
	}

	
}
