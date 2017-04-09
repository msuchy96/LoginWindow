package application;

import java.util.Optional;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;
/**
 * 
 * Class which test LogonWindow and displays on console pair environment,username if login was successful or Logon Canceled if was canceled.
 *
 * 
 * @author Maciej Suchocki / msuchock@stud.elka.pw.edu.pl
 *
 */
public class LogonWindowTest extends Application {

	/**
	 * Calls main JavaFx method.
	 * 
	 * @param args
	 *            commandLine arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Calls constructor of LogonWindow and displays on console pair environment,username if login was successful or Logon Canceled if was canceled.
	 * 
	 * @param arg0
	 *            Instance of Stage which was automatically created by JavaFX.
	 */
	@Override
	public void start(Stage primaryStage) {

		Optional<Pair<Environment, String>> result = (new LogonWindow("Sign in", "Login to the STYLEman system"))
				.showAndWait();

		if (result.isPresent()) {
			System.out.println(
					"Environment: " + result.get().getKey().toString() + "\nUsername: " + result.get().getValue());
		} else {
			System.out.println("Logon Canceled");
		}
	}
}
