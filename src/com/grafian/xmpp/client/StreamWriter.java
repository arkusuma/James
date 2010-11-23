package com.grafian.xmpp.client;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

public class StreamWriter implements Runnable {

	final private OutputStream os;
	final private StreamWriterListener listener;
	final private Vector queue = new Vector();
	private boolean done = false;

	public StreamWriter(OutputStream os, StreamWriterListener listener) {
		this.os = os;
		this.listener = listener;
	}

	public void write(String s) {
		synchronized (queue) {
			queue.addElement(s);
			queue.notifyAll();
		}
	}
	
	public void shutdown() {
		try {
			done = true;
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void flush() {
		while (!done && !queue.isEmpty()) {
			synchronized (queue) {
				try {
					queue.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void run() {
		while (!done) {
			while (!done && queue.isEmpty()) {
				synchronized (queue) {
					try {
						queue.wait(1000);
					} catch (InterruptedException e) {
						if (!done) {
							done = true;
							e.printStackTrace();
							listener.onWriteError();
						}
					}
				}
			}
			if (!done) {
				String s = (String) queue.elementAt(0);
				try {
					os.write(s.getBytes("UTF-8"));
					synchronized (queue) {
						queue.removeElementAt(0);
						queue.notifyAll();
					}
				} catch (Exception e) {
					if (!done) {
						done = true;
						e.printStackTrace();
						listener.onWriteError();
					}
				}
			}
		}
		try {
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
