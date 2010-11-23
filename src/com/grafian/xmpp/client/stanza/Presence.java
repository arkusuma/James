package com.grafian.xmpp.client.stanza;

public class Presence extends Stanza {

	final public static String TYPE_UNAVAILABLE = "unavailable";
	final public static String TYPE_SUBSCRIBE = "subscribe";
	final public static String TYPE_SUBSCRIBED = "subscribed";
	final public static String TYPE_UNSIBSCRIBE = "unsubscribe";
	final public static String TYPE_UNSUBSCRIBED = "unsubscribed";
	final public static String TYPE_PROBE = "probe";
	final public static String TYPE_ERROR = "error";

	final public static String SHOW_AWAY = "away";
	final public static String SHOW_CHAT = "chat";
	final public static String SHOW_DND = "dnd";
	final public static String SHOW_PRIORITY = "priority";

	private String show;
	private String status;
	private int priority = 0;

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String toXmlString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<presence");
		String attr = getAttrString();
		if (attr.length() > 0) {
			sb.append(" ");
			sb.append(attr);
		}
		if (show != null || status != null || priority != 0) {
			sb.append(">");
			if (show != null) {
				sb.append("<show>");
				sb.append(escapeXml(show));
				sb.append("</show>");
			}
			if (status != null) {
				sb.append("<status>");
				sb.append(escapeXml(status));
				sb.append("</status>");
			}
			if (priority != 0) {
				sb.append("<priority>");
				sb.append(Integer.toString(priority));
				sb.append("</priority>");
			}
			sb.append("</presence>");
		} else {
			sb.append("/>");
		}
		return sb.toString();
	}
}
