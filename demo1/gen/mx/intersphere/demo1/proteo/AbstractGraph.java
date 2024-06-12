package mx.intersphere.demo1.proteo;

import io.intino.magritte.framework.Graph;

public class AbstractGraph extends io.intino.magritte.framework.GraphWrapper {
	protected io.intino.magritte.framework.Graph graph;
	private java.util.List<mx.intersphere.demo1.proteo.User> userList = new java.util.ArrayList<>();

	private java.util.Map<String, Indexer> _index = _fillIndex();

	public AbstractGraph(io.intino.magritte.framework.Graph graph) {
		this.graph = graph;
		this.graph.i18n().register("demo1");
	}

	public AbstractGraph(io.intino.magritte.framework.Graph graph, AbstractGraph wrapper) {
		this.graph = graph;
		this.graph.i18n().register("demo1");
		this.userList = new java.util.ArrayList<>(wrapper.userList);
	}

	public <T extends io.intino.magritte.framework.GraphWrapper> T a$(Class<T> t) {
		return this.core$().as(t);
	}

    @Override
	public void update() {
		this._index.values().forEach(v -> v.clear());
		graph.rootList().forEach(r -> addNode$(r));
	}

	@Override
	protected void addNode$(io.intino.magritte.framework.Node node) {
		for (io.intino.magritte.framework.Concept c : node.conceptList()) if (this._index.containsKey(c.id())) this._index.get(c.id()).add(node);
		if (this._index.containsKey(node.id())) this._index.get(node.id()).add(node);
	}

	@Override
	protected void removeNode$(io.intino.magritte.framework.Node node) {
		for (io.intino.magritte.framework.Concept c : node.conceptList()) if (this._index.containsKey(c.id())) this._index.get(c.id()).remove(node);
		if (this._index.containsKey(node.id())) this._index.get(node.id()).remove(node);
	}

	public java.net.URL resourceAsMessage$(String language, String key) {
		return graph.loadResource(graph.i18n().message(language, key));
	}

	public java.util.List<mx.intersphere.demo1.proteo.User> userList() {
		return userList;
	}

	public java.util.stream.Stream<mx.intersphere.demo1.proteo.User> userList(java.util.function.Predicate<mx.intersphere.demo1.proteo.User> filter) {
		return userList.stream().filter(filter);
	}

	public mx.intersphere.demo1.proteo.User user(int index) {
		return userList.get(index);
	}

	public io.intino.magritte.framework.Graph core$() {
		return graph;
	}

	public io.intino.magritte.framework.utils.I18n i18n$() {
		return graph.i18n();
	}

	public Create create() {
		return new Create("Misc", null);
	}

	public Create create(String stash) {
		return new Create(stash, null);
	}

	public Create create(String stash, String name) {
		return new Create(stash, name);
	}

	public Clear clear() {
		return new Clear();
	}

	public class Create {
		private final String stash;
		private final String name;

		public Create(String stash, String name) {
			this.stash = stash;
			this.name = name;
		}

		public mx.intersphere.demo1.proteo.User user(java.lang.String nombre, java.lang.String apellido) {
			mx.intersphere.demo1.proteo.User newElement = AbstractGraph.this.graph.createRoot(mx.intersphere.demo1.proteo.User.class, stash, this.name).a$(mx.intersphere.demo1.proteo.User.class);
			newElement.core$().set(newElement, "nombre", java.util.Collections.singletonList(nombre));
			newElement.core$().set(newElement, "apellido", java.util.Collections.singletonList(apellido));
			return newElement;
		}
	}

	public class Clear {
	    public void user(java.util.function.Predicate<mx.intersphere.demo1.proteo.User> filter) {
	    	new java.util.ArrayList<>(AbstractGraph.this.userList()).stream().filter(filter).forEach(io.intino.magritte.framework.Layer::delete$);
	    }
	}


	private java.util.HashMap<String, Indexer> _fillIndex() {
		java.util.HashMap<String, Indexer> map = new java.util.HashMap<>();
		map.put("User", new Indexer(node -> userList.add(node.as(mx.intersphere.demo1.proteo.User.class)), node -> userList.remove(node.as(mx.intersphere.demo1.proteo.User.class)), () -> userList.clear()));
		return map;
	}

	public static io.intino.magritte.io.model.Stash[] _language() {
		return new io.intino.magritte.io.model.Stash[]{stash()};
	}

	private static io.intino.magritte.io.model.Stash stash() {
		String content = stash0();
		return io.intino.magritte.io.StashDeserializer.stashFrom(java.util.Base64.getDecoder().decode(content));
	}

	private static String stash0() {
		return "gAEAamF2YS51dGlsLkFycmF5TGlz9IIBAWlvLmludGluby5tYWdyaXR0ZS5pby5tb2RlbC5Db25jZXD0AG14LmludGVyc3BoZXJlLmRlbW8xLnByb3Rlby5Vc2XyAQABAAABAFVzZfIBAAEBAAGAAQACQ29uY2Vw9AEAAQEAggECaW8uaW50aW5vLm1hZ3JpdHRlLmlvLm1vZGVsLkNvbmNlcHQkQ29udGVu9AD+////DwBVc2XyUHJvdGXvAQABTW9kZWwuc3Rhc+gBAAGA";
	}

	public static class Indexer {
		Add add;
		Remove remove;
		IndexClear clear;

		public Indexer(Add add, Remove remove, IndexClear clear) {
			this.add = add;
			this.remove = remove;
			this.clear = clear;
		}

		void add(io.intino.magritte.framework.Node node) {
			this.add.add(node);
		}

		void remove(io.intino.magritte.framework.Node node) {
			this.remove.remove(node);
		}

		void clear() {
			this.clear.clear();
		}
	}

	interface Add {
		void add(io.intino.magritte.framework.Node node);
	}

	interface Remove {
		void remove(io.intino.magritte.framework.Node node);
	}

	interface IndexClear {
		void clear();
	}
}