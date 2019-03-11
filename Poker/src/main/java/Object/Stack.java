package Object;

public class Stack {
	
	float stack;
	
	public Stack(float stack)
	{
		this.stack = stack;
	}
	
	public float getStack() {return stack;}
	public void setStack(float stack) {this.stack = stack;}
	
	public void sumToStack(float val) {this.stack += val;}
	public void takeByStack(float val) {this.stack -= val;}
}
