package DLV;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("cardNoChanged")

public class cardChanged {

	@Param(0)
	int changed;
	
	public cardChanged(int c) {changed = c;}
	
	public cardChanged() {}

	public int getChanged() {
		return changed;
	}

	public void setChanged(int changed) {
		this.changed = changed;
	}
	
	
}
