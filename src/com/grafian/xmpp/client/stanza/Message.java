package com.grafian.xmpp.client.stanza;

public class Message extends Stanza {

	final public static String TYPE_CHAT = "chat";
	final public static String TYPE_ERROR = "error";
	final public static String TYPE_GROUPCHAT = "groupchat";
	final public static String TYPE_HEADLINE = "headline";
	final public static String TYPE_NORMAL = "normal";

	private String subject;
	private String body;
	private String thread;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	public String toXmlString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<message");
		String attr = getAttrString();
		if (attr.length() > 0) {
			sb.append(" ");
			sb.append(attr);
		}
		if (subject != null || body != null || thread != null) {
			sb.append(">");
			if (subject != null) {
				sb.append("<subject>");
				sb.append(escapeXml(subject));
				sb.append("</subject>");
			}
			if (body != null) {
				sb.append("<body>");
				sb.append(escapeXml(body));
				sb.append("</body>");
			}
			if (thread != null) {
				sb.append("<thread>");
				sb.append(escapeXml(thread));
				sb.append("</thread>");
			}
			sb.append("</message>");
		} else {
			sb.append("/>");
		}
		return sb.toString();
	}
}
