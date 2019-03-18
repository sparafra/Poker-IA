package Object;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("myMoney")

public class Stack {
	
	
	float stack;
	
	@Param(0)
	int stackInt;
	
	public Stack(float stack)
	{
		this.stack = stack;
		this.stackInt = (int) (stack*100);

	}
	public Stack() { }
	
	public float getStack() {return stack;}
	public void setStack(float stack) 
	{
		this.stack = stack;
		this.stackInt = (int) (stack*100);
	}
	public int getStackInt() {return stackInt;}
	public void sumToStack(float val) 
	{
		this.stack += val;
		this.stackInt = (int) (stack*100);

	}
	public void takeByStack(float val) 
	{
		this.stack -= val;
		this.stackInt = (int) (stack*100);

	}
	public void setStackInt(int stackInt) {
		this.stackInt = stackInt;
	}
	
}
