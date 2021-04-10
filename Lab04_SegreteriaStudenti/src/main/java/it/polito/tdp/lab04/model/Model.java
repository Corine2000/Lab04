package it.polito.tdp.lab04.model;

import java.util.*;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
   CorsoDAO corsoDAO ;
   StudenteDAO sDAO ;
   
	public Model() {
		corsoDAO =new CorsoDAO();
		sDAO = new StudenteDAO();
	}

	public List<Corso> getListaCorsi(){ //questo metodo mi restituisce una lista che contiene il nome di tutti i corsi
		//List<String> lista = new LinkedList<>();
		
		List<Corso> corsi = corsoDAO.getTuttiICorsi();
		Collections.sort(corsi);
		//corsi.add(null);
		Corso vuoto = new Corso(null,0,null,0);
		corsi.add(vuoto);
			
		return corsi;
	}
	
	public Studente GetStudente(Integer matricola) {
		return sDAO.getStudente(matricola);
	}

	public boolean esisteStudente(int matricola) {
		return sDAO.esisteStudente(matricola);
	}
	
	public List<Studente> getStudenti(Corso corso){
		return corsoDAO.getStudentiIscrittiAlCorso(corso);
	}
	
	public List<Corso> getCorsiByStudente(Integer matricola ){
		return corsoDAO.getCorsiByStudente(matricola);
	}
	
	public boolean EsisteRelazioneTraStudenteECorso(int matricola, Corso corso) {
		return corsoDAO.EsisteIscrizioneAdUnCorso(matricola, corso);
	}
	
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		return corsoDAO.inscriviStudenteACorso(studente, corso);
	}
}
