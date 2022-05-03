package application;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class WholeSceneController implements Initializable{

	private ObservableList<Tasks> Data = FXCollections.observableArrayList(
			new Tasks("Task1", "18-05-1985", "Desc1", "Details1", "important")
	);

	private String[] Category = {"Important!!!","In Progress...","Someday"};

    @FXML
    private TableView<Tasks> tv_tasks;

    @FXML
    private TableColumn<Tasks, String> col_taskName;

    @FXML
    private TableColumn<Tasks, String> col_taskDescription;

    @FXML
    private TableColumn<Tasks, String> col_Details;

    @FXML
    private TableColumn<Tasks, String> col_date;

    @FXML
    private TableColumn<Tasks, String> col_category;

    @FXML
    private Button submitTask;

    @FXML
    private Button saveTasks;

    @FXML
    private TextField tf_taskName;

    @FXML
    private DatePicker dateTask;

    @FXML
    private TextField tf_taskDescription;

    @FXML
    private TextField tf_taskDetails;

    @FXML
    private TextField tf_hour;

    @FXML
    private TextField tf_minutes;

    @FXML
    private ChoiceBox<String> cb_category;

    @FXML
    void handleSaveTasks(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(getPrimaryStage());

		if (file != null) {
			// Make sure it has the correct extension
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			saveTasksToFile(file);
		}
    }

    @FXML
    void handleSubmitTask(ActionEvent event) {
    	Tasks NewTask = new Tasks(
				tf_taskName.getText(),
				dateTask.getValue().toString()+ " at " + tf_hour.getText() + " h " + tf_minutes.getText() + " min.",
				tf_taskDescription.getText(),
				tf_taskDetails.getText(),
				cb_category.getValue()
	);
    	Data.add(NewTask);
    }

    public Stage getPrimaryStage() {
		Stage primaryStage = (Stage) tv_tasks.getScene().getWindow();
		return primaryStage;
	}


    public void showTasksList() {

    	ObservableList<Tasks> Data = getData();

		col_taskName.setCellValueFactory(new PropertyValueFactory<>("TaskName"));
		col_taskDescription.setCellValueFactory(new PropertyValueFactory<>("TaskDescription"));
		col_Details.setCellValueFactory(new PropertyValueFactory<>("TaskDetails"));
		col_date.setCellValueFactory(new PropertyValueFactory<>("TaskDate"));
		col_category.setCellValueFactory(new PropertyValueFactory<>("TaskCategory"));

		tv_tasks.setItems(Data);

	}

    private ObservableList<Tasks> getData() {
		return Data;
	}

    public File getTasksFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(WholeSceneController.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setTasksFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(WholeSceneController.class);
        Stage primaryStage = (Stage) tv_tasks.getScene().getWindow();
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("Base Scene : " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("Base Scene...");
        }
    }

    //---------------------------------------------------------------------------------------------


    public void saveTasksToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TasksModelForJAXB.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            TasksModelForJAXB model = new TasksModelForJAXB();
            model.setTasks(Data);

            // Marshalling and saving XML to the file.
            m.marshal(model, file);

            // Save the file path to the registry.
            setTasksFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void loadTasksFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TasksModelForJAXB.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            TasksModelForJAXB model = (TasksModelForJAXB) um.unmarshal(file);

            Data.clear();
            List<Tasks> list = model.getTasks();
            System.out.println(list);
            Data.addAll(list);

            // Save the file path to the registry.
            setTasksFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
    	showTasksList();
		cb_category.getItems().addAll(Category);
	}

}
