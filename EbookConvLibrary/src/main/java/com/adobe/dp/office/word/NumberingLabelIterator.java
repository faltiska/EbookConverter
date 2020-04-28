package com.adobe.dp.office.word;

import com.adobe.dp.xml.util.StringUtil;

import java.util.Iterator;

class NumberingLabelIterator implements Iterator {

	private final NumberingDefinitionInstance instance;

	private final NumberingLevelDefinition level;

	private int count;

	boolean first;

	NumberingLabelIterator(NumberingDefinitionInstance instance, NumberingLevelDefinition level) {
		this.instance = instance;
		this.level = level;
		reset();
	}

	void reset() {
		first = true;
		count = level.start;
	}

	public boolean hasNext() {
		return true;
	}

	private String latin(int n) {
		StringBuffer r = new StringBuffer();
		n--;
		char c = (char) ('a' + n % 26);
		int cc = n / 26 + 1;
		for (int i = 0; i < cc; i++)
			r.append(c);
		return r.toString();
	}

	String getNumberStr() {
		String fmt = level.numFmt;
		if (fmt.equals("decimal"))
			return Integer.toString(count);
		if (fmt.equals("upperLetter"))
			return latin(count).toUpperCase();
		if (fmt.equals("lowerLetter"))
			return latin(count);
		if (fmt.equals("upperRoman"))
			return StringUtil.printRoman(count).toUpperCase();
		if (fmt.equals("lowerRoman"))
			return StringUtil.printRoman(count);
		return "";
	}

	public Object next() {
		if (first)
			first = false;
		else
			count++;
		String txt = instance.formatText(level.lvlText, level.lvl);
		// System.out.println("[" + instance.numId + "," + level.lvl + "]: " +
		// level.lvlText + " -> " + txt);
		instance.resetLevels(level.lvl);
		return new NumberingLabel(instance, level, txt);
	}

	public void remove() {
		throw new RuntimeException("not supported");
	}

}
