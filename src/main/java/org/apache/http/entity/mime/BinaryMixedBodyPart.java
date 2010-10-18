/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.http.entity.mime;

import org.apache.http.entity.mime.content.ContentBody;

/**
 * FormBodyPart class represents a content body that can be used as a part of
 * multipart encoded entities. This class automatically populates the header
 * with standard fields based on the content description of the enclosed body.
 * 
 * @since 4.0
 */
public class BinaryMixedBodyPart implements MultipartBodyPart {

	private final String name;
	private final Header header;

	private final ContentBody body;

	public BinaryMixedBodyPart(final String name, final ContentBody body) {
		super();
		if (name == null) {
			throw new IllegalArgumentException("Name may not be null");
		}
		if (body == null) {
			throw new IllegalArgumentException("Body may not be null");
		}
		this.name = name;
		this.body = body;
		this.header = new Header();

		generateContentType();
		generateTransferEncoding();
		generateTransferID(body);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.http.entity.mime.MultipartBodyPart#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.http.entity.mime.MultipartBodyPart#getBody()
	 */
	@Override
	public ContentBody getBody() {
		return this.body;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.http.entity.mime.MultipartBodyPart#getHeader()
	 */
	@Override
	public Header getHeader() {
		return this.header;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.http.entity.mime.MultipartBodyPart#addField(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void addField(final String name, final String value) {
		if (name == null) {
			throw new IllegalArgumentException("Field name may not be null");
		}
		this.header.addField(new MinimalField(name, value));
	}

	protected void generateContentType() {
		addField(MIME.CONTENT_TYPE, "application/octet-stream");

	}

	protected void generateTransferEncoding() {
		addField(MIME.CONTENT_TRANSFER_ENC, "binary");
	}

	protected void generateTransferID(final ContentBody body) {
		final StringBuilder buffer = new StringBuilder();
		buffer.append("<");
		buffer.append(getName());
		buffer.append(">");
		addField(MIME.CONTENT_ID, buffer.toString());

	}

}
