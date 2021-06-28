import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

class NewDialogue extends Alert {

    private TextField sets = new TextField();
    private TextField reps = new TextField();
    private TextField weight = new TextField();
    private TextField date = new TextField();

    NewDialogue(){
        super(Alert.AlertType.CONFIRMATION);
        GridPane grid = new GridPane();
        grid.addRow(0, new Label("Sets:"), sets);
        grid.addRow(1, new Label("Reps:"), reps);
        grid.addRow(2, new Label("Weight:"), weight);
        grid.addRow(3, new Label("Date (yyyy-mm-dd):"), date);
        getDialogPane().setContent(grid);
        setHeaderText(null);
    }

    int getSets(){
        return Integer.parseInt(sets.getText());
    }

    int getReps(){
        return Integer.parseInt(reps.getText());
    }

    double getWeight(){
        return Double.parseDouble(weight.getText());
    }

    Date getDate(){
        String line = date.getText();
        String[] tokens = line.split("-");
        int year = Integer.parseInt(tokens[0]);
        int month = Integer.parseInt(tokens[1]);
        int day = Integer.parseInt(tokens [2]);
        return new Date(year, month, day);
    }
}
