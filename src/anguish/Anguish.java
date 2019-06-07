package anguish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Anguish {
	public static void main(String[] args) {
		String input = "The quick brown fox didn't jump over the lazy dog";
		List<String> inputWords = Arrays.asList(clean(input).split(" "));
		List<String> inputPhones = new ArrayList<String>();
		Map<Integer, Solution> solutions = new HashMap<Integer, Solution>();
		
		// generate list of the input phones
		for (String word : inputWords) {
			inputPhones.addAll(Arrays.asList(Phones.getPhones(word)));
		}
		
		// loop through positions in the list of input phones
		for (int i = 0; i < 1; i++) { // (inputPhones.size() - 1)
			System.out.println("step " + (i + 1) + " of " + inputPhones.size());
			int remainingPhones = inputPhones.size() - i;
			Map<Integer, Integer> bestScores = new HashMap<Integer, Integer>();
			Map<Integer, List<String>> bestWords = new HashMap<Integer, List<String>>();
			for (String commonWord : Phones.COMMON_LIST) {
				if(!inputWords.contains(commonWord) && commonWord.length() <= remainingPhones) {
					String[] commonPhones = Phones.getPhones(commonWord);
					Integer score = 0;
					for (int j = 0; j < commonPhones.length; j++) {
						score += Phones.compare(commonPhones[j], inputPhones.get(i+j));
					}
					if (!bestScores.containsKey(commonPhones.length) ||
							score > bestScores.get(new Integer(commonPhones.length))) {
						bestScores.put(new Integer(commonPhones.length), score);
						bestWords.put(new Integer(commonPhones.length), new ArrayList<String>());
						bestWords.get(new Integer(commonPhones.length)).add(commonWord);
					} else if (score == bestScores.get(new Integer(commonPhones.length))) {
						bestWords.get(new Integer(commonPhones.length)).add(commonWord);
					}
				}
			}
		}
		
		// TODO track only the highest scoring combination ending at each position
		// TODO return highest scoring combination at final position
	}
	
	private static String clean(String input) {
		return input.replaceAll("[^a-zA-Z' ]", "").replaceAll(" +", " ");
	}
}
