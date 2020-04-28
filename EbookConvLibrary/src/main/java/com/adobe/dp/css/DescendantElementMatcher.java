package com.adobe.dp.css;

import com.adobe.dp.xml.util.SMap;

public class DescendantElementMatcher extends ElementMatcher {

	private final ElementMatcher ancestor;

	private final ElementMatcher descendant;

	private int ancestorMatchedDepth;

	DescendantElementMatcher(DescendantSelector selector, ElementMatcher parent, ElementMatcher child) {
		super(selector);
		this.ancestor = parent;
		this.descendant = child;
	}

	public void popElement() {
		if (ancestorMatchedDepth > 0)
			descendant.popElement();
		else
			ancestor.popElement();
		ancestorMatchedDepth--;
	}

	public MatchResult pushElement(String ns, String name, SMap attrs) {
		if (ancestorMatchedDepth > 0) {
			ancestorMatchedDepth++;
			return descendant.pushElement(ns, name, attrs);
		} else {
			MatchResult r = ancestor.pushElement(ns, name, attrs);
			if( r != null && r.getPseudoElement() == null )
				ancestorMatchedDepth = 1;
			return null;
		}
	}

}
