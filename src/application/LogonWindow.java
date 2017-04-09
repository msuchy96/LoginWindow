package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

/**
 * Class which implements public method showAndWait, allowing user to display a LogonDialog.
 *
 * 
 * @author Maciej Suchocki / msuchock@stud.elka.pw.edu.pl
 *
 */
public class LogonWindow {

	private String givenTitle;
	private String givenText;
	private List<Environment> listChoice;
	private Environment pickedEnvironment;
	private String username;
	private String password;

	/**
	 * Public constructor of this class allows creating an instance of it.
	 * 
	 * @param title
	 *            window label
	 * @param text
	 *            header text
	 */
	public LogonWindow(String title, String text) {

		givenTitle = title;
		givenText = text;
		username = "";

	};

	/**
	 * Image setter.
	 * 
	 * @param dialog
	 *            main window container
	 * @return Icon container.
	 */
	private static ImageView setPic(Dialog<Pair<Environment, String>> dialog) {
		ImageView imgPic = new ImageView();
		String path = "file:src/images/icon.png";
		Image img = new Image(path);
		imgPic.setImage(img);
		imgPic.setFitHeight(100);
		imgPic.setFitWidth(100);

		return imgPic;
	}
	
	/**
	 * EnvironmentBox setter.
	 * 
	 * @return ChoiceBox with all environments set up.
	 */
	private ChoiceBox<Environment> setEnvironmentBox() {

		listChoice = new ArrayList<Environment>();
		listChoice.add(Environment.Production);
		listChoice.add(Environment.Test);
		listChoice.add(Environment.Development);

		ObservableList<Environment> itemsChoice = FXCollections.observableList(listChoice);

		ChoiceBox<Environment> choiceBox = new ChoiceBox<>(itemsChoice);

		choiceBox.setPrefWidth(230);

		return choiceBox;

	}

	/**
	 * UsernameBox setter.
	 * 
	 * @param clickedEnvironment
	 *            environment which was clicked in order to set proper usernames
	 *            in usernameBox
	 * @param logUsernameBox
	 *            usernameBox to be set up
	 */
	private void setUsernameBox(Environment clickedEnvironment, ComboBox<String> logUsernameBox) {

		pickedEnvironment = clickedEnvironment;
		List<String> listUsername = new ArrayList<>();

		for (int i = 0; i < clickedEnvironment.getCount(); i++) {
			listUsername.add(clickedEnvironment.getText(i));
		}

		ObservableList<String> itemsUsername = FXCollections.observableList(listUsername);

		logUsernameBox.getItems().clear();

		logUsernameBox.setItems(itemsUsername);

		logUsernameBox.setDisable(false);

		logUsernameBox.setEditable(true);

	}

	/**
	 * Layout and action setter.
	 * 
	 * @param loginButton
	 *            container with button which you clicked to sign in
	 * @return Ready layout with all elements set up.
	 */
	private GridPane setNewLayout(Node loginButton) {

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 20, 20));

		PasswordField fieldPassword = new PasswordField();
		fieldPassword.setPromptText("Password");

		grid.add(new Label("Password:"), 1, 3);
		grid.add(fieldPassword, 2, 3);

		ChoiceBox<Environment> logEnvBox = setEnvironmentBox();

		ComboBox<String> logUsernameBox = new ComboBox<>();
		logUsernameBox.setDisable(true);
		logUsernameBox.setPrefWidth(230);

		grid.add(new Label("Environment:"), 1, 1);
		grid.add(logEnvBox, 2, 1);

		grid.add(new Label("Username:"), 1, 2);
		grid.add(logUsernameBox, 2, 2);

		addListeners(logUsernameBox, logEnvBox, fieldPassword, loginButton);

		loginButton.setDisable(true);

		return grid;

	}

	/**
	 * Method which is called when user dosen't exist or password is invalid. It clears password and username field then displays a message about error.
	 * 
	 * @param fieldPassword
	 *            container where user enters the password
	 * @param logUsernameBox
	 *            container where user enters username or select from existing
	 *            usernames
	 */
	private void resetAndDisplayMessage(PasswordField fieldPassword, ComboBox<String> logUsernameBox) {
		fieldPassword.textProperty().setValue("");
		password = fieldPassword.textProperty().toString();
		logUsernameBox.valueProperty().set("");
		username = "";
		logUsernameBox.setValue(null);
		fieldPassword.setPromptText("Wrong password or user doesn't exist!");
	}

	/**
	 * Method which listens for actions and changes.
	 * 
	 * @param logUsernameBox
	 *            container where user enters username or select from existing usernames
	 * @param logEnvBox
	 *            container where user select from existing environments
	 * @param fieldPassword
	 *            container where user enters the password
	 * @param loginButton
	 *            container with button which you clicked to sign in
	 */
	private void addListeners(ComboBox<String> logUsernameBox, ChoiceBox<Environment> logEnvBox,
			PasswordField fieldPassword, Node loginButton) {

		loginButton.addEventFilter(ActionEvent.ACTION, (event) -> {
			if (!authenticated()) {
				event.consume();
				resetAndDisplayMessage(fieldPassword, logUsernameBox);

			}
		});

		logEnvBox.setOnAction(e -> {

			setUsernameBox(logEnvBox.getValue(), logUsernameBox);

		});

		logUsernameBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(
					(newValue == null) || logEnvBox.getValue() == null || fieldPassword.getText().trim().isEmpty());
			username = newValue;
		});

		logEnvBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable((newValue == null) || fieldPassword.getText().trim().isEmpty()
					|| logUsernameBox.getValue() == null);

		});

		fieldPassword.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(
					newValue.trim().isEmpty() || logEnvBox.getValue() == null || logUsernameBox.getValue() == null);
			password = newValue;

		});

	}

	

	/**
	 * Sets basic dialog properties and calls other methods.
	 * 
	 * @param dialog
	 *            main window container
	 * @param loginButton
	 *            container with button which you clicked to sign in
	 */
	void setDialogProperties(Dialog<Pair<Environment, String>> dialog, Node loginButton) {

		dialog.setTitle(givenTitle);

		dialog.setHeaderText(givenText);

		dialog.setGraphic(setPic(dialog));

		dialog.getDialogPane().setContent(setNewLayout(loginButton));

	}

	/**
	 * Checks if user typed valid password and username.
	 * 
	 * @return bollean which tells if authentication was successful
	 */
	private boolean authenticated() {

		if (contains(username)) {
			if (password.equals(UserPassword.valueOf(username).toString()))
				return true;
			else
				return false;

		} else
			return false;
	}

	/**
	 * Checks if typed user exists.
	 * 
	 * @param test
	 *            typed username
	 * @return Bollean which tells if user exist.
	 */
	private static boolean contains(String test) {

		for (UserPassword u : UserPassword.values()) {
			if (u.name().equals(test)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Calls all methods which create dialog window.
	 * 
	 * @return Environment and username as pair or empty optional.
	 */
	public Optional<Pair<Environment, String>> showAndWait() {

		Dialog<Pair<Environment, String>> dialog = new Dialog<>();

		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, cancelButtonType);

		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);

		setDialogProperties(dialog, loginButton);

		dialog.setResultConverter(button -> {
			if (button == loginButtonType)

				return new Pair<Environment, String>(pickedEnvironment, username);

			else
				return null;

		});

		Optional<Pair<Environment, String>> result = dialog.showAndWait();

		if (result == null)
			return Optional.empty();
		else
			return result;

	}

}
