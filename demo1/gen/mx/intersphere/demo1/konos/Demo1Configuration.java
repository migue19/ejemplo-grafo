package mx.intersphere.demo1.konos;

import java.util.Map;
import java.util.HashMap;
import java.io.File;

public class Demo1Configuration extends io.intino.alexandria.core.BoxConfiguration {

	public Demo1Configuration(String[] args) {
		super(args);
	}



	public java.io.File home() {
		return new java.io.File(args.getOrDefault("home", System.getProperty("user.home")));
	}
}