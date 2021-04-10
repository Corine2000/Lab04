/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.lab04;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> ComboxCorsi;

    @FXML
    private TextField txtMatricola;

    @FXML
    private TextField txtNomeStudente;

    @FXML
    private TextField txtCognomeStudente;

    @FXML
    private TextArea textResult;
    
    @FXML
    void doRicercaStudende(ActionEvent event) {
    	txtNomeStudente.clear();
    	txtCognomeStudente.clear();
    	textResult.clear();
    	
    	if(txtMatricola.getText().isEmpty()) {
    		textResult.setText("devi inserire una matricola");
    		return;
    	}
    	
    	int  matricola =0;
    	try {
    		matricola = Integer.parseInt(txtMatricola.getText());
    	}catch(NumberFormatException e) {
    		txtMatricola.clear();
    		textResult.setText("devi inserire un numero");
    		return ;
    	}
    	
    	if(model.esisteStudente(matricola)) {
	    	Studente trovato = model.GetStudente(matricola);
	    	txtNomeStudente.setText(trovato.getNome());
	    	txtCognomeStudente.setText(trovato.getCognome());
    	}else {
    		textResult.setText("la matricola inserita non corrisponde ad alcuno studente");
    	}
    }

    @FXML
    void doRicercaIscritti(ActionEvent event) { // mi da l'elenco degli studenti iscritti ad un corso scelto nel menu a tendina
     
    textResult.clear();	
    Corso c = ComboxCorsi.getValue();
     
     if(c.getCodins().equals(null)) {
     textResult.setText("errore devi scegliere un corso");
     return;
     }
     
     List<Studente> studenti = model.getStudenti(c);
     
     if(studenti.size()==0) {
    	 textResult.setText("il corso scelto non ha iscritti"); 
    	 return;
     }
     
     StringBuilder sb = new StringBuilder(); //costrusco una stringa per la mia stampa
     for(Studente s: studenti) {
    	 sb.append(String.format("%-10d", s.getMatricola()));
    	 sb.append(String.format("%-30s", s.getCognome()));
    	 sb.append(String.format("%-30s", s.getNome()));
    	 sb.append(String.format("%-10s\n", s.getCDS()));
     }
     
     textResult.setText(sb.toString());
    }
    
    
    @FXML
    void doRicercaCorsi(ActionEvent event) { //mi da l'elenco dei corsi a cui è iscritto uno studente la cui matricola è passata coma parametro
    	textResult.clear();
    	
    	if(txtMatricola.getText().isEmpty()) {
    		textResult.setText("devi inserire una matricola");
    		return;
    	}
    	
    	int  matricola =0;
    	try {
    		matricola = Integer.parseInt(txtMatricola.getText());
    	}catch(NumberFormatException e) {
    		txtMatricola.clear();
    		textResult.setText("la matricola deve contenere solo caratteri numerici");
    		return ;
    	}
    	
    	if(!model.esisteStudente(matricola)) {
    		textResult.setText("la matricola inserita non corrisponde ad alcuno studente");
             return ;	
    	}
    	
    	List<Corso> corsiTrovati = model.getCorsiByStudente(matricola);
    	if(corsiTrovati.size()==0) {
    		textResult.setText("lo studente non è iscritto ad alcun corso");
    		return;
	    	
    	}else {
    		StringBuilder sb = new StringBuilder();
    		   for(Corso c: corsiTrovati) {
    			   sb.append(String.format("%-10s", c.getCodins()));
    		    	 sb.append(String.format("%-5d", c.getCrediti()));
    		    	 sb.append(String.format("%-50s", c.getNomeCorso()));
    		    	 sb.append(String.format("%-5d\n", c.getPeriodoDidatico()));  
    		   }
    		   
    		   textResult.setText(sb.toString()); 
    	}

    }
    
    /*
     * questo metodo ha 2 casi, o verifico se uno studente è iscritto ad un corso data la sua matricola
     * o lo iscrivo ad un corso
     */
    @FXML
    void doIscrizioneCorso(ActionEvent event) {
    	textResult.clear();
    	
    	if(txtMatricola.getText().isEmpty() ) { //verifico che il campo matricola non sia vuoto
    		textResult.setText("Devi inserire una matricola");
    		return ;
    	}
       if(ComboxCorsi.getValue().equals(null)) {
    	   textResult.setText("devi scegliere un corso");
    	   return;
       }
       
       int  matricola =0;
   	try {
   		matricola = Integer.parseInt(txtMatricola.getText());
   	}catch(NumberFormatException e) {
   		txtMatricola.clear();
   		textResult.setText("la matricola deve essere un numero");
   		return ;
   	   }
   	
   	if(!model.esisteStudente(matricola)) {
   		textResult.setText(" la matricola che hai inserito non corrisponde ad uno studente");
   		return;
   	   }
   	
   	boolean esiste = model.EsisteRelazioneTraStudenteECorso(matricola, ComboxCorsi.getValue());
   	if(esiste==true) {
   		textResult.setText("lo studente è gia iscritto a quel corso");
   		return;
   	}else {
   		textResult.setText("lo studente non è iscritto a quel corso \n"); //ora devo fare l'iscrizione
   		
   		Studente s= model.GetStudente(matricola);
   		Corso c = ComboxCorsi.getValue();
   		boolean risultato = model.inscriviStudenteACorso(s, c);
   		
   		if(risultato == true) {
   			textResult.appendText("iscrizione avvenuta con successo");
   			return;
   		}else {
   			textResult.setText(" iscrizione fallita");
   			return;
   		}
   	 }
  }

 
    
    @FXML
    void doReset(ActionEvent event) {
    	textResult.clear();
    	txtMatricola.clear();
    	txtNomeStudente.clear();
    	txtCognomeStudente.clear();
    	
    }

   
    @FXML
    void initialize() {
        assert ComboxCorsi != null : "fx:id=\"ComboxCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNomeStudente != null : "fx:id=\"txtNomeStudente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognomeStudente != null : "fx:id=\"txtCognomeStudente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert textResult != null : "fx:id=\"textResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model= model;
		List<Corso> lista = model.getListaCorsi();
		lista.add(null);
		ComboxCorsi.getItems().addAll(lista);
		System.out.println(lista);
		
		textResult.setStyle("-fx-font-family : monospace"); // per avere una stampa migliorata
	}
    
}
