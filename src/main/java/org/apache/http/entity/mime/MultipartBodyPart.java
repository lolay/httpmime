package org.apache.http.entity.mime;

import org.apache.http.entity.mime.content.ContentBody;

public interface MultipartBodyPart {

	public abstract String getName();

	public abstract ContentBody getBody();

	public abstract Header getHeader();

	public abstract void addField(final String name, final String value);

}