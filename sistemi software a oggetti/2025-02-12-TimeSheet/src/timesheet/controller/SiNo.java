package timesheet.controller;

public enum SiNo {
	SI(true, "Sì"), NO(false, "No");
	
	private boolean state;
	private String description;

	private SiNo(boolean state, String description) {
		this.state=state; this.description=description;
	}
	
	@Override public String toString() {
		return description;
	}

	public boolean getValue() {
		return state;
	} 
	
	public static SiNo of(boolean b) {
		return b ? SI : NO;
	}
}
