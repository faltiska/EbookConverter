package com.adobe.dp.epub.opf;

import com.adobe.dp.epub.ops.Element;
import com.adobe.dp.epub.ops.OPSDocument;
import com.adobe.dp.epub.ops.XRef;

import java.util.Hashtable;

public class ResourceRef {

	private Hashtable unresolvedXRefMap;

	ResourceRef(Publication epub, String name) {
		this.epub = epub;
		this.name = name;
	}

	private final Publication epub;

	String name;

	public Resource getResource() {
		return epub.getResourceByName(name);
	}

	public String getResourceName() {
		return name;
	}

	public XRef getXRef(String id) {
		Resource r = getResource();
		if( r == null )
			return null;
		if (r instanceof OPSResource) {
			OPSDocument ops = ((OPSResource) r).getDocument();
			if (id == null)
				return ops.getRootXRef();
			Element e = ops.getElementById(id);
			if (e != null)
				return e.getSelfRef();
		}
		XRef ref;
		if (unresolvedXRefMap == null || (ref = (XRef) unresolvedXRefMap.get(id)) == null) {
			if (unresolvedXRefMap == null)
				unresolvedXRefMap = new Hashtable();
			ref = new XRef(r, id);
			unresolvedXRefMap.put((id == null ? "" : id), ref);
		}
		return ref;
	}

	public XRef takeOverUnresolvedXRef(String id) {
		if (unresolvedXRefMap == null)
			return null;
		return (XRef) unresolvedXRefMap.remove(id == null ? "" : id);
	}
}
