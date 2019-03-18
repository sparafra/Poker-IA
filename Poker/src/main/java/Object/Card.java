package Object;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("card")

public class Card {

	@Param(0)
	protected int f;
	//Face f;	
	
	@Param(1)
	protected String s;
	//Suite s;	
	
	/*
	public Card(Face f, Suite s)
	{
		this.f = f;
		this.s = s;
	}
	*/
	public Card(int f, String s)
	{
		this.f = f;
		this.s = s;
	}
	public Card()
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
	@Override
    public boolean equals(Object o) 
	{ 
		// If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Card)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        Card c = (Card) o; 
          
        // Compare the data members and return accordingly  
        return f == c.getF() && s.equals(c.getS()); 
	}
	
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
