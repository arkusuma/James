package com.grafian.xmpp.client.stanza;

import org.xmlpull.v1.XmlPullParser;

public abstract class Stanza {
	protected String to;
	protected String from;
	protected String id;
	protected String type;
	protected String lang;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public void setCommonAttr(XmlPullParser parser) {
		for (int i = 0; i < parser.getAttributeCount(); i++) {
			String attr = parser.getAttributeName(i);
			String val = parser.getAttributeValue(i);
			if (attr.equals("to")) {
				to = val;
			} else if (attr.equals("from")) {
				from = val;
			} else if (attr.equals("id")) {
				id = val;
			} else if (attr.equals("type")) {
				type = val;
			} else if (attr.equals("lang")) {
				lang = val;
			}
		}
	}

	public String escapeXml(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '&':
				sb.append("&amp;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case '\'':
				sb.append("&apos");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public abstract String toXmlString();

	public String getAttrString() {
		StringBuffer sb = new StringBuffer();
		if (to != null) {
			sb.append("to='").append(escapeXml(to)).append("'");
		}
		if (from != null) {
			if (sb.length() > 0) {
				sb.append(' ');
			}
			sb.append("from='").append(escapeXml(from)).append("'");
		}
		if (id != null) {
			if (sb.length() > 0) {
				sb.append(' ');
			}
			sb.append("id='").append(escapeXml(id)).append("'");
		}
		if (type != null) {
			if (sb.length() > 0) {
				sb.append(' ');
			}
			sb.append("type='").append(escapeXml(type)).append("'");
		}
		if (lang != null) {
			if (sb.length() > 0) {
				sb.append(' ');
			}
			sb.append("xml:lang='").append(escapeXml(lang)).append("'");
		}
		return sb.toString();
	}
}
