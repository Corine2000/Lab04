package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	
	List<Studente> listaStudenti = new LinkedList<Studente>();
	Corso corso ;
	
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

			//	System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				// Crea un nuovo JAVA Bean Corso
				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				corsi.add(c);
			}
             
			//corsi.add(null);
			
			conn.close();
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public void getCorso(Corso corso) {
		
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola=i.matricola AND i.codins=? ";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Studente stemp = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
				listaStudenti.add(stemp);
			}
			
			st.close();
			conn.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore nel metodo getStudentiIscrittiAlCorso", e);
		}
		
		return listaStudenti;
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		// TODO
		// ritorna true se l'iscrizione e' avvenuta con successo
		String sql = "INSERT INTO iscrizione(matricola,codins) "
				+ "VALUES (?,?)";
		boolean iscritto = false;
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1,studente.getMatricola());
			st.setString(2, corso.getCodins());
			
			int rs = st.executeUpdate();
			if(rs == 1) {
				iscritto=true;
			}else
				iscritto = false;
			
			conn.close();
		}catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore nel metodo getStudentiIscrittiAlCorso", e);
		}
		

		return iscritto;
	}


	public List<Studente> getListaStudenti() {
		return listaStudenti;
	}



	public Corso getCorso() {
		return corso;
	}


	/*public void setCorso(Corso corso) {
		this.corso = corso;
	}*/
	
	 public List<Corso> getCorsiByStudente(Integer matricola ){
		 List<Corso> risultato = new ArrayList<>();
		 String sql = "SELECT c.codins, c.crediti, c.nome, c.pd "
		 		+ "FROM corso c, iscrizione i "
		 		+ "WHERE c.codins = i.codins AND i.matricola=? ";
		 
		 try {
			 Connection conn = ConnectDB.getConnection();
			 PreparedStatement st = conn.prepareStatement(sql);
			 st.setInt(1, matricola);
			 
			 ResultSet rs = st.executeQuery();
			 while(rs.next()) {
				 Corso nuovo = new Corso(rs.getString("codins"),rs.getInt("crediti"), rs.getString("nome"),rs.getInt("pd"));
				 risultato.add(nuovo);
			 }
		 }catch (SQLException e) {
				throw new RuntimeException("Errore nel metodo getCorsiByStudente", e);
			}
		 
		 return risultato;
	 }
	

	 public boolean EsisteIscrizioneAdUnCorso(int matricola, Corso corso) { //dato uno studente ed un corso ,verifi se esise la relazione studente-corso
		 String sql= "SELECT s.matricola, c.codins, c.crediti, c.nome, c.pd "
		 		+ "FROM corso c, iscrizione i, studente s "
		 		+ "WHERE c.codins = i.codins AND  s.matricola=i.matricola AND s.matricola=? AND c.codins=? ";
		 
		 try {
			 Connection conn = ConnectDB.getConnection();
			 PreparedStatement st = conn.prepareStatement(sql);
			 
			 st.setInt(1, matricola);
			 st.setString(2, corso.getCodins());
			 ResultSet rs = st.executeQuery();
			 
			 if(rs.next()) {
				 conn.close();
				 return true;
			 }
			 else {
				 conn.close();
				 return false;
			 }
			 
		 }catch(SQLException e) {
				throw new RuntimeException("Errore nel metodo EsisteIscrizioneAdUnCorso", e);
			}
	 }
}
	 
