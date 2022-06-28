package it.polito.tdp.borders.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.management.loading.PrivateClassLoader;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private SimpleGraph<Country, DefaultEdge> grafo;
	private Map<Integer, Country> idMap;
	private BordersDAO dao;
	
	private List<Country> stati;
	
	
	public Model() {
		dao = new BordersDAO();
		idMap = new HashMap<Integer, Country>();
		for(Country c : dao.loadAllCountries())
			idMap.put(c.getId(), c);
	}

	public void creaGrafo(int anno) {
		
		grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		// aggiungo vertici
		Graphs.addAllVertices(this.grafo, dao.getCountryConnessi(anno));
		// aggiungo archi
		for(Border b : dao.getCountryPairs(anno)) {
//			this.grafo.addEdge(idMap.get(b.getIdStato1()), idMap.get(b.getIdStato2())); 
			Graphs.addEdgeWithVertices(this.grafo, idMap.get(b.getIdStato1()), idMap.get(b.getIdStato2()));
		}
		
		System.out.println("grafo creato!");
		System.out.println("N. Vertici: "+this.grafo.vertexSet().size());
		System.out.println("N. Archi: "+this.grafo.edgeSet().size());
	}
	
	public Map<Country, Integer> getCountries(){
		
		Map<Country, Integer> result = new HashMap<Country, Integer>();
		
		for(Country c : this.grafo.vertexSet()) {
			result.put(c, this.grafo.degreeOf(c));
		}
		return result;
	}
	
	public int getNumberOfConnectedComponents() {
		if (grafo == null) {
			throw new RuntimeException("Grafo non esistente");
		}

		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<Country, DefaultEdge>(grafo);
		return ci.connectedSets().size();
	}
	
	public List<Country> verticiRaggiungibili(Country partenza){
		List<Country> statiRaggiungibili = new LinkedList<>();
		GraphIterator<Country, DefaultEdge> it = new BreadthFirstIterator<>(this.grafo, partenza);
		while(it.hasNext()) {
			statiRaggiungibili.add(it.next());
		}
		return statiRaggiungibili;
	}
	
}
