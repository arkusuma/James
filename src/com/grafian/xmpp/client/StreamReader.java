package com.grafian.xmpp.client;

import java.io.IOException;
import java.io.InputStream;

import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;

public class StreamReader implements Runnable {
	
	final private InputStream is;
	final private StreamReaderListener listener;
	final private XmlPullParser parser = new KXmlParser();
	private boolean done = false;
	
	public StreamReader(InputStream is, StreamReaderListener listener) {
		this.is = is;
		this.listener = listener;
		new Thread(this).run();
	}
	
	public void shutdown() {
		try {
			done = true;
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			parser.setInput(is, "UTF-8");
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
			while (!done) {
				parser.next();
				listener.onXmlEvent(parser);
			}
			is.close();
		} catch (Exception e) {
			if (!done) {
				e.printStackTrace();
				listener.onReadError();
			}
		}
	}
}
