package utility;

public class WordChecker {
	public static boolean checkAlphanum(String word) {
		int a = 0;
		int n = 0;
		for (Character c : word.toCharArray()) {
			if (Character.isAlphabetic(c)) {
				a++;
			} else if (Character.isDigit(c)) {
				n++;
			}
		}

		return (a > 0) && (n > 0) && ((a + n) == word.length());
	}
	
	public static boolean checkAlphabet(String word){
		for (Character c : word.toCharArray()) {
			if (!Character.isAlphabetic(c)) {
				return false;
			}
		}
		
		return true;
	}
}
