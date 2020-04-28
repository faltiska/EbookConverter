package com.adobe.dp.fb2;

import com.adobe.dp.css.CSSURL;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FB2CSSURL extends CSSURL {

	private FB2Document doc;

	FB2CSSURL(FB2Document doc, String url) {
		super(url);
	}

	public FB2Document getFB2Document() {
		return doc;
	}

	private FB2Binary getResource() {
		String uri = getURI();
		if (!uri.startsWith("#"))
			return null;
		return doc.getBinaryResource(uri.substring(1));
	}

	public String getContentType() {
		FB2Binary resource = getResource();
		if (resource == null)
			return super.getContentType();
		return resource.getMediaType();
	}

	public InputStream getInputStream() throws IOException {
		FB2Binary resource = getResource();
		if (resource == null)
			return super.getInputStream();
		return new ByteArrayInputStream(resource.getData());
	}

	public byte[] getData() throws IOException {
		FB2Binary resource = getResource();
		if (resource == null)
			return super.getData();
		return resource.getData();
	}
}
