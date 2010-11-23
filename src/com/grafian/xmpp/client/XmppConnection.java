package com.grafian.xmpp.client;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.grafian.xmpp.client.stanza.Message;

public class XmppConnection implements StreamReaderListener,
		StreamWriterListener {

	final public static int STATE_INACTIVE = 0;
	final public static int STATE_CONNECTING = 1;
	final public static int STATE_AUTHENTICATING = 2;
	final public static int STATE_BINDING = 3;
	final public static int STATE_ACTIVE = 4;

	private SocketConnection socket;
	private StreamReader reader;
	private StreamWriter writer;

	private Jid jid;
	private String password;

	private int state;

	final private Vector listener = new Vector();

	public XmppConnection() {

	}

	public void addListener(XmppListener xl) {
		if (!listener.contains(xl)) {
			listener.addElement(xl);
		}
	}

	public void removeListener(XmppListener xl) {
		listener.removeElement(xl);
	}

	private void setState(int state) {
		if (this.state != state) {
			int from = this.state;
			this.state = state;
			for (int i = 0; i < listener.size(); i++) {
				XmppListener xl = (XmppListener) listener.elementAt(i);
				xl.onStateChanged(from, state);
			}
		}
	}

	public void login(final String server, final int port, final Jid jid,
			String password, final boolean enableSsl) {
		this.jid = jid;
		this.password = password;
		final XmppConnection xmpp = this;

		new Thread(new Runnable() {

			public void run() {
				try {
					if (enableSsl) {
						socket = (SocketConnection) Connector.open("ssl://"
								+ server + ":" + port);
					} else {
						socket = (SocketConnection) Connector.open("socket://"
								+ server + ":" + port);
					}
					reader = new StreamReader(socket.openInputStream(), xmpp);
					writer = new StreamWriter(socket.openOutputStream(), xmpp);

					writer.write("<?xml version='1.0'?>" + "<s:stream to='"
							+ jid.getDomain() + "' " + "xmlns='jabber:client' "
							+ "xmlns:s='http://etherx.jabber.org/streams' "
							+ "version='1.0'>");
				} catch (IOException e) {
					setState(STATE_INACTIVE);
					e.printStackTrace();
				}
			}
		}).run();
	}

	public void shutdown() {
		reader.shutdown();
		writer.shutdown();
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setState(STATE_INACTIVE);
	}

	private void skipTag(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		String tag = parser.getName();
		int depth = parser.getDepth();
		while (parser.next() != XmlPullParser.END_TAG
				|| parser.getDepth() != depth || !parser.getName().equals(tag))
			/* skip */;
	}

	private void parseStream(XmlPullParser parser)
			throws XmlPullParserException, IOException {
	}

	private void parseError(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		String tag = parser.getName();
		String condition = null;
		while (parser.next() != XmlPullParser.END_TAG
				&& !parser.getName().equals(tag)) {
			if (!parser.getName().equals("text")) {
				condition = parser.getName();
			}
		}
	}

	private void parseMessage(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		Message m = new Message();
		m.setCommonAttr(parser);
		String tag = parser.getName();
		while (parser.next() != XmlPullParser.END_TAG
				&& !parser.getName().equals(tag)) {
			
		}
	}

	private void parsePresence(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		String tag = parser.getName();
		while (parser.next() != XmlPullParser.END_TAG
				&& !parser.getName().equals(tag)) {
			
		}
	}

	private void parseIq(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		String tag = parser.getName();
		while (parser.next() != XmlPullParser.END_TAG
				&& !parser.getName().equals(tag)) {
			
		}
	}

	public void onXmlEvent(XmlPullParser parser) {
		try {
			String tag;
			switch (parser.getEventType()) {
			case XmlPullParser.START_TAG:
				tag = parser.getName();
				if (tag.equals("stream")) {
					parseStream(parser);
				} else if (tag.equals("error")) {
					parseError(parser);
				} else if (tag.equals("message")) {
					parseMessage(parser);
				} else if (tag.equals("presence")) {
					parsePresence(parser);
				} else if (tag.equals("iq")) {
					parseIq(parser);
				} else {
					skipTag(parser);
				}
				break;
			case XmlPullParser.END_TAG:
				tag = parser.getName();
				if (tag.equals("stream")) {
					shutdown();
					break;
				}
				// Fall through
			default:
				shutdown();
				throw new RuntimeException("Invalid parser state.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			shutdown();
		}
	}

	public void onReadError() {
		shutdown();
	}

	public void onWriteError() {
		shutdown();
	}
}
