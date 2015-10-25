/*
 * @author: Mohit Uniyal
 * 			This is a tool which uses java reflection api, Through this tool user can see all 
 * 			information (method, field, constructor) of a class
 * Date   : 24-10-2015
 */
package gui;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import helper.TableData;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGui extends Application implements EventHandler<ActionEvent> {
	TableView<TableData> table;
	private ObservableList<TableData> tableDataList;
	TableColumn<TableData, String> tblColVal, tblPropColVal;

	private TextField packTxtFld;
	private Label someInfoLbl;

	public MainGui() {
		table = new TableView<>();
		tableDataList = FXCollections.observableArrayList();
		// to make column expand to table width
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stg) throws Exception {
		BorderPane mainPane = new BorderPane();
		stg.setTitle("Class Info Tool: By Mohit Uniyal");
		Scene sc = new Scene(mainPane, 640, 480);
		// adding buttons, text field and table to the main interface
		addGuiComponents(mainPane);
		stg.setScene(sc);
		stg.show();
	}

	private void addGuiComponents(BorderPane bp) {
		Label toolNameLbl = new Label("ClassInfoTool");

		toolNameLbl
				.setStyle("-fx-font-size:20px \"Serif\"; -fx-text-fill: blue;");

		VBox outerTopVb = new VBox();
		HBox innerFirstHb = new HBox();

		innerFirstHb.setPadding(new Insets(20, 0, 0, 0));

		innerFirstHb.getChildren().add(toolNameLbl);
		innerFirstHb.setAlignment(Pos.CENTER);

		outerTopVb.getChildren().addAll(innerFirstHb);
		// add buttons
		addTopVboxEle(outerTopVb);

		bp.setTop(outerTopVb);

		// adding center table
		addTableToCeter(bp);

		// adding footer components
		someInfoLbl = new Label("");
		HBox bpBottomHb = new HBox();
		bpBottomHb.setAlignment(Pos.CENTER_RIGHT);
		bpBottomHb.setPadding(new Insets(0, 10, 10, 0));
		bpBottomHb.getChildren().add(someInfoLbl);
		someInfoLbl.setStyle("-fx-font-size:15px");
		bp.setBottom(bpBottomHb);
	}

	// to display all information to the table
	private void addTableToCeter(BorderPane bp) {
		table.setItems(tableDataList);

		VBox tableHb = new VBox();
		tableHb.getChildren().add(table);
		tableHb.setAlignment(Pos.CENTER);
		tableHb.setPadding(new Insets(10, 10, 5, 10));
		bp.setCenter(tableHb);
	}

	// adding buttons that will be used to display all the class information
	private void addTopVboxEle(VBox outerTopVb) {
		HBox innerSecHb = new HBox(10);

		innerSecHb.setAlignment(Pos.CENTER);
		innerSecHb.setPadding(new Insets(20, 0, 0, 0));

		packTxtFld = new TextField();

		Button basicInfoBtn = new Button("Basic Info");
		Button constructorBtn = new Button("Constructors");
		Button fieldBtn = new Button("Fields");
		Button methodsBtn = new Button("Methods");

		// set focus to the button
		Platform.runLater(new Runnable() {
			public void run() {
				constructorBtn.requestFocus();
			}
		});

		packTxtFld.setPromptText("java.lang.String");

		innerSecHb.getChildren().addAll(packTxtFld, basicInfoBtn,
				constructorBtn, fieldBtn, methodsBtn);
		outerTopVb.getChildren().add(innerSecHb);

		// setting event handlers
		constructorBtn.setOnAction(this);
		fieldBtn.setOnAction(this);
		methodsBtn.setOnAction(this);
		basicInfoBtn.setOnAction(this);
	}

	@SuppressWarnings("unchecked")
	public void handle(ActionEvent ae) {
		Button clickedBtn = (Button) ae.getSource();
		String buttonTxtStr = clickedBtn.getText();
		String packStrName = "";

		if (!packTxtFld.getText().equals("")) {
			packStrName = packTxtFld.getText();
		}

		// handling basicButton
		if ("Basic Info".equals(buttonTxtStr)) {
			table.getColumns().clear();

			tblColVal = new TableColumn<>(buttonTxtStr);
			tblColVal
					.setCellValueFactory(new PropertyValueFactory<>("propCol"));

			tblPropColVal = new TableColumn<>("Property");
			tblPropColVal.setCellValueFactory(new PropertyValueFactory<>(
					"propColValue"));
			
			table.getColumns().addAll(tblColVal, tblPropColVal);
		} else {
			//getting particular values like methods, fileds..
			table.getColumns().clear();
			tblColVal = new TableColumn<>(buttonTxtStr);
			tblColVal.setCellValueFactory(new PropertyValueFactory<>(
					"tableColValue"));
			table.getColumns().addAll(tblColVal);
		}

		System.out.println(buttonTxtStr);
		switch (buttonTxtStr) {
		case "Constructors":
			getConstructorData(packStrName);
			break;
		case "Fields":
			getFieldsData(packStrName);
			break;
		case "Methods":
			getmethodsData(packStrName);
			break;
		case "Basic Info":
			getClassBasicInfo(packStrName);
			break;
		}
	}

	// added 25-10-2015
	private void getClassBasicInfo(String packStrName) {
		try {
			Class<?> c = Class.forName(packStrName);
			// empty previous list data and display new values
			tableDataList.removeAll(tableDataList);

			tableDataList.add(new TableData("Name", c.getName()));
			tableDataList.add(new TableData("Canonical Name", ""
					+ c.getCanonicalName()));
			tableDataList.add(new TableData("Simple Name", ""
					+ c.getSimpleName()));
			tableDataList.add(new TableData("Type Name", "" + c.getTypeName()));
			tableDataList.add(new TableData("Generic String", c
					.toGenericString()));
			tableDataList
					.add(new TableData("Annotation", "" + c.isAnnotation()));
			tableDataList.add(new TableData("Anonymous Class", ""
					+ c.isAnonymousClass()));
			tableDataList.add(new TableData("Enum", "" + c.isEnum()));
			tableDataList.add(new TableData("Interface", "" + c.isInterface()));
			tableDataList.add(new TableData("Local Class", "" + c.isLocalClass()));
			tableDataList.add(new TableData("Member Class", "" + c.isMemberClass()));
			tableDataList.add(new TableData("Primitive", "" + c.isPrimitive()));
			tableDataList.add(new TableData("Synthetic", "" + c.isSynthetic()));
			
		} catch (Exception e) {
			displayAlert();
			System.err.println(e.getMessage());
		}
	}

	private void getConstructorData(String packStrName) {
		try {
			Class<?> c = Class.forName(packStrName);
			Constructor<?> cons[] = c.getConstructors();
			int i = 0;
			// empty previous list data and display new values
			tableDataList.removeAll(tableDataList);
			for (; i < cons.length; i++) {
				tableDataList.add(new TableData(cons[i].toString()));
			}
			someInfoLbl.setText("Total Constructor : " + (i));
		} catch (Exception e) {
			displayAlert();
			System.err.println(e.getMessage());
		}
	}

	private void getFieldsData(String packStrName) {
		try {
			Class<?> c = Class.forName(packStrName);
			Field cons[] = c.getFields();
			int i = 0;
			// empty previous list data and display new values
			tableDataList.removeAll(tableDataList);
			for (; i < cons.length; i++) {
				tableDataList.add(new TableData(cons[i].toString()));
			}
			someInfoLbl.setText("Total Fields : " + (i));
		} catch (Exception e) {
			displayAlert();
			System.err.println(e.getMessage());
		}
	}

	private void getmethodsData(String packStrName) {
		try {
			Class<?> c = Class.forName(packStrName);
			Method cons[] = c.getMethods();
			int i = 0;
			// empty previous list data and display new values
			tableDataList.removeAll(tableDataList);
			for (; i < cons.length; i++) {
				tableDataList.add(new TableData(cons[i].toString()));
			}
			someInfoLbl.setText("Total Methods : " + (i));
		} catch (Exception e) {
			displayAlert();
			System.err.println(e.getMessage());
		}
	}

	private void displayAlert() {
		Alert alertBox = new Alert(AlertType.ERROR);
		alertBox.setHeaderText("Text input error");
		alertBox.setTitle("Error Occured!");
		alertBox.setContentText("Enter Valid Package and Class Name");
		alertBox.showAndWait();
	}
}
