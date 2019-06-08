package anguish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
	private List<String> words;
	private List<String> phones;
	private int score;
	
	public Solution() {
		words = new ArrayList<String>();
		phones = new ArrayList<String>();
		score = 0;
	}
	
	public Solution(List<String> words, List<String> phones, int score) {
		this.words = new ArrayList<String>();
		this.phones = new ArrayList<String>();
		this.score = 0;
		this.words.addAll(words);
		this.phones.addAll(phones);
		this.score += score;
	}
	
	public Solution newWithAdd(String additionalWord, String[] additionalPhones, int additionalScore) {
		Solution newSolution = new Solution(words, phones, score);
		newSolution.add(additionalWord, additionalPhones, additionalScore);
		return newSolution;
	}
	
	public void add(String additionalWord, String[] additionalPhones, int additionalScore) {
		this.words.add(additionalWord);
		this.phones.addAll(Arrays.asList(additionalPhones));
		this.score += additionalScore;
	}
	
	public int getSize() {
		return phones.size();
	}
	
	@Override
	public String toString() {
		return "\"" + String.join(" ", words) + "\" (" + score + ")";
	}

	public int getScore() {
		return score;
	}
}
