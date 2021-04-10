package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	public StudenteDAO() {
		
	}
	
	public Studente getStudente(Integer matricola) {
		Studente stemp;
		String sql = "SELECT * "
				+ "FROM studente "
				+ "WHERE matricola=?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
            
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			rs.first();
			
			stemp = new Studente(matricola,rs.getString("cognome"),rs.getString("nome"),rs.getString("CDS"));
			
		}catch(SQLException e) {
			throw new RuntimeException("Errore nel metodo getStudente", e);
		}
		
		return stemp;
	}

	public boolean esisteStudente(int matricola) {
		String sql = "SELECT * "
				+ "FROM studente "
				+ "WHERE matricola=?";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
            
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
            
			if(rs.next()) {
				conn.close();
				return true;
				
			}else {
				conn.close();
				return false;
			}
			
		}catch(SQLException e) {
			throw new RuntimeException("Errore nel metodo getStudente", e);
		}
		
	
	}

}
