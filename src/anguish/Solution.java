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
	
	public void add(String additionalWord, String[] additionalPhones, int additionalScore) {
		words.add(additionalWord);
		phones.addAll(Arrays.asList(additionalPhones));
		score += additionalScore;
	}
	
	public int getSize() {
		return phones.size();
	}
	
	@Override
	public String toString() {
		return "TODO";
	}

}
