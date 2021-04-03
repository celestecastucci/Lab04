/**
 /**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.lab04;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class FXMLController {

	
	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCorsi"
    private ComboBox<Corso> boxCorsi; // Value injected by FXMLLoader


    @FXML // fx:id="btnCercaIscrittiCorso"
    private Button btnCercaIscrittiCorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtInserisci"
    private TextField txtInserisci; // Value injected by FXMLLoader

    @FXML // fx:id="btnCheck"
    private CheckBox btnCheck; // Value injected by FXMLLoader

    @FXML // fx:id="txtNome"
    private TextField txtNome; // Value injected by FXMLLoader

    @FXML // fx:id="txtCognome"
    private TextField txtCognome; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaCorsi"
    private Button btnCercaCorsi; // Value injected by FXMLLoader

    @FXML // fx:id="btnIscrivi"
    private Button btnIscrivi; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader

    @FXML // fx:id="btnReset"
    private Button btnReset; // Value injected by FXMLLoader

    /**
     * data una matricola restituisce l'elenco di corsi a cui è iscritto lo studente
     * @param event
     */
    @FXML
    void doCercaCorsi(ActionEvent event) {
    	
    	txtRisultato.clear();
    
    	String matricolaStringa= txtInserisci.getText();
    	Integer matricola;
    	
    	try {
    		
    	matricola=Integer.parseInt(matricolaStringa);
    	Studente studente=this.model.getStudente(matricola);
    	
    	if(matricolaStringa.length()!=6) {
    		this.txtRisultato.setText("Inserisci la matricola giusta!");
    		return;
    	}
    	if(studente==null) {
    		txtRisultato.setText("Non esiste uno studente con la matricola "+matricola);
    		return;
    	}
    		//metto qui la lista perchè ho inizializzato la matricola, fuori dal try non sa che valore prendere
    		//creo lo string builder per visualizzare meglio
    	
    	List<Corso>listaCorsiPerStudente= this.model.getCorsibyStudenteIscritto(matricola);
    	Collections.sort(listaCorsiPerStudente);
    	
    		StringBuilder sb = new StringBuilder();

    		for (Corso corso: listaCorsiPerStudente) {

    			sb.append(String.format("%-10s ", corso.getCodins()));
    			sb.append(String.format("%-12d ", corso.getCrediti()));
    			sb.append(String.format("%-50s ", corso.getNome()));
    			sb.append(String.format("%-10d ", corso.getPd()));
    			sb.append("\n");
    		}

        	txtRisultato.setText(sb.toString());
    		
    	} catch(NumberFormatException ne) {
    		txtRisultato.setText("ERRORE: La matricola deve essere un intero");
    		return;
    		
    	} catch(NullPointerException pe) {
    		txtRisultato.setText("ERRORE: Devi inserire un valore");
    		return;
    		
    	
    } catch (RuntimeException e) {
		txtRisultato.setText("ERRORE DI CONNESSIONE AL DATABASE!");
	return;
    }


    }

    /**
     * selezionato un corso visualizzare tutti gli studenti iscritti 
     * @param event
     */
    @FXML
    void doCercaIscrittiCorso(ActionEvent event) {
       
    	txtRisultato.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	
    	List<Corso>elencocorsi= this.model.getTuttiICorsi();
    	Collections.sort(elencocorsi);
    	
    	
    	
     try {
	    Corso corso= boxCorsi.getValue();
    	if(corso==null) {
    		txtRisultato.setText("Selezionare un corso");
    		return;
    	}
    	List<Studente>listaStudentiIscritti= this.model.getStudentiIscrittiAlCorso(corso);
    	Collections.sort(listaStudentiIscritti);
    	
    	
    	if(listaStudentiIscritti.size()==0) {
    		txtRisultato.setText("Nessuno studente appartiene al corso " +corso);
    		return;
    	}
    	StringBuilder sb = new StringBuilder();

		for (Studente studente : listaStudentiIscritti) {
			

			sb.append(String.format("%-10d ", studente.getMatricola()));
			sb.append(String.format("%-20s ", studente.getCognome()));
			sb.append(String.format("%-20s ", studente.getNome()));
			sb.append(String.format("%-10s ", studente.getCDS()));
			sb.append("\n");
		}

    	txtRisultato.setText(sb.toString());
    	
    } catch (RuntimeException e) {
		txtRisultato.setText("ERRORE DI CONNESSIONE AL DATABASE!");
	}


    }

    @FXML
    void doCheck(ActionEvent event) {
    	
    	txtNome.clear();
    	txtCognome.clear(); 
    	txtInserisci.clear();
    	
    	String matricolaStringa= txtInserisci.getText();
    	Integer matricola;
    	Studente studente=null;
    	
    	try {
    		matricola=Integer.parseInt(matricolaStringa);
    		studente=this.model.getStudente(matricola);
    		
    		if(matricolaStringa.length()!=6) {
        		this.txtRisultato.setText("ERRORE: la matricola deve essere lunga 6 numeri!");
        		return;
    		}
    		if(studente==null) {
    			txtRisultato.setText("ERRORE: Nessuno studente ha questa matricola");
    			return;
    		}
    		
    	} catch(NumberFormatException ne) {
    		txtRisultato.setText("ERRORE: La matricola deve essere un intero");
    		return;
    		
    	} catch(NullPointerException pe) {
    		txtRisultato.setText("ERRORE: Devi inserire un valore");
    		return;
    		
    	}
    	
    
    	txtNome.setText(studente.getNome());
    	txtCognome.setText(studente.getCognome());
    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	
    	txtRisultato.clear();
    	String matricolaStringa= txtInserisci.getText();
    	Integer matricola;
    	
    	try {
    		
    		Corso corso=boxCorsi.getValue();
    		if(corso==null) {
    			txtRisultato.setText("Selezionare un corso");
        		return;
        	}
    		
        		matricola=Integer.parseInt(matricolaStringa);
        	     Studente studente=this.model.getStudente(matricola);
        		
        		if(matricolaStringa.length()!=6) {
            		this.txtRisultato.setText("Inserisci la matricola giusta!");
            		return;
        		}
        		if(studente==null) {
        			txtRisultato.setText("ERRORE: Nessuno studente ha questa matricola");
        			return;
        		}
        		// faccio comparire anche nome e cognome corrispondenti alla matricola
        		
        		txtNome.setText(studente.getNome());
    			txtCognome.setText(studente.getCognome());
        		
        		//se sono qui posso verificare se lo studente è già iscritto o no
            	if(this.model.isIscritto(studente, corso)==true){
            		txtRisultato.setText("Lo studente "+studente.getMatricola()+" è gia iscritto al corso "+corso.getNome());
            		
            	} else {
            	   this.model.inscriviStudenteACorso(studente, corso);
            		txtRisultato.setText("Lo studente "+studente.getMatricola()+" è stato iscritto con successo a "+corso.getNome());            	}
        		
        	} catch(NumberFormatException ne) {
        		txtRisultato.setText("ERRORE: La matricola deve essere un intero");
        		return;
        		
        	} catch(NullPointerException pe) {
        		txtRisultato.setText("ERRORE: Devi inserire un valore");
        		return;
        		
        	}
    		
    	}

    @FXML
    void doReset(ActionEvent event) {
    	//HO RESO TXTRISULTATO NON EDITABILE 
    boxCorsi.getSelectionModel().clearSelection();
    txtRisultato.clear();
    txtNome.clear();
    txtCognome.clear();
    txtInserisci.clear();
    
    

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	assert boxCorsi != null : "fx:id=\"boxCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscrittiCorso != null : "fx:id=\"btnCercaIscrittiCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInserisci != null : "fx:id=\"txtInserisci\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCheck != null : "fx:id=\"btnCheck\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model=model;
   
    	//aggiungo tutti i corsi al combo box
    	this.boxCorsi.getItems().addAll(model.getTuttiICorsi());
    	//setto il carattere per la textArea da usare
    	txtRisultato.setStyle("-fx-font-family: monospace");
    }
}
