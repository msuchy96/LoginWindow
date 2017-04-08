package application;

public enum LogonEnvironments {

	/**
	 * Enum which contains all predefined icons.
	 * 
	 */
	Test("msuchock", "ssawicki", "mwisniewski"), Developer("azamojski", "rsawicki", "pkaminski"), Generative("kbidus", "tmurawski", "sszymborski");
	private String[] text = new String[3];
	private int count;
	
	/**
	 * Enum's constructor.
	 * 
	 * @param msg
	 *            String argument which is copied as new icon file name in enum
	 */
	LogonEnvironments(String... msg) {
		for (int i = 0; i < msg.length; ++i)
			text[i] = msg[i];

		count = msg.length;
	}

	/**
	 * Returns the label of a button with a given index.
	 * 
	 * @param i
	 *            given index of the array with labels
	 * @return Element of array with labels
	 */
	public String getText(int i) {
		return text[i];
	}
	
	public int getCount() {
		return count;
	}
}
