package com.italam.data;

public enum EnumDBsConnDetails {

	OSB_DB_CONN_STRING(0), OSB_DB_USER(1), OSB_DB_PASS(2), CRM_DB_CONN_STRING(3), CRM_DB_USER(4), CRM_DB_PASS(
			5), WIZ_DB_CONN_STRING(6), WIZ_DB_USER(7), WIZ_DB_PASS(8), IRB_DB_CONN_STRING(9), IRB_DB_USER(
					10), IRB_DB_PASS(11), WIZ_HOT_DB_CONN_STRING(12), WIZ_HOT_DB_USER(13), WIZ_HOT_DB_PASS(14);

	private final int value;

	EnumDBsConnDetails(final int newValue) {
		value = newValue;
	}

	public int getValue() {
		return value;
	}

}
