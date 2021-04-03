package it.polito.tdp.lab04.model;

import java.util.Collections;
import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;

import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	CorsoDAO corsodao;
	StudenteDAO studentedao;
	public Model() {
		corsodao= new CorsoDAO();
		studentedao= new StudenteDAO();
	}
	

	public List<Corso> getTuttiICorsi() {
	
		return corsodao.getTuttiICorsi();
		

	}
	
	public Studente getStudente(Integer matricola) {
		return studentedao.getStudente(matricola);
		
	}
	
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		return corsodao.getStudentiIscrittiAlCorso(corso);
	}
	
	public List<Corso> getCorsibyStudenteIscritto(Integer matricola){
		return studentedao.getCorsibyStudenteIscritto(matricola);
	}
	
	public boolean isIscritto(Studente studente, Corso corso) {
		return corsodao.isIscritto(studente, corso);
	}
	
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		return corsodao.inscriviStudenteACorso(studente, corso);
	}
}
