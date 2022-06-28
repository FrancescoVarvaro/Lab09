package it.polito.tdp.borders.model;

public class Border {
	private int idStato1;
	private int idStato2;
	private String stato1;
	private String stato2;
	
	public Border(int idStato1, String stato1, int idStato2, String stato2) {
		super();
		this.idStato1=idStato1;
		this.idStato2=idStato2;
		this.stato1 = stato1;
		this.stato2 = stato2;
	}
	public String getStato1() {
		return stato1;
	}
	public void setStato1(String stato1) {
		this.stato1 = stato1;
	}
	public String getStato2() {
		return stato2;
	}
	public void setStato2(String stato2) {
		this.stato2 = stato2;
	}
	
	public int getIdStato1() {
		return idStato1;
	}
	public int getIdStato2() {
		return idStato2;
	}
	
	
}
