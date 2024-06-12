package mx.intersphere.demo1.konos.ui;
import mx.intersphere.demo1.konos.ui.displays.*;
import mx.intersphere.demo1.konos.ui.resources.*;
import mx.intersphere.demo1.konos.Demo1Box;
import mx.intersphere.demo1.konos.Demo1Configuration;

import io.intino.alexandria.ui.UISpark;
import io.intino.alexandria.ui.displays.notifiers.DisplayNotifier;
import io.intino.alexandria.ui.displays.notifiers.DisplayNotifierProvider;
import io.intino.alexandria.ui.displays.DisplayRouteDispatcher;
import io.intino.alexandria.ui.resources.AssetResourceLoader;
import io.intino.alexandria.ui.services.push.PushService;
import io.intino.alexandria.ui.spark.resources.AfterDisplayRequest;
import io.intino.alexandria.ui.spark.resources.AssetResource;
import io.intino.alexandria.ui.spark.resources.AuthenticateCallbackResource;
import io.intino.alexandria.ui.spark.resources.BeforeDisplayRequest;

import java.net.MalformedURLException;
import java.net.URL;

public class Demo1ElementsService extends io.intino.alexandria.ui.UI {

	public static void init(UISpark spark, Demo1Box box, PushService pushService, DisplayRouteDispatcher routeDispatcher) {
		Demo1Configuration configuration = (Demo1Configuration) box.configuration();
		box.routeManager(routeManager(spark, routeDispatcher));
		spark.route("/_alexandria/push").push(pushService);
		spark.route("/authenticate-callback").get(manager -> new AuthenticateCallbackResource(manager, notifierProvider()).execute());
		spark.route("/authenticate-callback/").get(manager -> new AuthenticateCallbackResource(manager, notifierProvider()).execute());
		spark.route("/asset/:name").get(manager -> new AssetResource(name -> new AssetResourceLoader(box).load(name), manager, notifierProvider()).execute());
		spark.route("/").get(manager -> new MainResource(box, manager, notifierProvider()).execute());
		initDisplays(spark, pushService);
	}

	public static void initDisplays(UISpark spark, PushService pushService) {
		initHomeTemplate(spark, pushService);
		registerNotifiers();
	}

	private static void registerNotifiers() {
		register(io.intino.alexandria.ui.displays.notifiers.TemplateNotifier.class).forDisplay(mx.intersphere.demo1.konos.ui.displays.templates.HomeTemplate.class);
	}

	private static void initHomeTemplate(UISpark spark, PushService pushService) {
		spark.route("/hometemplate/:displayId").before(manager -> new BeforeDisplayRequest(manager).execute());
		spark.route("/hometemplate/:displayId").post(manager -> new io.intino.alexandria.ui.displays.requesters.TemplateRequester(manager, notifierProvider()).execute());
		spark.route("/hometemplate/:displayId").after(manager -> new AfterDisplayRequest(manager).execute());
		pushService.register("hometemplate", new io.intino.alexandria.ui.displays.requesters.TemplatePushRequester());

	}
}