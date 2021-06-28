import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.util.*;

public class Program extends Application {
    private ListView<Exercise> exercises = new ListView<>();
    private TextArea display;
    private boolean changed = false;
    private Set<Lift> lifts = new HashSet<>();
    private Map<Exercise, Set<Lift>> liftsByExercise = new TreeMap<>();
    private Map<Date, List<Lift>> liftsByDate = new TreeMap<>();
    private Stage stage;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        FlowPane top = new FlowPane();
        top.setAlignment(Pos.CENTER);

        Button newButton = new Button("New");
        top.getChildren().add(newButton);
        newButton.setOnAction(new NewHandler());

        Button showButton = new Button("Show");
        top.getChildren().add(showButton);
        showButton.setOnAction(new ShowHandler());

        Button calculateTotalButton = new Button("Calculate Total");
        top.getChildren().add(calculateTotalButton);
        calculateTotalButton.setOnAction(new CalculateTotalHandler());

        Button saveButton = new Button("Save");
        top.getChildren().add(saveButton);
        saveButton.setOnAction(new SaveHandler());

        top.setPadding(new Insets(5));
        top.setHgap(5);
        root.setTop(top);

        VBox right = new VBox();
        right.getChildren().add(new Label("Exercises"));
        exercises.setItems(FXCollections.observableArrayList(Exercise.values()));
        right.getChildren().add(exercises);
        exercises.setPrefSize(200,400);
        right.setPadding(new Insets(5));
        right.setSpacing(5);
        root.setRight(right);

        VBox exerciseButtons = new VBox();

        Button showAllButton = new Button("Show All Lifts");
        exerciseButtons.getChildren().add(showAllButton);
        showAllButton.setOnAction(new ShowAllHandler());

        Button showBestButton = new Button("Show Best Lift");
        exerciseButtons.getChildren().add(showBestButton);
        showBestButton.setOnAction(new ShowBestHandler());

        Button calculateButton = new Button("Calculate 1RM");
        exerciseButtons.getChildren().add(calculateButton);
        calculateButton.setOnAction(new CalculateOneRepMaxHandler());

        right.getChildren().add(exerciseButtons);
        exerciseButtons.setAlignment(Pos.CENTER);
        exerciseButtons.setPadding(new Insets(5));
        exerciseButtons.setSpacing(5);

        display = new TextArea();
        display.setEditable(false);
        getLifts();
        root.setCenter(display);

        stage.setScene(new Scene(root));
        stage.sizeToScene();
        stage.setTitle("Workout Tracker");
        stage.setOnCloseRequest(new ExitTopHandler());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    class ExitTopHandler implements EventHandler<WindowEvent>{
        @Override public void handle(WindowEvent event){
            if (changed){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Unsaved changes, exit anyway?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.CANCEL)
                    event.consume();
            }
        }
    }

    class NewHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                display.clear();
                NewDialogue newDialogue = new NewDialogue();
                Exercise exercise = exercises.getSelectionModel().getSelectedItem();
                if (exercise == null){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Exercise not selected");
                    alert.showAndWait();
                    return;
                }
                Optional<ButtonType> answer = newDialogue.showAndWait();
                if (answer.isPresent() && answer.get() == ButtonType.OK) {
                    int sets = newDialogue.getSets();
                    int reps = newDialogue.getReps();
                    double weight = newDialogue.getWeight();
                    Date date = newDialogue.getDate();
                    Lift lift = new Lift(date, exercise, sets, reps, weight);
                    addLift(lift);
                    changed = true;
                    display.appendText(lift.toString());
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Valid number is missing");
                alert.showAndWait();
            }
        }
    }

    class ShowHandler implements EventHandler<ActionEvent>{
        @Override public void handle(ActionEvent event){
            display.clear();
            showLifts();
        }
    }

    class CalculateTotalHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event){
            display.clear();
            double squatWeight = Objects.requireNonNull(getBestLift(Exercise.SQUAT)).getWeight();
            double benchWeight = Objects.requireNonNull(getBestLift(Exercise.BENCHPRESS)).getWeight();
            double deadliftWeight = Objects.requireNonNull(getBestLift(Exercise.DEADLIFT)).getWeight();
            double total = squatWeight + benchWeight + deadliftWeight;
            display.appendText("Total: " + total);
        }
    }

    //TODO needs more testing
    class SaveHandler implements EventHandler<ActionEvent>{
        @Override public void handle(ActionEvent event){
            try {
                //TODO duplicated code, make method
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Workouts");
                fileChooser.setInitialDirectory(new File("C:/"));
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                        new FileChooser.ExtensionFilter("All files", "*.*")
                );
                File file = fileChooser.showOpenDialog(stage);
                if (file == null)
                    return;
                String fileName = file.getAbsolutePath();
                FileWriter outfile = new FileWriter(fileName);
                PrintWriter out = new PrintWriter(outfile);
                for (Lift lift : lifts){
                     out.println(lift.getDate() + "," + lift.getExercise() + "," + lift.getSets() + "," + lift.getReps() + "," + lift.getWeight());
                }
                out.close();
                outfile.close();
                changed = false;
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "File not found");
                alert.showAndWait();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "IOException!");
                alert.showAndWait();
            }
        }
    }

    class ShowAllHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            display.clear();
            Exercise exercise = exercises.getSelectionModel().getSelectedItem();
            if (exercise == null){
                return;
            }
            if (liftsByExercise.get(exercise) == null){
                return;
            }
            ArrayList<Lift> temp = new ArrayList<>(liftsByExercise.get(exercise));
            DateComparator dateCmp = new DateComparator();
            temp.sort(dateCmp);
            for (Lift t : temp){
                display.appendText(t.toString() + "\n");
            }
        }
    }

    class ShowBestHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            display.clear();
            Exercise exercise = exercises.getSelectionModel().getSelectedItem();
            if (exercise == null){
                return;
            }
            Lift bestLift = getBestLift(exercise);
            if (bestLift == null){
                return;
            }
            display.appendText(bestLift.toString());
        }
    }

    class CalculateOneRepMaxHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event){
            display.clear();
            Exercise exercise = exercises.getSelectionModel().getSelectedItem();
            double oneRepMax = calculateOneRepMax(exercise);
            if (oneRepMax == 0){
                return;
            }
            display.appendText(exercise.toString() + ": " + oneRepMax);
        }
    }

    private double calculateOneRepMax(Exercise exercise){
        if (exercise == null){
            return 0;
        }
        double oneRepMax;
        ArrayList<Double> oneRepMaxesList = new ArrayList<>();
        Set<Lift> lifts = liftsByExercise.get(exercise);
        if (lifts == null){
            return 0;
        }
        for (Lift lift : lifts){
            if (lift.getExercise() == exercise){
                int reps = lift.getReps();
                double weight = lift.getWeight();
                if (reps == 1){
                    oneRepMax = weight;
                } else {
                    oneRepMax = weight * (1 + reps / 30.0);
                }
                oneRepMaxesList.add(oneRepMax);
            }
        }
        if(oneRepMaxesList.isEmpty()){
            return 0;
        }
        Collections.sort(oneRepMaxesList);
        oneRepMax = oneRepMaxesList.get(oneRepMaxesList.size() - 1);
        return oneRepMax;
    }

    private void showLifts(){
        display.clear();
        for (Map.Entry<Date, List <Lift>> lift : liftsByDate.entrySet()) {
             ArrayList<Lift> temp = new ArrayList<>(lift.getValue());
             for (Lift t : temp){
                 display.appendText(t.toString() + "\n");
             }
        }
    }

    private Lift getBestLift(Exercise exercise){
        Set<Lift> lifts = liftsByExercise.get(exercise);
        if (lifts == null){
            return null;
        }
        Lift bestLift = null;
        for (Lift lift : lifts){
            if (bestLift == null || lift.getWeight() > bestLift.getWeight()){
                bestLift = lift;
            }
        }
        return bestLift;
    }

    private void getLifts() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Saved Workouts");
            fileChooser.setInitialDirectory(new File("C:/"));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                    new FileChooser.ExtensionFilter("All files", "*.*")
            );
            File file = fileChooser.showOpenDialog(stage);
            if (file == null)
                return;
            String fileName = file.getAbsolutePath();
            FileReader infile = new FileReader(fileName);
            BufferedReader in = new BufferedReader(infile);
            String line;
            while ((line = in.readLine()) != null) {
                String[] tokens = line.split(",");
                Date date = Date.parseDate(tokens[0]);
                String exerciseName = tokens[1];
                Exercise exercise = Exercise.valueOf(exerciseName.toUpperCase().replace(" ",""));
                int sets = Integer.parseInt(tokens[2]);
                int reps = Integer.parseInt(tokens[3]);
                double weight = Double.parseDouble(tokens[4]);
                Lift lift = new Lift(date, exercise, sets, reps, weight);
                addLift(lift);
            }
            in.close();
            infile.close();
            changed = false;
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "File not found");
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "IOException!");
            alert.showAndWait();
        }
    }

    private void addLift(Lift lift){
        addLiftToExercise(lift);
        addLiftToDate(lift);
        lifts.add(lift);
    }

    private void addLiftToExercise(Lift lift){
        Set<Lift> places = liftsByExercise.computeIfAbsent(lift.getExercise(), k -> new HashSet<>());
        places.add(lift);
    }

    private void addLiftToDate(Lift lift){
        List<Lift> places = liftsByDate.computeIfAbsent(lift.getDate(), k -> new ArrayList<>());
        places.add(lift);
    }
}
