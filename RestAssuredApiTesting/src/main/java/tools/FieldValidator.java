package tools;

import java.util.List;

import model.Post;

public class FieldValidator {

	
	public static boolean isValid(List<Post> posts, int searched) 
	{
		boolean flag = true;
		if (searched <= 0){
			flag = false;
		}
	    int numCount = 0;

	    for (int i = 0;i<posts.size();i++) {
	        if (posts.get(i).getId() == searched) numCount++;
	    }
	    if (numCount > 1){
	    	flag = false;
	    }
	    return flag;
	}
	
	public static boolean isTitleValid(String text) {
		boolean flag = true;
		text = text.replace("&nbsp;"," ");
		for (int i = 0;i<text.length();i++){
			if (!(Character.isUpperCase(text.charAt(0)) && Character.isLetter(text.charAt(0)))) {
				flag = false;		
			}
			if (text.charAt(i)==' ' && i+1 <text.length()){
				if (!(Character.isLetter(text.charAt(i)) && Character.isLowerCase(text.charAt(i)) || Character.isDigit(text.charAt(i)))){
					flag = false;
				}
			}			
		}
		if (text.charAt(text.length()-1) != '.' || text.charAt(text.length()-1) != '!' || text.charAt(text.length()-1) != '?'){
			flag = false;
		}
		return flag;		
	}
	
	public static boolean isBodyValid(String text) {
		boolean flag = true;
		text = text.replace("&nbsp;"," ");
		for (int i = 0;i<text.length();i++){
			if (!(Character.isUpperCase(text.charAt(0)) && Character.isLetter(text.charAt(0)))) {
				flag = false;		
			}
			if (text.charAt(i)==' ' && i+1 <text.length()){
				if (!(Character.isLetter(text.charAt(i)) && Character.isLowerCase(text.charAt(i)) || Character.isDigit(text.charAt(i)))){
					flag = false;
				}
			}			
		}
		if (text.charAt(text.length()-1) != '.' || text.charAt(text.length()-1) != '!' || text.charAt(text.length()-1) != '?'){
			flag = false;
		}
		return flag;		
	}
	
}
