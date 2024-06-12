package mx.intersphere.demo1.proteo;

import io.intino.magritte.framework.Graph;

public class Demo1Graph extends mx.intersphere.demo1.proteo.AbstractGraph {

	public Demo1Graph(Graph graph) {
		super(graph);
	}

	public Demo1Graph(io.intino.magritte.framework.Graph graph, Demo1Graph wrapper) {
	    super(graph, wrapper);
	}


	public static Demo1Graph load(io.intino.magritte.io.model.Stash... startingModel) {
		return new Graph().loadLanguage("Demo1", _language()).loadStashes(startingModel).as(Demo1Graph.class);
	}

	public static Demo1Graph load(io.intino.magritte.framework.Store store, io.intino.magritte.io.model.Stash... startingModel) {
		return new Graph(store).loadLanguage("Demo1", _language()).loadStashes(startingModel).as(Demo1Graph.class);
	}

	public static Demo1Graph load(String... startingModel) {
		return new Graph().loadLanguage("Demo1", _language()).loadStashes(startingModel).as(Demo1Graph.class);
	}

	public static Demo1Graph load(io.intino.magritte.framework.Store store, String... startingModel) {
		return new Graph(store).loadLanguage("Demo1", _language()).loadStashes(startingModel).as(Demo1Graph.class);
	}

	public String userStash(String s) {
		return "u/" + s;
	}
}