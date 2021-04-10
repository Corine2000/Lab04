package it.polito.tdp.lab04.DAO;

import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class TestDB {

	public static void main(String[] args) {

		/*
		 * 	This is a main to check the DB connection
		 */
		
		CorsoDAO cdao = new CorsoDAO();
		StudenteDAO sdao = new StudenteDAO();
		
		for(Corso c: cdao.getTuttiICorsi())
		System.out.println(c.toString()); //stampa tutti i corsi del database
		
		Studente trovato = sdao.getStudente(190710);
		System.out.println(sdao.esisteStudente(190710));
		System.out.println(trovato.toString());
		
		List<Corso> list = cdao.getCorsiByStudente(190710);
		for(Corso c: list)
			System.out.println(c.toString()); 
		
		boolean iscrizione = cdao.EsisteIscrizioneAdUnCorso(190710, null);
		
	}

}
