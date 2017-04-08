package application;

import java.util.Optional;

import org.omg.CORBA.Environment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class LogonWindow {

	private String givenTitle;
	private String givenText;
	private LogonEnvironments chosenEnvironment;

	public LogonWindow(String title, String text) {
		givenTitle = title;
		givenText = text;
		chosenEnvironment = LogonEnvironments.Test;
	};

	private static ImageView setPic() {
		ImageView imgPic = new ImageView();
		String path = "file:src/images/icon.png";
		Image img = new Image(path);
		imgPic.setImage(img);
		imgPic.setFitHeight(100);
		imgPic.setFitWidth(100);

		return imgPic;
	}

	private GridPane setNewLayout(PasswordField fieldPassword) {

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		grid.add(new Label("Password:"), 1, 3);
		grid.add(fieldPassword, 2, 3);

		ComboBox<LogonEnvironments> logEnvBox = setEnvironmentBox();

		grid.add(new Label("Environment:"), 1, 1);
		grid.add(logEnvBox, 2, 1);

		
		

		grid.add(new Label("Username:"), 1, 2);
		grid.add(pickUsernameBox(logEnvBox.getValue()),2,2);
		
		logEnvBox.setOnAction(e -> grid.add(pickUsernameBox(logEnvBox.getValue()), 2, 2));

		return grid;

	}



	private ComboBox<LogonEnvironments> setEnvironmentBox() {

		ComboBox<LogonEnvironments> comboBox = new ComboBox<>();

		comboBox.getItems().addAll(LogonEnvironments.values());

		return comboBox;

	}


	private ComboBox<String> pickUsernameBox(LogonEnvironments logonEnvironments) {

		ComboBox<String> comboBox = new ComboBox<>();

			if(logonEnvironments != null)
			for(int i=0; i<logonEnvironments.getCount();i++)
				comboBox.getItems().add(logonEnvironments.getText(i));

		
		
		comboBox.setEditable(true);
		return comboBox;

	}

	void setDialogProperties(Dialog<Pair<Environment, String>> dialog) {

		dialog.setTitle(givenTitle);

		dialog.setHeaderText(givenText);

		dialog.setGraphic(setPic());

		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		PasswordField password = new PasswordField();
		password.setPromptText("Password");

		dialog.getDialogPane().setContent(setNewLayout(password));
	}

	public Optional<Pair<Environment, String>> showAndWait() {

		Dialog<Pair<Environment, String>> dialog = new Dialog<>();

		setDialogProperties(dialog);

		return dialog.showAndWait();

	}

}
