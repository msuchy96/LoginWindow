package application;
	
import java.util.Optional;



import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;


public class LogonWindowTest extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) {
		
		Optional<Pair<Environment, String>> result =  (new LogonWindow("Sign in",  "Login to the STYLEman system")).showAndWait();
		if (result.isPresent()) {
			System.out.println(result.get().getKey().toString() + "\n" + result.get().getValue());
			} 
		else {
			System.out.println("Logon Canceled");
			}
	}
}
	
	
	
