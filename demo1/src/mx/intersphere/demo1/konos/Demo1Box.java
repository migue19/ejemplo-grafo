package mx.intersphere.demo1.konos;

import io.intino.magritte.framework.Graph;
import io.intino.magritte.framework.stores.VolatileStore;
import io.intino.magritte.io.model.Stash;
import mx.intersphere.demo1.konos.bd.UsersBD;
import mx.intersphere.demo1.proteo.AbstractGraph;
import mx.intersphere.demo1.proteo.Demo1Graph;

import java.util.List;
import java.util.stream.Stream;

public class Demo1Box extends AbstractBox {
	public Demo1Graph demo1Graph;
	private UsersBD usersBD;

	public Demo1Box(String[] args) {
		this(new Demo1Configuration(args));
	}

	public Demo1Box(Demo1Configuration configuration) {
		super(configuration);
	}

	@Override
	public io.intino.alexandria.core.Box put(Object o) {
		super.put(o);
		return this;
	}
	// MARK:
	public void beforeStart() {
		initDatamart();
		//Cargar graph
		// usersBD = new UsersBD(demo1Graph);
		// usersBD.loadData();
	}

	private void initDatamart() {
		demo1Graph = newGraph();
		for (int i =0; i<10; i++){
			demo1Graph.create(demo1Graph.userStash("userId-"+i),"1").user("nombre"+i,"apellido"+i);
		}
	}

	private Demo1Graph newGraph() {
		return new Graph(new Demo1Store().setWhiteList(List.of("demo1"))).loadStashes(false, "demo1").as(Demo1Graph.class);
	}

	private class Demo1Store extends VolatileStore {
		//public Stream<String> keys() {
		//			return stashes.keySet().stream().filter(k -> k.startsWith("k/"));
		//		}
		@Override
		public Stash stashFrom(String path) {
			return new Stash();
		}

		public Stream<String> users(){
			return stashes.keySet().stream().filter(k->k.startsWith("u/"));
		}
	}

	public void afterStart() {

	}

	public void beforeStop() {

	}

	public void afterStop() {

	}

	protected io.intino.alexandria.ui.services.AuthService authService(java.net.URL authServiceUrl) {
		//TODO add your authService
		return null;
	}


}