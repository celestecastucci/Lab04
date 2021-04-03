package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
/**
 * data una matricola, restituisco il nome e cognome
 * 
 */

	public Studente getStudente(Integer matricola) {
		
		String sql="SELECT * "  //RICORDA DI METTERE * PER PRENDERE TUTTI I CAMPI INTERESSATI
				+ "FROM studente "
				+ "WHERE matricola=?";
		
		Studente s=null;
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,matricola);
			ResultSet rs = st.executeQuery();
			
			
			if(rs.next()) {
				s= new Studente(matricola, rs.getString("cognome"),rs.getString("nome") , rs.getString("CDS"));
						
			}
		
		conn.close();
	} catch (SQLException e) {
		// e.printStackTrace();
		throw new RuntimeException("Errore Db", e);
	}
		return s;
	}
	
	
	
	/**
	 * data una matricola ottengo l'elenco dei corsi a cui è iscritto
	 * @param matricola
	 * @return
	 */
public List<Corso> getCorsibyStudenteIscritto(Integer matricola){
	
	String sql="SELECT c.codins, c.crediti, c.nome, c.pd "
			+ "FROM studente s, corso c, iscrizione i "
			+ "WHERE s.matricola=i.matricola AND c.codins=i.codins AND s.matricola=?";
	List<Corso>corsiPerStudente= new LinkedList<Corso>();
	
	try {
		Connection conn = ConnectDB.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, matricola);
		ResultSet rs = st.executeQuery();
		
		while(rs.next()) {
	corsiPerStudente.add(new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd")));			
		
		}
		conn.close();
		
	} catch (SQLException e) {
		// e.printStackTrace();
		throw new RuntimeException("Errore Db", e);
	}
	
	return corsiPerStudente;
		
	}
	
}

