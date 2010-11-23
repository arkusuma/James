package com.grafian.xmpp.client;

public class Jid {
	private String node;
	private String domain;
	private String resource;
	
	public Jid(String jid) {
		parse(jid);
	}
	
	public Jid(String node, String domain, String resource) {
		if (domain == null) {
			throw new RuntimeException("domain is mandatory");
		}
		
		this.node = node;
		this.domain = domain;
		this.resource = resource;
	}
	
	public void parse(String jid) {
		int atIndex = jid.indexOf("@");
		int slashIndex = jid.indexOf("/", atIndex);
		if (slashIndex == -1) {
			slashIndex = jid.length();
			resource = null;
		} else {
			resource = jid.substring(slashIndex);
		}
		if (atIndex == -1) {
			node = null;
		} else {
			node = jid.substring(0, atIndex);
		}
		domain = jid.substring(atIndex + 1, slashIndex);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (node != null) {
			sb.append(node).append('@');
		}
		sb.append(domain);
		if (resource != null) {
			sb.append('/').append(domain);
		}
		return sb.toString();
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
}
