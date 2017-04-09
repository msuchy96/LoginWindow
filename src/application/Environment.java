package application;

public enum Environment {

	/**
	 * Enum which contains all predefined environments.
	 * 
	 * @author Maciej Suchocki / msuchock@stud.elka.pw.edu.pl
	 */
	Test("msuchock", "ssawicki", "mwisniewski"), Development("azamojski", "rsawicki", "pkaminski"), Production("kbidus", "tmurawski", "sszymborski");
	private String[] text = new String[3];
	private int count;

	/**
	 * Enum's constructor.
	 * 
	 * @param msg
	 *            String argument which is copied as new environment in enum
	 */
	Environment(String... msg) {
		for (int i = 0; i < msg.length; ++i)
			text[i] = msg[i];

		count = msg.length;
	}

	/**
	 * Returns the username with a given index.
	 * 
	 * @param i
	 *            given index of the array with usernames
	 * @return Element of array with labels
	 */
	public String getText(int i) {
		return text[i];
	}

	/**
	 * Getter number usernames in enum.
	 * 
	 * @return Number of usernames in enum.
	 */
	public int getCount() {
		return count;
	}
}
