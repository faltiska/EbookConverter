package com.adobe.dp.office.conv;

import com.adobe.dp.css.InlineRule;

class StylingResult {

	InlineRule containerRule;

	final InlineRule elementRule = new InlineRule();

	String containerClassName;

	String elementClassName;

	String elementName;

	InlineRule tableCellRule;
	
	Integer cols;
}
