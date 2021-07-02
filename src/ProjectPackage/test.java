package ProjectPackage;

import java.util.Arrays;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String Button_name = "Button_13-14";
		String X = Button_name.replaceAll("[A-Za-z_]","");
		String[] temp = X.split("");
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < temp.length; i++) {
	         sb.append(temp[i]);
	    }
	    X = sb.toString();
	    temp = X.split("");
	    sb = new StringBuffer();
	    for(int i = 0; i < temp.length; i++) {
	         sb.append(temp[i]);
	    }
	    X = sb.toString();
	    String Y = X.substring(X.indexOf('-')+1, X.length());
	    X = X.substring(0, X.indexOf('-'));

				
	}
}
