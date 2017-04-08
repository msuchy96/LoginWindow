package application;
	
import java.util.Optional;

import org.omg.CORBA.Environment;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;


public class LogonWindowTest extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		Optional<Pair<Environment,String>> result =  (new LogonWindow("Sign in",  "Login to the STYLEman system")).showAndWait();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
