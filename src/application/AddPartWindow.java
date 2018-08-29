package application;

import java.util.Random;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
public class AddPartWindow {

	public static void display(){
		final Double TEXT_FIELD_WIDTH = 100.0;
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Add Part");
		
		//Window Label
		Label addPartLabel = new Label ("Add Part");
		addPartLabel.setPadding (new Insets(0,40,0,0));
		HBox windowLabelHBox = new HBox(35);
		windowLabelHBox.setPadding(new Insets(25,0,0,30));
		
		//Part Source Radio Buttons
		ToggleGroup partsSourceToggle = new ToggleGroup();
		RadioButton inHouseRadio = new RadioButton("Inhouse");
		inHouseRadio.setToggleGroup(partsSourceToggle);
		inHouseRadio.setSelected(true);
		RadioButton outsourcedRadio = new RadioButton("Outsourced");
		outsourcedRadio.setToggleGroup(partsSourceToggle);
		windowLabelHBox.getChildren().addAll(addPartLabel,inHouseRadio,outsourcedRadio);
		
		Label idLabel = new Label ("ID");
		TextField idTextField = new TextField("Auto Gen - Disabled");
		idTextField.setEditable(false);
		idLabel.setPrefWidth(TEXT_FIELD_WIDTH);
		idTextField.setPrefWidth(TEXT_FIELD_WIDTH);
		
		Label nameLabel = new Label ("Name");
		TextField nameTextField = new TextField();
		nameLabel.setPrefWidth(TEXT_FIELD_WIDTH);
		nameTextField.setPrefWidth(TEXT_FIELD_WIDTH);


		Label invLabel = new Label ("Inventory");
		TextField invTextField = new TextField();
		invLabel.setPrefWidth(TEXT_FIELD_WIDTH);
		invTextField.setPrefWidth(TEXT_FIELD_WIDTH);


		Label priceLabel = new Label ("Price");
		TextField priceTextField = new TextField();
		priceLabel.setPrefWidth(TEXT_FIELD_WIDTH);
		priceTextField.setPrefWidth(TEXT_FIELD_WIDTH);


		Label minLabel = new Label ("Min");
		HBox minMaxHBox = new HBox(5);
		TextField minTextField = new TextField();
		minTextField.setPrefWidth(TEXT_FIELD_WIDTH/2);
		minLabel.setPrefWidth(TEXT_FIELD_WIDTH/2);
		Label maxLabel = new Label ("Max");
		TextField maxTextField = new TextField();
		maxTextField.setPrefWidth(TEXT_FIELD_WIDTH/2);
		maxLabel.setPrefWidth(TEXT_FIELD_WIDTH/2);
		minMaxHBox.getChildren().addAll(minTextField,maxLabel,maxTextField);


		Label companyLabel = new Label ("Company name");
		TextField companyTextField = new TextField();
		companyLabel.setPrefWidth(TEXT_FIELD_WIDTH);
		companyTextField.setPrefWidth(TEXT_FIELD_WIDTH);
		
		Label machineIDLabel = new Label ("Machine ID");
		TextField machineIDTextField = new TextField();
		machineIDLabel.setPrefWidth(TEXT_FIELD_WIDTH);
		machineIDTextField.setPrefWidth(TEXT_FIELD_WIDTH);
		
		VBox fieldLabelVBox = new VBox(13);
		fieldLabelVBox.getChildren().addAll(idLabel,nameLabel,invLabel,priceLabel,minLabel,machineIDLabel);

		
		VBox textFieldVBox = new VBox(5);
		textFieldVBox.getChildren().addAll(idTextField,nameTextField,invTextField,priceTextField, minMaxHBox,machineIDTextField);
		textFieldVBox.setPadding(new Insets (30,50,25,50));
		
		outsourcedRadio.setOnAction(e->{
		fieldLabelVBox.getChildren().add(companyLabel);
		textFieldVBox.getChildren().add(companyTextField);
		fieldLabelVBox.getChildren().remove(machineIDLabel);
		textFieldVBox.getChildren().remove(machineIDTextField);
		});
		inHouseRadio.setOnAction(e->{
		fieldLabelVBox.getChildren().remove(companyLabel);
		textFieldVBox.getChildren().remove(companyTextField);
		fieldLabelVBox.getChildren().add(machineIDLabel);
		textFieldVBox.getChildren().add(machineIDTextField);
		});
		fieldLabelVBox.setPadding(new Insets (35,25,25,50));
		
		
		Label errorMessage = new Label("");
		
		Button saveButton = new Button ("Save");
		saveButton.setOnAction(e->{
			if (nameTextField.getText().isEmpty()){
				errorMessage.setText("Please enter a valid part name.");
			}
			else if(!invTextField.getText().matches("[0-9]*") || invTextField.getText().isEmpty()){
				errorMessage.setText("Please enter a valid amount in inventory.");
			}
			else if(!priceTextField.getText().matches("\\d+\\.\\d\\d") || priceTextField.getText().isEmpty()){
				errorMessage.setText("Please enter a valid part price.");
			}
			else if(!minTextField.getText().matches("[0-9]*") || minTextField.getText().isEmpty() || Integer.parseInt(minTextField.getText())>Integer.parseInt(invTextField.getText())){
				errorMessage.setText("Please enter a valid minimum stock.");
			}
			else if(!maxTextField.getText().matches("[0-9]*") || maxTextField.getText().isEmpty()|| Integer.parseInt(minTextField.getText())>Integer.parseInt(maxTextField.getText())){
				errorMessage.setText("Please enter a valid maximum stock.");
			}
			else if (outsourcedRadio.isSelected() && companyTextField.getText().isEmpty()){
				errorMessage.setText("Please enter a valid company name.");
			}
			else if (inHouseRadio.isSelected() && (machineIDTextField.getText().isEmpty() ||!machineIDTextField.getText().matches("[0-9]*"))){
				errorMessage.setText("Please enter a valid machine ID.");
			}
			else{
				Random rnd = new Random();
				int newID = 100000 + rnd.nextInt(900000);
				if(inHouseRadio.isSelected()){
					InhousePart newIHPart = new InhousePart();
					newIHPart.setPartID(newID);
					newIHPart.setName(nameTextField.getText());
					newIHPart.setInStock(Integer.parseInt(invTextField.getText()));
					newIHPart.setPrice(Double.parseDouble(priceTextField.getText()));
					newIHPart.setMin(Integer.parseInt(minTextField.getText()));
					newIHPart.setMax(Integer.parseInt(maxTextField.getText()));
					newIHPart.setMachineID(Integer.parseInt(machineIDTextField.getText()));
					Main.partsData.add(newIHPart);
					window.close();
				}
				else{
					OutsourcedPart newOSPart = new OutsourcedPart();
					newOSPart.setPartID(newID);
					newOSPart.setName(nameTextField.getText());
					newOSPart.setInStock(Integer.parseInt(invTextField.getText()));
					newOSPart.setPrice(Double.parseDouble(priceTextField.getText()));
					newOSPart.setMin(Integer.parseInt(minTextField.getText()));
					newOSPart.setMax(Integer.parseInt(maxTextField.getText()));
					newOSPart.setCompanyName(companyTextField.getText());
					Main.partsData.add(newOSPart);
					window.close();
				}
		}
		});
		
		HBox buttonHBox = new HBox(10);
		Button exitButton = new Button ("Exit");
		exitButton.setOnAction(e -> window.close());
		buttonHBox.getChildren().addAll(errorMessage,saveButton,exitButton);
		buttonHBox.setAlignment(Pos.BASELINE_RIGHT);
		buttonHBox.setPadding(new Insets(0,25,15,0));
		
		BorderPane layout = new BorderPane();
		layout.setTop(windowLabelHBox);
		layout.setLeft(fieldLabelVBox);
		layout.setRight(textFieldVBox);
		layout.setBottom(buttonHBox);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}
