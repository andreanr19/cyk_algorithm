package ui;

import java.util.StringTokenizer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import model.Grammar;

public class cykGUI {

	private Grammar gr;

	@FXML
	private Button aCYK;

	@FXML
	private TextField txtCad;

	@FXML
	private TextArea txtArea;

	@FXML
	void cykButton(ActionEvent event) {
		verifyString(txtArea.getText(), txtCad.getText());
	}

	public void verifyString(String g, String c) {
		try {
			g = applicateFormat(g);
			gr = new Grammar();

			gr.addVarsToTheGrammar(g);
			boolean generates = gr.CYK(c);
			if (generates) {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Congratulations!!!!!!!!");
				alert.setGraphic(new ImageView(this.getClass().getResource("yes.gif").toString()));
				alert.setHeaderText(null);
				alert.setContentText("The grammar does generates the chain " + c);

				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Congratulationsn't!!!!!!!!");
				alert.setGraphic(new ImageView(this.getClass().getResource("not.gif").toString()));
				alert.setHeaderText(null);
				alert.setContentText("The grammar doesn't generates the chain " + c);

				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!!!!!!!!");
			alert.setGraphic(new ImageView(this.getClass().getResource("wrongInput.gif").toString()));
			alert.setHeaderText(null);
			alert.setContentText("The format is incorrect");

			alert.showAndWait();
		}
	}


	public String applicateFormat(String g) {
		String formatedGrammar = "";
		String[] vars = g.split("\n");
		for (int i = 0; i < vars.length; i++) {
			StringTokenizer separator = new StringTokenizer(vars[i], ">>>");
			String var = separator.nextToken().trim();
			formatedGrammar += var;
			String productions = separator.nextToken();
			separator = new StringTokenizer(productions.trim(), "||");
			while (separator.hasMoreTokens()) {
				formatedGrammar += " " + separator.nextToken();
			}
			if (i != vars.length - 1) {
				formatedGrammar += "\n";
			}
		}
		return formatedGrammar;
	}

	public cykGUI() {
	}
}
