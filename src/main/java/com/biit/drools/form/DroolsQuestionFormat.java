package com.biit.drools.form;

public enum DroolsQuestionFormat {

	NULL,

	TEXT,

	MULTI_TEXT,

	NUMBER,

	DATE,

	POSTAL_CODE;

	public static DroolsQuestionFormat get(String tag) {
		for (DroolsQuestionFormat format : DroolsQuestionFormat.values()) {
			if (format.toString().toLowerCase().equals(tag.toLowerCase())) {
				return format;
			}
		}
		return null;
	}

}
