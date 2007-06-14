package org.ourproject.kune.client.rpc.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class KuneDoc implements IsSerializable{
	private String name;
	private String content;
	private String licenseName;
	
	
	public KuneDoc(String name, String content, String licenseName) {
		this.name = name;
		this.content = content;
		this.licenseName = licenseName;
	}
	
	public KuneDoc() {
		this(null, null, null);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLicenseName() {
		return licenseName;
	}
	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}
	
	
}
