package com.adobe.dp.css;

import java.util.Stack;

class SparseStack {

	static class Entry {

		final Object content;

		int depth;

		Entry(Object obj) {
			this.content = obj;
		}
	}

	private final Stack stack = new Stack();

	public SparseStack() {
		stack.push(new Entry(null));
	}

	public void push(Object obj) {
		if (obj == null)
			((Entry) stack.peek()).depth++;
		else
			stack.push(new Entry(obj));
	}

	public Object pop() {
		Entry top = (Entry) stack.peek();
		if (top.depth == 0) {
			stack.pop();
			if (top.content == null)
				throw new RuntimeException("stack underflow");
			return top.content;
		}
		top.depth--;
		return null;
	}
}
