package accesoBD;

public enum EnumMarcas {

	SEAT("Martorell"), VOLKSWAGEN("Navarra"), MERCEDES("Vitoria"), NISSAN("Barcelona");
	
	private String loc;
	
	EnumMarcas(String loc) {
		this.loc = loc;
	}
	
	public String getLoc() {
		return this.loc;
	}
}
