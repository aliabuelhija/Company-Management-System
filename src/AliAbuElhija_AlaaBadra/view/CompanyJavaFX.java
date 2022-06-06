package AliAbuElhija_AlaaBadra.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import AliAbuElhija_AlaaBadra.listeners.CompanyUIEventListener;
import AliAbuElhija_AlaaBadra.model.Department;
import AliAbuElhija_AlaaBadra.model.Preference;
import AliAbuElhija_AlaaBadra.model.Role;

public class CompanyJavaFX {

	private ArrayList<CompanyUIEventListener> listeners;

	private Label addDepartmentResponse;
	private Label addRuleResponse;
	private Label addEmployeeResponse;
	private Label changeRolePreferenceResponse;
	private Label changeDepartmentPreferenceResponse;
	private Label setBonusResponse;
	private FlowPane departmentsFp;
	private FlowPane gainAndLossFp;

	public CompanyJavaFX(Stage stage) {
		listeners = new ArrayList<>();

		stage.setTitle("Company - Menu");

		Button setBonusBtn = new Button("Set Bonus");
		setBonusBtn.setOnAction(e -> {
			handleSetBonus();
		});

		Button addDepartmentBtn = new Button("Add Department");
		addDepartmentBtn.setOnAction(e -> {
			handleAddDepartment();
		});

		Button addRoleBtn = new Button("Add Role");
		addRoleBtn.setOnAction(e -> {
			handleAddRole();
		});

		Button addEmployeeBtn = new Button("Add Employee");
		addEmployeeBtn.setOnAction(e -> {
			handleAddEmployee();
		});

		Button showDetailsBtn = new Button("Show Company Details");
		showDetailsBtn.setOnAction(e -> {
			handleShowDetails();
		});

		Button changeRoleWorkingMethodBtn = new Button("Change Role Working Method");
		changeRoleWorkingMethodBtn.setOnAction(e -> {
			handleChangeRoleWorkingMethod();
		});

		Button changeDepartmentWorkingMethodBtn = new Button("Change Department Working Method");
		changeDepartmentWorkingMethodBtn.setOnAction(e -> {
			handleChangeDepartmentWorkingMethod();
		});

		Button showGainAndLossBtn = new Button("Show Gain & Loss");
		showGainAndLossBtn.setOnAction(e -> {
			handleShowGainAndLoss();
		});

		Button exitBtn = new Button();
		exitBtn.setText("Exit");
		exitBtn.setOnAction(e -> {
			handleExit();
			Platform.exit();
		});

		VBox vbRoot = createStandardVBox();
		vbRoot.setAlignment(Pos.CENTER);
		vbRoot.getChildren().addAll(setBonusBtn, addDepartmentBtn, addRoleBtn, addEmployeeBtn, showDetailsBtn,
				changeRoleWorkingMethodBtn, changeDepartmentWorkingMethodBtn, showGainAndLossBtn, exitBtn);

		stage.setScene(new Scene(vbRoot, 350, 310));
		stage.show();
	}

	private void handleSetBonus() {
		VBox vbRoot = createStandardVBox();

		Stage stage = new Stage();
		stage.setTitle("Set Bonus");

		TextField monthlySellsTb = new TextField();
		HBox monthlySellsHb = createLabelWithTextFieldHBox(monthlySellsTb, "Monthly Sells: ");

		TextField monthlyBonusPercentTb = new TextField();
		HBox monthlyBonusPercentHb = createLabelWithTextFieldHBox(monthlyBonusPercentTb, "Monthly Bonus Percent: ");

		setBonusResponse = new Label("");

		Button saveBtn = new Button("Save");
		Button closeBtn = new Button("Close");

		saveBtn.setOnAction(e -> {
			if (!isInteger(monthlySellsTb.getText())) {
				showResponse(setBonusResponse, "ERROR", "Monthly sells need to be integer.");
				return;
			}
			if (!isInteger(monthlyBonusPercentTb.getText())) {
				showResponse(setBonusResponse, "ERROR", "Monthly bonus percent need to be integer.");
				return;
			}
			int monthlySells = Integer.parseInt(monthlySellsTb.getText());
			int monthlyBonusPercent = Integer.parseInt(monthlyBonusPercentTb.getText());
			listeners.forEach(l -> l.saveBonusToUIEvent(monthlySells, monthlyBonusPercent));
			showResponse(setBonusResponse, "OK", "Saved!");
		});

		closeBtn.setOnAction(e -> stage.close());
		HBox hbFooter = createFooter(saveBtn, closeBtn);

		vbRoot.getChildren().addAll(monthlySellsHb, monthlyBonusPercentHb, setBonusResponse, hbFooter);
		stage.setScene(new Scene(vbRoot, 340, 200));
		stage.show();
	}

	private void handleShowGainAndLoss() {
		Stage stage = new Stage();
		stage.setTitle("Show Gain & Loss");
		gainAndLossFp = new FlowPane();
		gainAndLossFp.setPadding(new Insets(10));
		listeners.forEach(CompanyUIEventListener::showGainAndLossToUIEvent);

		stage.setScene(new Scene(gainAndLossFp, 600, 500));
		stage.show();
	}

	private void handleChangeDepartmentWorkingMethod() {
		VBox vbRoot = createStandardVBox();

		Label selectDepartmentLbl = new Label("Select Department");
		List<String> departments = listeners.get(0).getDepartmentsToUIEvent().stream().map(Department::getName)
				.collect(Collectors.toList());

		ListView<String> selectDepartment = createListView(departments);

		Label preference = new Label("Preference:");
		ToggleGroup tglFlag = new ToggleGroup();
		RadioButton rdoEarly = new RadioButton("Early.");
		RadioButton rdoLate = new RadioButton("Late.");
		RadioButton rdoStandard = new RadioButton("Standard.");

		rdoEarly.setToggleGroup(tglFlag);
		rdoLate.setToggleGroup(tglFlag);
		rdoStandard.setToggleGroup(tglFlag);

		rdoStandard.setSelected(true);

		Label hoers = new Label("");
		hoers.setVisible(false);
		TextField tfPreference = new TextField();
		tfPreference.setVisible(false);

		changeDepartmentPreferenceResponse = new Label();

		rdoStandard.setOnAction(e -> {
			tfPreference.setVisible(!rdoStandard.isSelected());
			hoers.setVisible(!rdoStandard.isSelected());
			changeDepartmentPreferenceResponse.setText("");
		});
		rdoEarly.setOnAction(e -> {
			tfPreference.setVisible(rdoEarly.isSelected());
			hoers.setText("Insert how early in hoers.");
			hoers.setVisible(rdoEarly.isSelected());
			changeDepartmentPreferenceResponse.setText("");
		});
		rdoLate.setOnAction(e -> {
			tfPreference.setVisible(rdoLate.isSelected());
			hoers.setText("Insert how late in hoers.");
			hoers.setVisible(rdoLate.isSelected());
			changeDepartmentPreferenceResponse.setText("");
		});

		Button changeBtn = new Button("Change");
		Button closeBtn = new Button("Close");

		changeBtn.setOnAction(e -> {
			if (selectDepartment.getSelectionModel().isEmpty()) {
				showResponse(changeDepartmentPreferenceResponse, "ERROR", "Please select department");
				return;
			}
			if ((rdoEarly.isSelected() || rdoLate.isSelected()) && !isInteger(tfPreference.getText())) {
				showResponse(changeDepartmentPreferenceResponse, "ERROR", "Please enter hoers in integer.");
				return;
			}
			if (rdoStandard.isSelected())
				listeners.forEach(l -> l.changeDepartmentPreferenceToUIEvent(0,
						selectDepartment.getSelectionModel().getSelectedItem()));
			else if (rdoEarly.isSelected())
				listeners.forEach(
						l -> l.changeDepartmentPreferenceToUIEvent(Integer.parseInt(tfPreference.getText()) * -1,
								selectDepartment.getSelectionModel().getSelectedItem()));
			else
				listeners.forEach(l -> l.changeDepartmentPreferenceToUIEvent(Integer.parseInt(tfPreference.getText()),
						selectDepartment.getSelectionModel().getSelectedItem()));
		});

		if (departments.isEmpty()) {
			showResponse(changeDepartmentPreferenceResponse, "Error", "No Departments Available.");
			changeBtn.setDisable(true);
		}

		Stage stage = new Stage();
		stage.setTitle("Change Department Working Method");

		closeBtn.setOnAction(e -> stage.close());
		HBox hbFooter = createFooter(changeBtn, closeBtn);

		vbRoot.getChildren().addAll(selectDepartmentLbl, selectDepartment, preference, rdoStandard, rdoEarly, rdoLate,
				hoers, tfPreference, changeDepartmentPreferenceResponse, hbFooter);
		stage.setScene(new Scene(vbRoot, 350, 350));
		stage.show();
	}

	private void handleChangeRoleWorkingMethod() {
		VBox vbRoot = createStandardVBox();
		Button changeBtn = new Button("Change");
		Button closeBtn = new Button("Close");

		Label selectDepartmentLbl = new Label("Select Department");

		List<String> departments = listeners.get(0).getDepartmentsToUIEvent().stream().map(Department::getName)
				.collect(Collectors.toList());
		ListView<String> selectDepartment = createListView(departments);

		Label selectRuleLbl = new Label("Select Rule");

		ListView<String> selectRole = new ListView<>();
		selectRole.setPrefHeight(70);

		selectDepartment.setOnMouseClicked(event -> {
			if (selectDepartment.getSelectionModel().isEmpty())
				return;
			List<String> roles = listeners.get(0)
					.getRolesByDepartmentNameToUIEvent(selectDepartment.getSelectionModel().getSelectedItem()).stream()
					.map(Role::getName).collect(Collectors.toList());
			if (roles.isEmpty()) {
				showResponse(changeRolePreferenceResponse, "Error", "No roles in this department.");
				changeBtn.setDisable(true);
				return;
			} else {
				showResponse(changeRolePreferenceResponse, "OK", "");
				changeBtn.setDisable(false);
			}
			ObservableList<String> rolesNames = FXCollections.observableArrayList(roles);
			selectRole.setItems(rolesNames);
		});

		Label preference = new Label("Preference:");
		ToggleGroup tglFlag = new ToggleGroup();
		RadioButton rdoEarly = new RadioButton("Early.");
		RadioButton rdoLate = new RadioButton("Late.");
		RadioButton rdoStandard = new RadioButton("Standard.");

		rdoEarly.setToggleGroup(tglFlag);
		rdoLate.setToggleGroup(tglFlag);
		rdoStandard.setToggleGroup(tglFlag);

		rdoStandard.setSelected(true);

		Label hoers = new Label("");
		hoers.setVisible(false);
		TextField tfPreference = new TextField();
		tfPreference.setVisible(false);

		changeRolePreferenceResponse = new Label();

		rdoStandard.setOnAction(e -> {
			tfPreference.setVisible(!rdoStandard.isSelected());
			hoers.setVisible(!rdoStandard.isSelected());
			changeRolePreferenceResponse.setText("");
		});
		rdoEarly.setOnAction(e -> {
			tfPreference.setVisible(rdoEarly.isSelected());
			hoers.setText("Insert how early in hoers.");
			hoers.setVisible(rdoEarly.isSelected());
			changeRolePreferenceResponse.setText("");
		});
		rdoLate.setOnAction(e -> {
			tfPreference.setVisible(rdoLate.isSelected());
			hoers.setText("Insert how late in hoers.");
			hoers.setVisible(rdoLate.isSelected());
			changeRolePreferenceResponse.setText("");
		});

		changeBtn.setOnAction(e -> {
			if (selectDepartment.getSelectionModel().isEmpty()) {
				showResponse(changeRolePreferenceResponse, "ERROR", "Please select department");
				return;
			}
			if (selectRole.getSelectionModel().isEmpty()) {
				showResponse(changeRolePreferenceResponse, "ERROR", "Please select role");
				return;
			}
			if ((rdoEarly.isSelected() || rdoLate.isSelected()) && !isInteger(tfPreference.getText())) {
				showResponse(changeRolePreferenceResponse, "ERROR", "Please enter hoers in integer.");
				return;
			}
			if (rdoStandard.isSelected())
				listeners.forEach(
						l -> l.changeRolePreferenceToUIEvent(0, selectDepartment.getSelectionModel().getSelectedItem(),
								selectRole.getSelectionModel().getSelectedItem()));
			else if (rdoEarly.isSelected())
				listeners.forEach(l -> l.changeRolePreferenceToUIEvent(Integer.parseInt(tfPreference.getText()) * -1,
						selectDepartment.getSelectionModel().getSelectedItem(),
						selectRole.getSelectionModel().getSelectedItem()));
			else
				listeners.forEach(l -> l.changeRolePreferenceToUIEvent(Integer.parseInt(tfPreference.getText()),
						selectDepartment.getSelectionModel().getSelectedItem(),
						selectRole.getSelectionModel().getSelectedItem()));
		});

		if (departments.isEmpty()) {
			showResponse(changeRolePreferenceResponse, "Error",
					"Before you change role you need to create it within department.");
			changeBtn.setDisable(true);
		}

		Stage stage = new Stage();
		stage.setTitle("Change Role Working Method");

		closeBtn.setOnAction(e -> stage.close());
		HBox hbFooter = createFooter(changeBtn, closeBtn);

		vbRoot.getChildren().addAll(selectDepartmentLbl, selectDepartment, selectRuleLbl, selectRole, preference,
				rdoStandard, rdoEarly, rdoLate, hoers, tfPreference, changeRolePreferenceResponse, hbFooter);
		stage.setScene(new Scene(vbRoot, 350, 450));
		stage.show();
	}

	private HBox createFooter(Button... buttons) {
		HBox hbFooter = new HBox();
		hbFooter.setSpacing(5);
		hbFooter.setPadding(new Insets(10));
		hbFooter.getChildren().addAll(buttons);
		return hbFooter;
	}

	private void handleShowDetails() {
		Stage stage = new Stage();
		stage.setTitle("Show Departments");
		departmentsFp = new FlowPane();
		departmentsFp.setPadding(new Insets(10));
		listeners.forEach(CompanyUIEventListener::showDetailsToUIEvent);

		stage.setScene(new Scene(departmentsFp, 600, 500));
		stage.show();
	}

	private void handleAddDepartment() {
		VBox vbRoot = createStandardVBox();

		TextField tfName = new TextField();
		HBox hbName = createLabelWithTextFieldHBox(tfName, "Name:");

		CheckBox synchronizeCb = new CheckBox("Synchronize.");
		CheckBox canChangeCb = new CheckBox("Can change.");

		addDepartmentResponse = new Label("");

		Button addBtn = new Button("Add");
		Button closeBtn = new Button("Close");
		addBtn.setOnAction(e -> {
			listeners.forEach(l -> l.addDepartmentToUIEvent(tfName.getText(), canChangeCb.isSelected(),
					synchronizeCb.isSelected()));
			tfName.setText("");
		});

		Stage stage = new Stage();
		stage.setTitle("Add Department");
		closeBtn.setOnAction(e -> stage.close());
		HBox hbFooter = createFooter(addBtn, closeBtn);

		vbRoot.getChildren().addAll(hbName, synchronizeCb, canChangeCb, addDepartmentResponse, hbFooter);
		stage.setScene(new Scene(vbRoot, 350, 250));
		stage.show();
	}

	private void handleAddRole() {
		VBox vbRoot = createStandardVBox();

		TextField tfName = new TextField();
		HBox hbName = createLabelWithTextFieldHBox(tfName, "Name:");

		CheckBox isChangeable = new CheckBox("Is changeable");
		CheckBox isSynchronize = new CheckBox("Is synchronize");
		CheckBox allowHomeWorking = new CheckBox("Allow working from home");

		Label selectDepartmentLbl = new Label("Select Department");

		List<String> departments = listeners.get(0).getDepartmentsToUIEvent().stream().map(Department::getName)
				.collect(Collectors.toList());
		ListView<String> selectDepartment = createListView(departments);

		addRuleResponse = new Label("");

		Button addBtn = new Button("Add");
		Button closeBtn = new Button("Close");
		addBtn.setOnAction(e -> {
			if (tfName.getText().isBlank()) {
				showResponse(addRuleResponse, "ERROR", "Please insert name.");
				return;
			}
			if (selectDepartment.getSelectionModel().isEmpty()) {
				showResponse(addRuleResponse, "ERROR", "Please select department.");
				return;
			}
			listeners.forEach(
					l -> l.addRoleToUIEvent(tfName.getText(), isChangeable.isSelected(), isSynchronize.isSelected(),
							allowHomeWorking.isSelected(), selectDepartment.getSelectionModel().getSelectedItem()));
			tfName.setText("");
		});

		if (departments.isEmpty()) {
			showResponse(addRuleResponse, "Error", "Before you enter role you need to create department");
			addBtn.setDisable(true);
		}

		Stage stage = new Stage();
		stage.setTitle("Add Role");
		closeBtn.setOnAction(e -> stage.close());
		HBox hbFooter = createFooter(addBtn, closeBtn);

		vbRoot.getChildren().addAll(hbName, isChangeable, isSynchronize, allowHomeWorking, selectDepartmentLbl,
				selectDepartment, addRuleResponse, hbFooter);
		stage.setScene(new Scene(vbRoot, 370, 300));
		stage.show();
	}

	private ListView<String> createListView(List<String> list) {
		ListView<String> listView = new ListView<>();
		ObservableList<String> items = FXCollections.observableArrayList(list);
		listView.setItems(items);
		listView.setPrefHeight(70);
		return listView;
	}

	private void handleAddEmployee() {
		VBox vbRoot = createStandardVBox();

		TextField tfName = new TextField();
		HBox hbName = createLabelWithTextFieldHBox(tfName, "Name:");

		Label salaryMethod = new Label("salaryMethod:");
		ToggleGroup tglSalary = new ToggleGroup();
		RadioButton rdoHourly = new RadioButton("Hourly pay.");
		RadioButton rdoBase = new RadioButton("Base pay.");
		RadioButton rdoBasePlusBonus = new RadioButton("From Base pay + Bonus.");

		rdoHourly.setToggleGroup(tglSalary);
		rdoBase.setToggleGroup(tglSalary);
		rdoBasePlusBonus.setToggleGroup(tglSalary);
		rdoHourly.setSelected(true);

		TextField tfSalary = new TextField();
		HBox hbSalary = createLabelWithTextFieldHBox(tfSalary, "Hourly pay:");

		rdoHourly.setOnAction(e -> {
			((Label) (hbSalary.getChildren().get(0))).setText("Hourly pay:");
		});

		rdoBase.setOnAction(e -> {
			((Label) (hbSalary.getChildren().get(0))).setText("Base pay:");
		});

		rdoBasePlusBonus.setOnAction(e -> {
			((Label) (hbSalary.getChildren().get(0))).setText("Base pay:");
		});

		VBox salaryVb = createStandardVBox();
		salaryVb.getChildren().addAll(salaryMethod, rdoHourly, rdoBase, rdoBasePlusBonus, hbSalary);

		Label selectDepartmentLbl = new Label("Select Department");

		List<String> departments = listeners.get(0).getDepartmentsToUIEvent().stream().map(Department::getName)
				.collect(Collectors.toList());
		ListView<String> selectDepartment = createListView(departments);

		Label selectRuleLbl = new Label("Select Rule");
		ListView<String> selectRole = createListView(new ArrayList<>());

		selectDepartment.setOnMouseClicked(event -> {
			if (selectDepartment.getSelectionModel().isEmpty())
				return;
			List<String> roles = listeners.get(0)
					.getRolesByDepartmentNameToUIEvent(selectDepartment.getSelectionModel().getSelectedItem()).stream()
					.map(Role::getName).collect(Collectors.toList());
			ObservableList<String> rolesNames = FXCollections.observableArrayList(roles);
			selectRole.setItems(rolesNames);
		});

		Label preference = new Label("Preference:");
		ToggleGroup tglFlag = new ToggleGroup();
		RadioButton rdoEarly = new RadioButton("Early.");
		RadioButton rdoLate = new RadioButton("Late.");
		RadioButton rdoHome = new RadioButton("From Home.");
		RadioButton rdoStandard = new RadioButton("Standard.");

		rdoEarly.setToggleGroup(tglFlag);
		rdoLate.setToggleGroup(tglFlag);
		rdoHome.setToggleGroup(tglFlag);
		rdoStandard.setToggleGroup(tglFlag);

		rdoStandard.setSelected(true);

		Label hoers = new Label("");
		hoers.setVisible(false);
		TextField tfPreference = new TextField();
		tfPreference.setVisible(false);

		rdoStandard.setOnAction(e -> {
			tfPreference.setVisible(!rdoStandard.isSelected());
			hoers.setVisible(!rdoStandard.isSelected());
			addEmployeeResponse.setText("");
		});
		rdoHome.setOnAction(e -> {
			tfPreference.setVisible(!rdoHome.isSelected());
			hoers.setVisible(!rdoHome.isSelected());
			addEmployeeResponse.setText("");
		});
		rdoEarly.setOnAction(e -> {
			tfPreference.setVisible(rdoEarly.isSelected());
			hoers.setText("Insert how early in hoers.");
			hoers.setVisible(rdoEarly.isSelected());
			addEmployeeResponse.setText("");
		});
		rdoLate.setOnAction(e -> {
			tfPreference.setVisible(rdoLate.isSelected());
			hoers.setText("Insert how late in hoers.");
			hoers.setVisible(rdoLate.isSelected());
			addEmployeeResponse.setText("");
		});

		addEmployeeResponse = new Label("");

		VBox vbPreference = createStandardVBox();
		vbPreference.getChildren().addAll(preference, rdoStandard, rdoEarly, rdoLate, rdoHome, hoers, tfPreference,
				addEmployeeResponse);

		Button addBtn = new Button("Add");
		Button closeBtn = new Button("Close");

		addBtn.setOnAction(e -> {
			if (tfName.getText().isBlank()) {
				showResponse(addEmployeeResponse, "ERROR", "Please enter employee name.");
				return;
			}
			if (!isInteger(tfSalary.getText())) {
				showResponse(addEmployeeResponse, "ERROR", "Salary need to be integer.");
				return;
			}
			if (selectDepartment.getSelectionModel().isEmpty()) {
				showResponse(addEmployeeResponse, "ERROR", "Please select department.");
				return;
			}
			if (selectRole.getSelectionModel().isEmpty()) {
				showResponse(addEmployeeResponse, "ERROR", "Please select role.");
				return;
			}
			if ((rdoEarly.isSelected() || rdoLate.isSelected()) && !isInteger(tfPreference.getText())) {
				showResponse(addEmployeeResponse, "ERROR", "Hoers have to be integer.");
				return;
			}
			int salary;
			if (rdoHourly.isSelected())
				salary = Integer.parseInt(tfSalary.getText());
			else
				salary = Integer.parseInt(tfSalary.getText()) / 160;
			boolean addBonus = rdoBasePlusBonus.isSelected();
			if (rdoStandard.isSelected() || rdoHome.isSelected()) {
				listeners.forEach(l -> l.addEmployeeToUIEvent(tfName.getText(), new Preference(rdoHome.isSelected()),
						selectDepartment.getSelectionModel().getSelectedItem(),
						selectRole.getSelectionModel().getSelectedItem(), salary, addBonus));
			} else {
				final int time;
				if (rdoEarly.isSelected())
					time = 8 - Integer.parseInt(tfPreference.getText());
				else
					time = 8 + Integer.parseInt(tfPreference.getText());
				listeners.forEach(l -> l.addEmployeeToUIEvent(tfName.getText(), new Preference(time),
						selectDepartment.getSelectionModel().getSelectedItem(),
						selectRole.getSelectionModel().getSelectedItem(), Integer.parseInt(tfSalary.getText()),
						addBonus));
			}
			tfName.setText("");
		});

		if (departments.isEmpty()) {
			showResponse(addEmployeeResponse, "Error", "Before you enter employee you need to create department");
			addBtn.setDisable(true);
		}

		Stage stage = new Stage();
		stage.setTitle("Add Employee");

		closeBtn.setOnAction(e -> stage.close());
		HBox hbFooter = createFooter(addBtn, closeBtn);

		vbRoot.getChildren().addAll(hbName, salaryVb, selectDepartmentLbl, selectDepartment, selectRuleLbl, selectRole,
				vbPreference, hbFooter);
		stage.setScene(new Scene(vbRoot, 370, 680));
		stage.show();
	}

	private void handleExit() {
		listeners.forEach(CompanyUIEventListener::exitToUIEvent);
	}

	private VBox createStandardVBox() {
		VBox vBox = new VBox();
		vBox.setSpacing(5);
		vBox.setPadding(new Insets(10));
		return vBox;
	}

	private HBox createLabelWithTextFieldHBox(TextField textField, String labelText) {
		Label label = new Label(labelText);
		HBox hBox = new HBox();
		hBox.setSpacing(5);
		hBox.setPadding(new Insets(10));
		hBox.getChildren().addAll(label, textField);
		return hBox;
	}

	public void registerListener(CompanyUIEventListener newListener) {
		listeners.add(newListener);
	}

	public void showAddDepartmentResponse(String status, String msg) {
		showResponse(addDepartmentResponse, status, msg);
	}

	public void showAddRuleResponse(String status, String msg) {
		showResponse(addRuleResponse, status, msg);
	}

	public void showAddEmployeeResponse(String status, String msg) {
		showResponse(addEmployeeResponse, status, msg);
	}

	public void showChangeRolePreferenceResponse(String status, String msg) {
		showResponse(changeRolePreferenceResponse, status, msg);
	}

	public void showChangeDepartmentPreferenceResponse(String status, String msg) {
		showResponse(changeDepartmentPreferenceResponse, status, msg);
	}

	public void showResponse(Label label, String status, String msg) {
		if (status.equals("OK"))
			label.setTextFill(Color.GREEN);
		else
			label.setTextFill(Color.RED);
		label.setText(msg);
	}

	private boolean isInteger(String string) {
		Pattern pattern = Pattern.compile("\\d+");
		return pattern.matcher(string).matches();
	}

	public void showDetails(List<Department> departments) {
		departmentsFp.getChildren().removeAll();
		VBox departmentsVb = new VBox();
		departmentsVb.setSpacing(5);
		departmentsVb.setPadding(new Insets(10));
		for (Department department : departments) {
			departmentsVb.getChildren().add(new Label(department.toString()));
		}
		departmentsFp.getChildren().add(departmentsVb);
	}

	public void showGainAndLoss(List<Department> departments) {
		gainAndLossFp.getChildren().removeAll();
		VBox gainAndLossVb = new VBox();
		gainAndLossVb.setSpacing(5);
		gainAndLossVb.setPadding(new Insets(10));
		for (Department department : departments) {
			gainAndLossVb.getChildren().add(new Label(department.gainAndLoss()));
		}
		gainAndLossFp.getChildren().add(gainAndLossVb);
	}
}