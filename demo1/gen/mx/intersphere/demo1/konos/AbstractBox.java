package mx.intersphere.demo1.konos;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import java.util.HashMap;
import java.util.Map;

import io.intino.alexandria.logger.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import io.intino.alexandria.ui.services.push.PushService;
import io.intino.alexandria.ui.Soul;

public abstract class AbstractBox extends io.intino.alexandria.ui.AlexandriaUiBox {
	protected Demo1Configuration configuration;
	protected Map<String, Soul> uiSouls = new java.util.HashMap<>();
	private java.util.List<io.intino.alexandria.ui.AlexandriaUiBox.SoulsClosed> soulsClosedListeners = new java.util.ArrayList<>();
	private io.intino.alexandria.ui.services.AuthService authService;
	private PushService pushService;

	public AbstractBox(String[] args) {
		this(new Demo1Configuration(args));
	}

	public AbstractBox(Demo1Configuration configuration) {
		this.configuration = configuration;
		initJavaLogger();
	}

	public Demo1Configuration configuration() {
		return configuration;
	}

	@Override
	public io.intino.alexandria.core.Box put(Object o) {
		return this;
	}

	public abstract void beforeStart();

	public io.intino.alexandria.core.Box start() {
		initConnector();
		if (owner != null) owner.beforeStart();
		beforeStart();
		if (owner != null) owner.startServices();
		startServices();
		if (owner != null) owner.afterStart();
		afterStart();
		return this;
	}

	public abstract void afterStart();

	public abstract void beforeStop();

	public void stop() {
		if (owner != null) owner.beforeStop();
		beforeStop();
		if (owner != null) owner.stopServices();
		stopServices();
		if (owner != null) owner.afterStop();
		afterStop();
	}

	@Override
	public void stopServices() {
		io.intino.alexandria.http.AlexandriaSparkBuilder.instance().stop();
	}

	public abstract void afterStop();

	@Override
	public void startServices() {
		initUI();
		initAgenda();
		initRestServices();
		initSoapServices();
		initJmxServices();
		initTerminal();
		initMessagingServices();
		initSentinels();
		initSlackBots();
		initWorkflow();
		initCli();
	}

	public PushService pushService() {
		return pushService;
	}

	public java.util.List<Soul> souls() {
		return new java.util.ArrayList<>(uiSouls.values());
	}

	public java.util.Optional<Soul> soul(String clientId) {
		return java.util.Optional.ofNullable(uiSouls.get(clientId));
	}

	public void registerSoul(String clientId, Soul soul) {
		uiSouls.put(clientId, soul);
	}

	public void unRegisterSoul(String clientId) {
		uiSouls.remove(clientId);
		if (uiSouls.size() <= 0) notifySoulsClosed();
	}

	public void onSoulsClosed(io.intino.alexandria.ui.AlexandriaUiBox.SoulsClosed listener) {
		this.soulsClosedListeners.add(listener);
	}

	private void notifySoulsClosed() {
		soulsClosedListeners.forEach(l -> l.accept());
	}

	protected abstract io.intino.alexandria.ui.services.AuthService authService(java.net.URL authServiceUrl);

	public void setupDemo1ElementsUi() {
		if("" == null || "".isEmpty()) return;
		this.authService = null;
		io.intino.alexandria.http.AlexandriaSparkBuilder.setup(Integer.parseInt(""), "www/");
		io.intino.alexandria.http.AlexandriaSparkBuilder.setUI(true);
		io.intino.alexandria.http.AlexandriaSparkBuilder.addParameters(this.authService);
		this.pushService = new io.intino.alexandria.ui.services.push.PushService();
		io.intino.alexandria.ui.UISpark sparkInstance = (io.intino.alexandria.ui.UISpark) io.intino.alexandria.http.AlexandriaSparkBuilder.instance();
		mx.intersphere.demo1.konos.ui.Demo1ElementsService.init(sparkInstance, (Demo1Box) this, pushService, new mx.intersphere.demo1.konos.ui.displays.RouteDispatcher());

		io.intino.alexandria.ui.UiElementsService.initDisplays(sparkInstance, pushService);
	}

	private void initRestServices() {

	}

	private void initSoapServices() {

	}

	private void initMessagingServices() {

	}

	private void initJmxServices() {

	}

	private void initSlackBots() {

	}

	private void initUI() {
		setupDemo1ElementsUi();
		this.initTranslatorService();
		io.intino.alexandria.ui.UISpark sparkDemo1ElementsInstance = (io.intino.alexandria.ui.UISpark) io.intino.alexandria.http.AlexandriaSparkBuilder.instance();
		sparkDemo1ElementsInstance.start();
		Logger.info("UI demo1Elements: started at port " + "" + "!");
	}

	private void initTranslatorService() {
		translatorService = new io.intino.alexandria.ui.services.TranslatorService();

		translatorService.addAll(mx.intersphere.demo1.konos.I18n.dictionaries());
		translatorService.addAll(io.intino.alexandria.I18n.dictionaries());
	}

	protected void initConnector() {
	}

	protected void initTerminal() {
	}

	protected void initSentinels() {
	}

	protected void initWorkflow() {
	}

	protected void initAgenda() {

	}

	protected void initCli() {

	}

	protected void initJavaLogger() {
		final java.util.logging.Logger Logger = java.util.logging.Logger.getGlobal();
		final ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.INFO);
		handler.setFormatter(new io.intino.alexandria.logger.Formatter());
		Logger.setUseParentHandlers(false);
		Logger.addHandler(handler);
		io.intino.alexandria.logger4j.Logger.init();
	}

	public static java.net.URL url(String url) {
        try {
            return new java.net.URI(url).toURL();
        } catch (java.net.MalformedURLException | java.net.URISyntaxException | IllegalArgumentException e) {
            return null;
        }
    }
}