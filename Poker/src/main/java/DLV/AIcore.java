package DLV;


import java.util.ArrayList;
import java.util.Random;


import Object.Card;
import Object.Hand;
import Object.Pot;
import Object.Stack;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

public class AIcore {

	private static Handler handler;    
	private static String encodingResourceDiscardCard="encodings/cambiaCarte";
	private static String encodingResourceChooseMoves="encodings/scegliMossa";

	InputProgram encoding;
	
	public AIcore()
	{
		handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
		/*
        encoding= new ASPInputProgram();
 	    encoding.addFilesPath(encodingResourceDiscardCard);
        handler.addProgram(encoding);
        */
	}
	
	public Decision getFinalDecision(Hand h, Stack s, Pot p, cardChanged cc)
	{
		encoding= new ASPInputProgram();
 	    encoding.addFilesPath(encodingResourceChooseMoves);
        handler.addProgram(encoding);
		
		Decision d = null;
		
		InputProgram facts= new ASPInputProgram();
		
		for(int k=0; k<h.getCards().size(); k++)
		{
			Card c = h.getCards().get(k);
			try {
				facts.addObjectInput(c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			facts.addObjectInput(s);
			facts.addObjectInput(p);
			facts.addObjectInput(cc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handler.addProgram(facts);
  	   	System.out.println("Fact");

  	   	System.out.println(facts.getPrograms());
  	   	
  	   	try {
			ASPMapper.getInstance().registerClass(Card.class);
			ASPMapper.getInstance().registerClass(NoHoldCard.class);
			ASPMapper.getInstance().registerClass(Stack.class);
			ASPMapper.getInstance().registerClass(Pot.class);
			ASPMapper.getInstance().registerClass(cardChanged.class);
			ASPMapper.getInstance().registerClass(Decision.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//   	   handler.startAsync(new MyCallback(gipfBoardComponent));
		
   	    Output o =  handler.startSync();
   	    
//   	    System.out.println("HO STARTATO");
   	    
	  		AnswerSets answers = (AnswerSets) o;
	  		System.out.println("ansersets");
	  		System.out.println(answers.getAnswersets().size());
	  		if (answers.getAnswersets().size() != 0)
	  		{
		  		for(AnswerSet a:answers.getAnswersets()){
		  			System.out.println("weight");
		  			System.out.println(a.getWeights());
		  			System.out.println(a.toString());
		  			try {
		  				for(Object obj:a.getAtoms()){
		  					if(! (obj instanceof Decision))
		  					{
		  						continue;
		  					}
		  					
		  					//gipfBoardComponent.game.applyMove(((Mossa) obj).getMove());
		  					
		  					System.out.println("MOSSA APPLICATA");
		  					d = new Decision();
		  					d.setMossa(((Decision)obj).getMossa());
		  					d.setBet(((Decision)obj).getBet());
		  					
		  					System.out.println(d.toString());
		  					//System.out.println(((NoHoldCard)obj).toString());
		  					break;
		  				}
		  			} catch (Exception e) {
		  				e.printStackTrace();
		  			} 
		  			
		  		}
		  		return d;
	  		}
	  		else
	  		{
	  			/*
	  			Object[] allowedMoves=gipfBoardComponent.game.getAllowedMoves().toArray();
	  			int i = new Random().nextInt(allowedMoves.length);
	  			try {
					gipfBoardComponent.game.applyMove((Move) allowedMoves[i]);
				} catch (Exception e) {
					// fai un cazzo e riprova	
				}
				*/
	  		}

   	  
	  		handler.removeProgram(facts);
	  		return null;
		
		
	}
	
	public ArrayList<Card> getCardToDiscard(Hand h)
	{
		encoding= new ASPInputProgram();
 	    encoding.addFilesPath(encodingResourceDiscardCard);
        handler.addProgram(encoding);
		
		ArrayList<Card> array = new ArrayList<Card>();
		
		InputProgram facts= new ASPInputProgram();
		
		for(int k=0; k<h.getCards().size(); k++)
		{
			Card c = h.getCards().get(k);
			try {
				facts.addObjectInput(c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		handler.addProgram(facts);
  	   	System.out.println("Fact");

  	   	System.out.println(facts.getPrograms());
  	   	
  	   	try {
			ASPMapper.getInstance().registerClass(Card.class);
			ASPMapper.getInstance().registerClass(NoHoldCard.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//   	   handler.startAsync(new MyCallback(gipfBoardComponent));
		
   	    Output o =  handler.startSync();
   	    
//   	    System.out.println("HO STARTATO");
   	    
	  		AnswerSets answers = (AnswerSets) o;
	  		System.out.println("ansersets");
	  		System.out.println(answers.getAnswersets().size());
	  		if (answers.getAnswersets().size() != 0)
	  		{
		  		for(AnswerSet a:answers.getAnswersets()){
		  			System.out.println("weight");
		  			System.out.println(a.getWeights());
		  			System.out.println(a.toString());
		  			try {
		  				for(Object obj:a.getAtoms()){
		  					if(! (obj instanceof NoHoldCard))
		  					{
		  						continue;
		  					}
		  					
		  					//gipfBoardComponent.game.applyMove(((Mossa) obj).getMove());
		  					
		  					System.out.println("MOSSA APPLICATA");
		  					Card c = new Card();
		  					c.setF(((NoHoldCard)obj).getF());
		  					c.setS(((NoHoldCard)obj).getS().replaceAll("\"", ""));
		  					array.add(c);
		  					System.out.println(c.toString());
		  					System.out.println(((NoHoldCard)obj).toString());
		  					//break;
		  				}
		  			} catch (Exception e) {
		  				e.printStackTrace();
		  			} 
		  			
		  		}
		  		return array;
	  		}
	  		else
	  		{
	  			/*
	  			Object[] allowedMoves=gipfBoardComponent.game.getAllowedMoves().toArray();
	  			int i = new Random().nextInt(allowedMoves.length);
	  			try {
					gipfBoardComponent.game.applyMove((Move) allowedMoves[i]);
				} catch (Exception e) {
					// fai un cazzo e riprova	
				}
				*/
	  		}

   	  
	  		handler.removeProgram(facts);
	  		return null;
	}
	
}
