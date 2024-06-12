package mx.intersphere.demo1.konos;

import io.intino.alexandria.ui.services.translator.Dictionary;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class I18n {
	private static Map<String, Dictionary> dictionaries = new HashMap<>();

	public static String translate(String word, String language) {
		language = dictionaries.containsKey(language) ? language : "en";
		Dictionary dictionary = dictionaries.get(language);
		return dictionary != null && dictionary.containsKey(word) ? dictionary.get(word) : word;
	}

	public static List<Dictionary> dictionaries() {
		return new ArrayList<>(dictionaries.values());
	}
}