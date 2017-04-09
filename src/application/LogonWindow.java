package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
	private List<Environment> listChoice;
	private Environment pickedEnvironment;
	
	
	public LogonWindow(String title, String text) {
		
		givenTitle = title;
		givenText = text;
		
		
	};

	
	
	
	
	private static ImageView setPic(Dialog<Pair<Environment, String>> dialog) {
		ImageView imgPic = new ImageView();
		String path = "file:src/images/icon.png";
		Image img = new Image(path);
		imgPic.setImage(img);
		imgPic.setFitHeight(100);
		imgPic.setFitWidth(100);

		// Get the Stage. and set
		// Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		// stage.getIcons().add(img);

		return imgPic;
	}

	private GridPane setNewLayout(Node loginButton) {

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		
		PasswordField fieldPassword = new PasswordField();
		fieldPassword.setPromptText("Password");
		
		grid.add(new Label("Password:"), 1, 3);
		grid.add(fieldPassword, 2, 3);

		ChoiceBox<Environment> logEnvBox = setEnvironmentBox();
		
		ComboBox<String> logUsernameBox = new ComboBox<>();
		logUsernameBox.setDisable(true);
		logUsernameBox.setPrefWidth(150);
		
		grid.add(new Label("Environment:"), 1, 1);
		grid.add(logEnvBox, 2, 1);

		grid.add(new Label("Username:"), 1, 2);
		grid.add(logUsernameBox, 2, 2);
		
		addListeners(logUsernameBox,logEnvBox,fieldPassword,loginButton);
	 
		loginButton.setDisable(true);

		return grid;

	}
	
	private void addListeners(ComboBox<String> logUsernameBox, ChoiceBox<Environment> logEnvBox, PasswordField fieldPassword, Node loginButton)
	{
		
		logEnvBox.setOnAction(e -> {
			
			setUsernameBox(logEnvBox.getValue(), logUsernameBox);

		});
		
		logUsernameBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(
					(newValue == null) || 
					logEnvBox.getValue() == null || 
					fieldPassword.getText().trim().isEmpty());
		});

		logEnvBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(
					(newValue == null) ||
					fieldPassword.getText().trim().isEmpty() || 
					logUsernameBox.getValue() == null); 
		});

		
		fieldPassword.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(
					newValue.trim().isEmpty() || 
					logEnvBox.getValue() == null || 
					logUsernameBox.getValue() == null);
		});
		
	}

	private ChoiceBox<Environment> setEnvironmentBox() {

		
		listChoice = new ArrayList<Environment>();
		listChoice.add(Environment.Production);
		listChoice.add(Environment.Test);
		listChoice.add(Environment.Development);
		
		ObservableList<Environment> itemsChoice = FXCollections.observableList(listChoice);
		
		ChoiceBox<Environment> choiceBox = new ChoiceBox<>(itemsChoice);
		
		choiceBox.setPrefWidth(150);
		
		return choiceBox;
		
	}
	
	private void setUsernameBox(Environment clickedEnvironment, ComboBox<String> logUsernameBox)
	{
		
		pickedEnvironment = clickedEnvironment;
		List<String> listUsername = new ArrayList<>();
		
		for(int i=0; i<clickedEnvironment.getCount();i++)
		{
			listUsername.add(clickedEnvironment.getText(i));
		}
		
		ObservableList<String>itemsUsername = FXCollections.observableList(listUsername);
		
		
		logUsernameBox.getItems().clear();
		
		logUsernameBox.setItems(itemsUsername);
		
		logUsernameBox.setDisable(false);
		
		logUsernameBox.setEditable(true);
		
	}

	

	void setDialogProperties(Dialog<Pair<Environment, String>> dialog, Node loginButton) {

		dialog.setTitle(givenTitle);

		dialog.setHeaderText(givenText);

		dialog.setGraphic(setPic(dialog));

		dialog.getDialogPane().setContent(setNewLayout(loginButton));
	}

	public Optional<Pair<Environment, String>> showAndWait() {

		Dialog<Pair<Environment, String>> dialog = new Dialog<>();


		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		ButtonType cancelButtonType = new ButtonType("Cancel",ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, cancelButtonType);

		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		
		setDialogProperties(dialog,loginButton);
		
		dialog.setResultConverter(button -> {
			if(button == loginButtonType)
				return new Pair<Environment, String>(pickedEnvironment, "Nothing");
			else 
				return null;
			
			});
		
		
		
			Optional<Pair<Environment, String>> result = dialog.showAndWait();


			if(result == null)
				return Optional.empty();
			else 
				return result;
			
		

	}

}
