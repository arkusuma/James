package com.grafian.xmpp.client;

import org.xmlpull.v1.XmlPullParser;

public interface StreamReaderListener {
	public void onXmlEvent(XmlPullParser parser);
	public void onReadError();
}
