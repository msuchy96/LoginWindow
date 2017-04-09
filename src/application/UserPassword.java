package application;

public enum UserPassword {

		msuchock("haslo1"),ssawicki("haslo2"),mwisniewski("haslo3"),azamojski("haslo4"),rsawicki("haslo5"),pkaminski("haslo6"),kbidus("haslo7"),tmurawski("haslo8"),sszymborski("haslo9"),xyz("haslo10");
	private String pass;
	
	UserPassword(String msg) {
		pass = msg;
	}
	
	@Override
	public String toString() {
		return pass;
	}
	
	
}
