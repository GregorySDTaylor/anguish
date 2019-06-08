package anguish;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Phones {
	private static final Path COMPLETE_PATH = Paths.get("cmudict-0.7b.txt");
	private static final Path COMMON_PATH = Paths.get("common-words.txt");
	private static final Path PHONES_PATH = Paths.get("phones.txt");
	public static final Map<String, String[]> COMPLETE_DICTIONARY = buildCompleteDictionary();
	public static List<String> COMMON_LIST = new ArrayList<String>();
	public static final Map<String, String[]> COMMON_DICTIONARY = buildCommonDictionary();
	public static final Map<String, String> PHONES = buildPhones();
	
	private static Map<String, String[]> buildCommonDictionary() {
		Map<String, String[]> dictionary = new HashMap<String, String[]>();
		BufferedReader reader;
		try {
			reader = Files.newBufferedReader(COMMON_PATH);
			reader.lines().forEach(line -> {
				String upperLine = line.trim().toUpperCase();
				if (COMPLETE_DICTIONARY.containsKey(upperLine)) {
					COMMON_LIST.add(upperLine);
					dictionary.put(upperLine, COMPLETE_DICTIONARY.get(upperLine));
				}
			});
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dictionary;
	}

	private static Map<String, String> buildPhones() {
		Map<String, String> dictionary = new HashMap<String, String>();
		BufferedReader reader;
		try {
			reader = Files.newBufferedReader(PHONES_PATH);
			reader.lines().forEach(line -> {
				String[] words = line.split("\t");
				dictionary.put(words[0], words[1]);
			});
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dictionary;
	}

	private static Map<String, String[]> buildCompleteDictionary() {
		Map<String, String[]> dictionary = new HashMap<String, String[]>();
		BufferedReader reader;
		try {
			reader = Files.newBufferedReader(COMPLETE_PATH);
			reader.lines().forEach(line -> {
				if (!line.startsWith(";")) {
					String[] words = line.split(" ");
					dictionary.put(words[0].toUpperCase(), Arrays.copyOfRange(words, 2, words.length));
				}
			});
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dictionary;
	}
	
	public static String[] getPhones(String word) throws IllegalArgumentException {
		String upperWord = word.toUpperCase();
		if (COMPLETE_DICTIONARY.containsKey(upperWord)) {
			return COMPLETE_DICTIONARY.get(upperWord);
		} else {
			throw new IllegalArgumentException(word + " not found in dictionary");
		}
	}

	public static int compare(String phone1, String phone2) {
		StringBuilder phone1Letters = new StringBuilder(2);
		StringBuilder phone1Numbers = new StringBuilder(1);
		StringBuilder phone2Letters = new StringBuilder(2);
		StringBuilder phone2Numbers = new StringBuilder(1);
		int score = 0;
		for (char c : phone1.toCharArray()) {
			if (c > 64) {
				phone1Letters.append(c);
			} else {
				phone1Numbers.append(c);
			}
		}
		for (char c : phone2.toCharArray()) {
			if (c > 64) {
				phone2Letters.append(c);
			} else {
				phone2Numbers.append(c);
			}
		}
		String phone1LetterString = phone1Letters.toString();
		String phone1NumberString = phone1Numbers.toString();
		String phone2LetterString = phone2Letters.toString();
		String phone2NumberString = phone2Numbers.toString();
		if (phone1LetterString.equals(phone2LetterString)) {
			score++;
		}
		if (phone1NumberString.equals(phone2NumberString)) {
			score++;
		}
		if (PHONES.get(phone1LetterString).equals(PHONES.get(phone2LetterString))) {
			score++;
		}
		return score;
	}
}
