package mx.intersphere.demo1.konos.ui.displays.templates;

import io.intino.alexandria.exceptions.*;
import mx.intersphere.demo1.konos.*;

import mx.intersphere.demo1.konos.Demo1Box;
import mx.intersphere.demo1.konos.ui.displays.templates.AbstractHomeTemplate;

public class HomeTemplate extends AbstractHomeTemplate<Demo1Box> {

	public HomeTemplate(Demo1Box box) {
		super(box);
	}

	@Override
	public void init() {
		super.init();
		System.out.println(box().demo1Graph.userList());
	}
}