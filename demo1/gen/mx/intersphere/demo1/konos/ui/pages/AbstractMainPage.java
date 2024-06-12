package mx.intersphere.demo1.konos.ui.pages;

import mx.intersphere.demo1.konos.Demo1Box;
import io.intino.alexandria.exceptions.*;
import java.util.*;

public abstract class AbstractMainPage extends io.intino.alexandria.ui.spark.pages.WebPage {
	public Demo1Box box;

	public AbstractMainPage() { super("demo1-elements"); }

	public String execute() {
		return super.template("homeTemplate");
	}

	@Override
	protected String title() {
		return "";
	}

	@Override
	protected java.net.URL favicon() {
		return this.getClass().getResource("");
	}
}