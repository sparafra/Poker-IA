package DLV;


import java.util.Random;


import Object.Card;
import Object.Hand;
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
	private static String encodingResource="encodings/cambiaCarte";
    
	
	public AIcore()
	{
		handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
        InputProgram encoding= new ASPInputProgram();
 	    encoding.addFilesPath(encodingResource);
        handler.addProgram(encoding);
	}
	
	public void Do(Hand h)
	{
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
  	   	System.out.println(facts.getPrograms());
  	   	
  	   	try {
			ASPMapper.getInstance().registerClass(Card.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//   	   handler.startAsync(new MyCallback(gipfBoardComponent));
		
   	    Output o =  handler.startSync();
   	    
//   	    System.out.println("HO STARTATO");
   	    
	  		AnswerSets answers = (AnswerSets) o;
	  		
	  		System.out.println(answers.getAnswersets().size());
	  		if (answers.getAnswersets().size() != 0)
	  		{
		  		for(AnswerSet a:answers.getAnswersets()){
		  			System.out.println(a.getWeights());
		  			try {
		  				for(Object obj:a.getAtoms()){
		  					if(! (obj instanceof Card))
		  						{
		  						continue;
		  						}
		  					
		  					//gipfBoardComponent.game.applyMove(((Mossa) obj).getMove());
		  					
	//	  					System.out.println("MOSSA APPLICATA");
		  					System.out.println(((Card)obj).getF() + "-" + ((Card)obj).getS());
		  					break;
		  				}
		  			} catch (Exception e) {
		  				e.printStackTrace();
		  			} 
		  			
		  		}
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
	}
	
}
