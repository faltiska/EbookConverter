package com.adobe.dp.css;

import java.io.PrintWriter;

class NamespaceRule {

	private final String prefix;

	private final String ns;

	public NamespaceRule(String prefix, String ns) {
		this.prefix = prefix;
		this.ns = ns;
	}

	public void serialize(PrintWriter out) {
		out.print("@namespace ");
		if(prefix != null ) {
			out.print(prefix);
			out.print(" ");
		}
		out.print("\"");
		out.print(ns);
		out.println("\";");
	}

}
