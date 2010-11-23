package james;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.sun.lwuit.Display;
import com.sun.lwuit.Form;

public class JamesMidlet extends MIDlet {

	public JamesMidlet() {
		// TODO Auto-generated constructor stub
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub
	}

	protected void startApp() throws MIDletStateChangeException {
		Display.init(this);
		Form f = new Form("Test");
		f.show();
	}

}
