
public class ArrayAlgorithms {
	public static int checkReversed(String a, String b) {
		//All to lower case
		a = a.toLowerCase();
		b = b.toLowerCase();
		//get rid of white spaces
		a = a.replaceAll(" ", "");
		b = b.replaceAll(" ", "");
		if (a.length() == b.length()) {
			for (int i = 0; i < a.length(); i++) {
				if (a.charAt(i) == b.charAt(b.length()-1-i)) { 
					continue;
				}else {return 0;}
			}
			return 1;
		}else {return 0;}
	}
	
	public static String leaveShortest(String sentence) {
		//Turn the string into array, split by white space

		String [] arr = sentence.split(" "); 
		String newString = "";
		for (int i = 0; i< arr.length; i = i + 3) {
			String temp = arr[i];
			if (arr[i].length() > arr[i+1].length()) {
				temp = arr[i+1];
			}
			if (temp.length() > arr[i+2].length()) {
				temp = arr[i+2];
			}
			newString += (temp+" ");
		}
		return newString.substring(0, newString.length()-1);
	}
}
