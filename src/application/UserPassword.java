package application;

public enum UserPassword {

	/**
	 * Enum which contains all predefined password for users.
	 * 
	 * @author Maciej Suchocki / msuchock@stud.elka.pw.edu.pl
	 */
	msuchock("haslo1"), ssawicki("haslo2"), mwisniewski("haslo3"), azamojski("haslo4"), rsawicki("haslo5"),
	pkaminski("haslo6"), kbidus("haslo7"), tmurawski("haslo8"), sszymborski("haslo9"), xyz("haslo10");
	private String pass;

	
	/**
	 * Enum's constructor.
	 * 
	 * @param msg 
	 *            String argument which is copied as password in enum
	 */
	UserPassword(String msg) {
		pass = msg;
	}

	/**
	 * Returns password of user.
	 * 
	 * @return Password of user.
	 */
	@Override
	public String toString() {
		return pass;
	}

}
