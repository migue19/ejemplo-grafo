package mx.intersphere.demo1.konos.ui.displays.templates;

import io.intino.alexandria.core.Box;
import io.intino.alexandria.exceptions.*;
import io.intino.alexandria.ui.displays.components.*;
import mx.intersphere.demo1.konos.ui.*;

import mx.intersphere.demo1.konos.Demo1Box;

import mx.intersphere.demo1.konos.ui.displays.templates.*;








import io.intino.alexandria.ui.displays.notifiers.TemplateNotifier;

public abstract class AbstractHomeTemplate<B extends Box> extends io.intino.alexandria.ui.displays.components.Template<TemplateNotifier, java.lang.Void, B> {
	public _9_1_0696137150 _9_1_0696137150;

	public AbstractHomeTemplate(B box) {
		super(box);
		id("homeTemplate");
	}

	@Override
	public void init() {
		super.init();
		if (_9_1_0696137150 == null) _9_1_0696137150 = register(new _9_1_0696137150(box()).<_9_1_0696137150>id("a_2012307258").owner(AbstractHomeTemplate.this));
	}

	@Override
	public void remove() {
		super.remove();
		if (_9_1_0696137150 != null) _9_1_0696137150.unregister();
	}

	public class _9_1_0696137150 extends io.intino.alexandria.ui.displays.components.Text<io.intino.alexandria.ui.displays.notifiers.TextNotifier, B>  {

		public _9_1_0696137150(B box) {
			super(box);
			_value("Hola");
		}

		@Override
		public void init() {
			super.init();
		}

		@Override
		public void unregister() {
			super.unregister();
		}
	}
}