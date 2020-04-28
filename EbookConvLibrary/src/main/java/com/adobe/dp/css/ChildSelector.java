package com.adobe.dp.css;

import java.io.PrintWriter;

public class ChildSelector extends Selector {

	private final Selector parent;

	private final Selector child;

	ChildSelector(Selector parent, Selector child) {
		this.parent = parent;
		this.child = child;
	}

	public ElementMatcher getElementMatcher() {
		return new ChildElementMatcher(this, parent.getElementMatcher(), child.getElementMatcher());
	}

	public int getSpecificity() {
		return addSpecificity(parent.getSpecificity(), child.getSpecificity());
	}

	public void serialize(PrintWriter out) {
		parent.serialize(out);
		out.print(">");
		child.serialize(out);
	}

}
