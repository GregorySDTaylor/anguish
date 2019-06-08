package anguish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Anguish {
	public static void main(String[] args) {
		// temporary input field
		String input = "‘If you prick us, do we not bleed? If you tickle us, do we not laugh? If you poison us, do we not die? And if you wrong us, shall we not revenge?";

		List<String> inputWords = Arrays.asList(clean(input).split(" "));
		List<String> inputPhones = new ArrayList<String>();
		Random random = new Random();

		// generate list of the input phones
		for (String word : inputWords) {
			inputPhones.addAll(Arrays.asList(Phones.getPhones(word)));
		}

		// generate array of solutions
		Solution[] solutions = new Solution[inputPhones.size()];
		for (int l = 0; l < solutions.length; l++) {
			solutions[l] = new Solution();
		}

		// loop through positions in the list of input phones
		for (int i = 0; i < (inputPhones.size() - 1); i++) {
			System.out.println("-------- step " + (i + 1) + " of " + inputPhones.size());
			int remainingPhones = inputPhones.size() - i;
			
			// set up temporary storage for potential best fit phones
			int[] bestScores = new int[remainingPhones];
			List<List<String>> bestWords = new ArrayList<List<String>>(remainingPhones);
			for (int k = 0; k < remainingPhones; k++) {
				bestScores[k] = -1;
				bestWords.add(new ArrayList<String>());
			}
			
			// loop through common words to find best fit phones
			for (String commonWord : Phones.COMMON_LIST) {
				String[] commonPhones = Phones.getPhones(commonWord);
				if (!inputWords.contains(commonWord) && commonPhones.length <= remainingPhones) {
					Integer score = 0;
					for (int j = 0; j < commonPhones.length; j++) {
						score += Phones.compare(commonPhones[j], inputPhones.get(i + j));
					}
					if (score > bestScores[commonPhones.length-1]) {
						bestScores[commonPhones.length-1] = score;
						bestWords.set(commonPhones.length-1, new ArrayList<String>());
						bestWords.get(commonPhones.length-1).add(commonWord);
					} else if (score == bestScores[commonPhones.length-1]) {
						bestWords.get(commonPhones.length-1).add(commonWord);
					}
				}
			}
			
			// check new best fit phones against current best solutions
			for (int n = 0; n < bestWords.size(); n++) {
				if (bestWords.get(n).size() > 0 ) {
					String randomBestWord = bestWords.get(n).get(random.nextInt(bestWords.get(n).size()));
					String[] randomBestPhones = Phones.getPhones(randomBestWord);
					if (i == 0) {
						solutions[n].add(randomBestWord, randomBestPhones, bestScores[n]);
					} else if (solutions[i-1].getScore() + bestScores[n] > solutions[i+n].getScore()) {
						Solution newSolution = solutions[i-1].newWithAdd(randomBestWord, randomBestPhones, bestScores[n]);
						System.out.println("replacing " + solutions[i+n].toString() + " with " + newSolution.toString());
						solutions[i+n] = newSolution;
					}					
				}
			}
			
			for (Solution solution : solutions) {
				System.out.println(solution.getSize() + ": " + solution.toString());
			}
		}
	}

	private static String clean(String input) {
		return input.replaceAll("[^a-zA-Z' ]", "").replaceAll(" +", " ").toUpperCase();
	}
}
