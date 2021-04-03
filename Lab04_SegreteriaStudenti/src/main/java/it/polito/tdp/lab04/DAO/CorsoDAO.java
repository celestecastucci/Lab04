package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * "+  "FROM corso "+ "ORDER BY nome ASC ";

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

				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				Corso c= new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
			corsi.add(c);
		
			}

			conn.close();
			Collections.sort(corsi);
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
		// TODO
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		
		String sql="SELECT s.matricola, s.nome, s.cognome, s.CDS "
				+ "FROM iscrizione i, corso c, studente s "
				+ "WHERE i.codins=c.codins AND i.matricola=s.matricola AND c.codins=?";
		List<Studente>studentiIscritti= new LinkedList<Studente>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
		studentiIscritti.add(new Studente(rs.getInt("matricola"), rs.getString("cognome"),rs.getString("nome") , rs.getString("CDS")));
			}
			conn.close();
			
	} catch (SQLException e) {
		// e.printStackTrace();
		throw new RuntimeException("Errore Db", e);
	}
		return studentiIscritti;
		
	}
	
	/**
	 * booleano per verificare se lo studente è già iscritto
	 * se isIscritto= false --> lo iscrivo
	 * se isIscritto = true --> già presente
	 * @param studente
	 * @param corso
	 * @return
	 */

	public boolean isIscritto(Studente studente, Corso corso) {
		
		boolean isIscritto=false;
		
		String sql="SELECT s.nome,s.cognome, s.matricola "
				+ "FROM studente s, iscrizione i, corso c "
				+ "WHERE s.matricola=i.matricola AND c.codins=i.codins AND c.codins=? AND s.matricola=?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			st.setInt(2, studente.getMatricola());
			ResultSet rs = st.executeQuery();
			if(rs.first()) {
				
				
				isIscritto=true;
			} else {
				
				isIscritto= false;
				
			}
			conn.close();
		}
		
		 catch (SQLException e) {
	// e.printStackTrace();
	throw new RuntimeException("Errore Db", e);
}
	return isIscritto;
	} 
	
	
	
	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		// TODO
		// ritorna true se l'iscrizione e' avvenuta con successo
		boolean iscrizioneCorretta=false;
		
		if(this.isIscritto(studente, corso)==false) {
			
		String sql="INSERT INTO iscrizione(codins,matricola) VALUES(?,?)";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			st.setInt(2, studente.getMatricola());
			int rs = st.executeUpdate();
			
			if(rs==1) {
			iscrizioneCorretta=true;
		} conn.close();
		}
		catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		}
		return iscrizioneCorretta;
	
	}

}
