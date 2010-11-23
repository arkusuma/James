package com.grafian.xmpp.client;

public interface XmppListener {
	public void onStateChanged(int from, int to);
	public void onMessage();
	public void onPresence();
}
