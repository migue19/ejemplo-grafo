package mx.intersphere.demo1.proteo;

import mx.intersphere.demo1.proteo.*;

public class User  extends io.intino.magritte.framework.Layer implements io.intino.magritte.framework.tags.Terminal {
	protected java.lang.String nombre;
	protected java.lang.String apellido;

	public User(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public java.lang.String nombre() {
		return nombre;
	}

	public java.lang.String apellido() {
		return apellido;
	}

	public User nombre(java.lang.String value) {
		this.nombre = value;
		return (User) this;
	}

	public User apellido(java.lang.String value) {
		this.apellido = value;
		return (User) this;
	}

	@Override
	protected java.util.Map<java.lang.String, java.util.List<?>> variables$() {
		java.util.Map<java.lang.String, java.util.List<?>> map = new java.util.LinkedHashMap<>();
		map.put("nombre", new java.util.ArrayList(java.util.Collections.singletonList(this.nombre)));
		map.put("apellido", new java.util.ArrayList(java.util.Collections.singletonList(this.apellido)));
		return map;
	}

	@Override
	protected void load$(java.lang.String name, java.util.List<?> values) {
		super.load$(name, values);
		if (name.equalsIgnoreCase("nombre")) this.nombre = io.intino.magritte.framework.loaders.StringLoader.load(values, this).get(0);
		else if (name.equalsIgnoreCase("apellido")) this.apellido = io.intino.magritte.framework.loaders.StringLoader.load(values, this).get(0);
	}

	@Override
	protected void set$(java.lang.String name, java.util.List<?> values) {
		super.set$(name, values);
		if (name.equalsIgnoreCase("nombre")) this.nombre = (java.lang.String) values.get(0);
		else if (name.equalsIgnoreCase("apellido")) this.apellido = (java.lang.String) values.get(0);
	}

	public mx.intersphere.demo1.proteo.Demo1Graph graph() {
		return (mx.intersphere.demo1.proteo.Demo1Graph) core$().graph().as(mx.intersphere.demo1.proteo.Demo1Graph.class);
	}
}