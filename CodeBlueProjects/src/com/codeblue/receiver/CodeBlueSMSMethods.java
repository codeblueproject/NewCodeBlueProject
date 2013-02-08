package com.codeblue.receiver;

import java.util.ArrayList;

public class CodeBlueSMSMethods {

	private final static String CODEBLUE_CODE = "%$CODE^&BLUE$%";

	public boolean isFromCodeBlue(String message) {
		boolean result = false;

		String[] code = message.split(",");

		if (code[0].equals(CODEBLUE_CODE)) {
			result = true;
		}

		return result;
	}

	public ArrayList<Double> getSenderLocation(String message) {
		ArrayList<Double> location = new ArrayList<Double>();

		String[] split = message.split(",");

		// add the latitude to the array
		location.add(Double.parseDouble(split[1]));
		// add the latitude to the array
		location.add(Double.parseDouble(split[2]));

		return location;
	}

}
