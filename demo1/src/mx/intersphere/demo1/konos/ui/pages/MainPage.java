package mx.intersphere.demo1.konos.ui.pages;

import io.intino.alexandria.exceptions.*;
import java.time.*;
import java.util.*;
import mx.intersphere.demo1.konos.ui.displays.templates.*;

public class MainPage extends AbstractMainPage {

	public io.intino.alexandria.ui.Soul prepareSoul(io.intino.alexandria.ui.services.push.UIClient client) {
		return new io.intino.alexandria.ui.Soul(session) {
			@Override
			public void personify() {
				HomeTemplate component = new HomeTemplate(box);
				register(component);
				component.init();
			}
		};
	}
}