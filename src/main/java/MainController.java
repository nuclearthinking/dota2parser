import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    private TextField steamIdTextArea;

    @FXML
    private Label steamIdLabel;

    @FXML
    private Button button;

    @FXML
    private Label resultText;

    @FXML
    private ProgressBar progressBar;


    public MainController() {

    }

    @FXML
    private void initialize() {

    }

    public void calculate() {
        resultText.setText("asdasd");
    }
}
