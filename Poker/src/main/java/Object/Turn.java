package Object;

public enum Turn {
	FIRSTBET, DISCARDCARD, SECONDBET, SHOWDOWN;
	private static Turn[] vals = values();
    public Turn next()
    {
    	System.out.println((this.ordinal()+1) % vals.length);
        return vals[(this.ordinal()+1) % vals.length];
    }
}
