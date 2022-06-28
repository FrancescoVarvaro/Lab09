
package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;


public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML
    private ComboBox<Country> cmbNazioni;


    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	txtResult.clear();
    	int anno;
    	try {
    		anno = Integer.parseInt(txtAnno.getText());
    		if ((anno < 1816) || (anno > 2016)) {
				txtResult.setText("Inserire un anno nell'intervallo 1816 - 2016");
				return;
			}
    	}catch(NumberFormatException e) {
    		txtResult.appendText("Inserire un anno nell'intervallo 1816 - 2016");
    		return;
    	}
    	model.creaGrafo(anno);
    	Map<Country, Integer> result = model.getCountries();
    	for(Country c : result.keySet()) {
    		txtResult.appendText(String.format("nazione: %s; stati confinanti: %d\n",c , result.get(c)));
    	}
    	int componentiConnesse;
    	componentiConnesse = model.getNumberOfConnectedComponents();
    	txtResult.appendText(String.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents()));
    	
    	for(Country c : model.getCountries().keySet()) {
    		cmbNazioni.getItems().add(c);
    	}
    }
    
    @FXML
    void handleRaggiungibile(ActionEvent event) {
    	txtResult.clear();

		if (cmbNazioni.getItems().isEmpty()) {
			txtResult.setText("Graph is empty. Create a graph, or select another year.");
		}
		
    	Country verticeSelezionato = cmbNazioni.getValue();
    	List<Country> statiRaggiungibili= model.verticiRaggiungibili(verticeSelezionato);
    	for(Country c : statiRaggiungibili) {
    		txtResult.appendText(c.toString()+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	assert cmbNazioni != null : "fx:id=\"cmbNazioni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    }
}
