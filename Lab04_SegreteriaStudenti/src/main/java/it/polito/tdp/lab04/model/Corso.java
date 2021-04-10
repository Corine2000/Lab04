package it.polito.tdp.lab04.model;

public class Corso implements Comparable<Corso>{
	
	private String codins;
	private int crediti;
	private String nomeCorso;
	private int periodoDidatico;
	
	public Corso(String codins, int crediti, String nomeCorso, int periodoDidatico) {
		super();
		this.codins = codins;
		this.crediti = crediti;
		this.nomeCorso = nomeCorso;
		this.periodoDidatico = periodoDidatico;
	}

	public String getCodins() {
		return codins;
	}

	public void setCodins(String codins) {
		this.codins = codins;
	}

	public int getCrediti() {
		return crediti;
	}

	public void setCrediti(int crediti) {
		this.crediti = crediti;
	}

	public String getNomeCorso() {
		return nomeCorso;
	}

	public void setNomeCorso(String nomeCorso) {
		this.nomeCorso = nomeCorso;
	}

	public int getPeriodoDidatico() {
		return periodoDidatico;
	}

	public void setPeriodoDidatico(int periodoDidatico) {
		this.periodoDidatico = periodoDidatico;
	}

	@Override
	public String toString() {
		return this.codins+" "+this.crediti+" "+this.nomeCorso+" "+this.periodoDidatico;
	}

	@Override
	public int compareTo(Corso altro) {
		// TODO Auto-generated method stub
		return this.nomeCorso.compareTo(altro.getNomeCorso());
	}
	
	
}
