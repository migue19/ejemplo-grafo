package mx.intersphere.demo1.konos.ui.displays;

import io.intino.alexandria.ui.Soul;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractRouteDispatcher implements io.intino.alexandria.ui.displays.DisplayRouteDispatcher {
	private static java.util.Map<String, String> patterns = new HashMap<>();

	public AbstractRouteDispatcher() {
		registerPatterns();
	}

	@Override
	public void dispatch(Soul soul, String address) {
		address = address.replaceFirst(soul.session().browser().basePath(), "");
		List<String> params = paramsOf(address);
		if (address.length() <= 1) { dispatchMain(soul); return; }
		if (address.matches(patterns.get("main"))) { dispatchMain(soul); return; }
	}

	public abstract void dispatchMain(Soul soul);

	private void registerPatterns() {
		if (patterns.size() > 0) return;
		patterns.put("main", "");
	}

	private String patternOf(String address) {
		if (address.matches(patterns.get("main"))) return patterns.get("main");
		else if (address.matches(patterns.get("main"))) return patterns.get("main");
		return null;
	}

	private List<String> paramsOf(String address) {
		return paramsOf(address, patternOf(address));
	}

	private List<String> paramsOf(String address, String pattern) {
		if (pattern == null) return java.util.Collections.emptyList();
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(address);
		if (!m.find()) return Collections.emptyList();
		List<String> result = new ArrayList<>();
		for (int i=1; i<=m.groupCount(); i++) result.add(m.group(i).split("\\?")[0]);
		return addQueryStringParams(address, result);
	}

	private List<String> addQueryStringParams(String address, List<String> result) {
		if (address.indexOf("?") == -1) return result;
		String[] parameters = address.split("\\?")[1].split("&");
		for (int i = 0; i < parameters.length; i++) {
			result.add(parameters[i].split("=")[1]);
		}
		return result;
	}

}