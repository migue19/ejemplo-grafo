package mx.intersphere.demo1.konos.ui.resources;

import mx.intersphere.demo1.konos.Demo1Box;

import mx.intersphere.demo1.konos.ui.pages.MainPage;
import io.intino.alexandria.exceptions.AlexandriaException;
import io.intino.alexandria.ui.displays.notifiers.DisplayNotifierProvider;
import io.intino.alexandria.ui.spark.pages.UiPage;

import java.util.Base64;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public class MainResource extends io.intino.alexandria.ui.spark.resources.Resource {
	private final Demo1Box box;

	public MainResource(Demo1Box box, io.intino.alexandria.ui.spark.UISparkManager manager, DisplayNotifierProvider notifierProvider) {
		super(manager, notifierProvider);
		this.box = box;
	}

	@Override
	public void execute() throws AlexandriaException {
		super.execute();
		fillDeviceParameter();
		render();
	}

	private void render() {
		String clientId = UUID.randomUUID().toString();
		UiPage page = new MainPage();
		page.session = manager.currentSession();
		page.session.browser().onRedirect(location -> manager.redirect(location));
		page.session.browser().requestUrl(manager.requestUrl());
		page.session.whenLogin(new Function<String, String>() {
			@Override
			public String apply(String baseUrl) {
				return MainResource.this.authenticate(page.session, baseUrl);
			}
		});
		page.session.whenLogout(b -> logout(page.session));
        ((MainPage)page).box = box;
		page.clientId = clientId;
		page.device = parameterValue("device");
		page.token = parameterValue("token");

		if (!page.hasPermissions()) {
			manager.redirect(page.redirectUrl());
			return;
		}

		manager.pushService().onOpen(client -> {
			if (!client.id().equals(page.clientId))
				return false;

			if (client.soul() != null) {
				((mx.intersphere.demo1.konos.ui.displays.RouteDispatcher)box.routeManager().routeDispatcher()).dispatchMain(client.soul());
				return false;
			}

			io.intino.alexandria.ui.Soul soul = page.prepareSoul(client);
			soul.onRedirect((location) -> manager.redirect(location));
			soul.addRegisterDisplayListener(display -> {
				display.inject(notifier(page.session, client, display));
				display.inject(page.session);
				display.inject(soul);
				display.inject(() -> soul);
			});
			client.soul(soul);
			client.cookies(manager.cookies());

			box.registerSoul(clientId, soul);
			soul.register(new io.intino.alexandria.ui.displays.DisplayRouter(box).id("__router__"));
			((mx.intersphere.demo1.konos.ui.displays.RouteDispatcher)box.routeManager().routeDispatcher()).dispatchMain(soul);

			return true;
		});

		manager.pushService().onClose(clientId).execute(new Consumer<io.intino.alexandria.ui.services.push.UIClient>() {
			@Override
			public void accept(io.intino.alexandria.ui.services.push.UIClient client) {
				box.soul(client.id()).ifPresent(s -> s.destroy());
				box.unRegisterSoul(client.id());
				manager.unRegister(client);
			}
		});

		manager.write(page.execute());
	}

}