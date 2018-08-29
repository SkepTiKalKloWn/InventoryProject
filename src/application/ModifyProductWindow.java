package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifyProductWindow {


	@SuppressWarnings("unchecked")
	public static void display(){
		TableView <Part>partTable = new TableView<Part>();
	    ObservableList<Part> productPartsData = FXCollections.observableArrayList();
	    TableView<Part>productPartTable = new TableView<Part>();
		FilteredList<Part> addFilteredPartsData = new FilteredList<>(Main.partsData, p -> true);
		final Double TEXT_FIELD_WIDTH = 100.0;
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Modify Product");
		String saveDeleteButtonText = "Save";

		GridPane productInfo = new GridPane();
		productInfo.setPadding(new Insets(100,0,0,0));

		//Window Label
		Label addPartLabel = new Label ("Modify Product");
		addPartLabel.setPadding (new Insets(0,40,0,0));
		HBox windowLabelHBox = new HBox(35);
		windowLabelHBox.setPadding(new Insets(25,0,0,30));

		//Product ID
		Label idLabel = new Label ("ID");
		TextField idTextField = new TextField(Integer.toString(Main.productToModify.getProductID()));
		idTextField.setEditable(false);
		idLabel.setPrefWidth(TEXT_FIELD_WIDTH);
		idTextField.setPrefWidth(TEXT_FIELD_WIDTH);

		//Product Name
		Label nameLabel = new Label ("Name");
		TextField nameTextField = new TextField(Main.productToModify.getName());
		nameLabel.setPrefWidth(TEXT_FIELD_WIDTH);
		nameTextField.setPrefWidth(TEXT_FIELD_WIDTH);

		//Product Inventory
		Label invLabel = new Label ("Inventory");
		TextField invTextField = new TextField(Integer.toString(Main.productToModify.getInStock()));
		invLabel.setPrefWidth(TEXT_FIELD_WIDTH);
		invTextField.setPrefWidth(TEXT_FIELD_WIDTH);

		//Product Price
		Label priceLabel = new Label ("Price");
		TextField priceTextField = new TextField(Double.toString(Main.productToModify.getPrice()));
		priceLabel.setPrefWidth(TEXT_FIELD_WIDTH);
		priceTextField.setPrefWidth(TEXT_FIELD_WIDTH);

		//Product Min/Max
		Label minLabel = new Label ("Min");
		HBox minMaxHBox = new HBox(5);
		TextField minTextField = new TextField(Integer.toString(Main.productToModify.getMin()));
		minTextField.setPrefWidth(TEXT_FIELD_WIDTH/2);
		minLabel.setPrefWidth(TEXT_FIELD_WIDTH/2);
		Label maxLabel = new Label ("Max");
		TextField maxTextField = new TextField(Integer.toString(Main.productToModify.getMax()));
		maxTextField.setPrefWidth(TEXT_FIELD_WIDTH/2);
		maxLabel.setPrefWidth(TEXT_FIELD_WIDTH/2);
		minMaxHBox.getChildren().addAll(minTextField,maxLabel,maxTextField);

		//Product Info Layout
		VBox fieldLabelVBox = new VBox(13);
		fieldLabelVBox.getChildren().addAll(idLabel,nameLabel,invLabel,priceLabel,minLabel);
		fieldLabelVBox.setPadding(new Insets (35,10,25,50));
		VBox textFieldVBox = new VBox(5);
		textFieldVBox.getChildren().addAll(idTextField,nameTextField,invTextField,priceTextField, minMaxHBox);
		textFieldVBox.setPadding(new Insets (30,30,25,10));
		productInfo.add(fieldLabelVBox, 0,0);
		productInfo.add(textFieldVBox, 1,0);
		HBox errorBox = new HBox(10);

		//Error Message
		Label errorMessage = new Label("");
		errorBox.getChildren().add(errorMessage);
		productInfo.add(errorBox,0,1,2,1);
		errorBox.setAlignment(Pos.BOTTOM_CENTER);

		VBox productList = new VBox(10);
		productList.setPadding(new Insets(10,25,0,0));

		//Search Bar
		HBox partsSearchBar = new HBox(10);
		partsSearchBar.setAlignment(Pos.BOTTOM_CENTER);
		partsSearchBar.setPadding(new Insets(0,0,10,0));
		TextField partsSearchField = new TextField();
		partsSearchField.setOnKeyReleased(e->{
			partsSearchField.textProperty().addListener((observable,oldValue,newValue) -> {
				addFilteredPartsData.setPredicate(part -> {
						// If filter text is empty, display all persons.
	                	if (newValue == null || newValue.isEmpty()) {
	                		return true;
	                	}

	                	// Compare first name and last name of every person with filter text.
	                	String lowerCaseFilter = newValue.toLowerCase();

	                	if (part.getName().toLowerCase().contains(lowerCaseFilter)) {
	                		return true; // Filter matches first name.
	                	}
	                	return false; // Does not match.
	            	});
			});
		});
		partsSearchField.setPrefWidth(200);
		partsSearchBar.getChildren().addAll(partsSearchField);

		//Full part List
		TableColumn<Part, String> partIDColumn = new TableColumn<Part, String>("Part ID");
		partIDColumn.setCellValueFactory(new PropertyValueFactory<Part,String>("partID"));
        partIDColumn.setMinWidth(150);
		TableColumn<Part, String> partNameColumn = new TableColumn<Part, String>("Part Name");
		partNameColumn.setMinWidth(150);
		partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
		TableColumn<Part, Integer> partInvColumn = new TableColumn<Part, Integer>("Inventory Level");
		partInvColumn.setMinWidth(150);
		partInvColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
		TableColumn<Part, Integer> partPriceColumn = new TableColumn<Part, Integer>("Price/Cost per unit");
		partPriceColumn.setMinWidth(150);
		partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		partTable.setItems(addFilteredPartsData);
		partTable.getColumns().addAll(partIDColumn,partNameColumn,partInvColumn,partPriceColumn);
		partTable.setMaxHeight(150);

		//Add part to Product Button
		HBox addButtonHBox = new HBox(10);
		Button addButton = new Button ("Add");
		addButton.setOnAction(e -> {
			if (partTable.getSelectionModel().getSelectedItem() == null){
				errorMessage.setText("Select part to add to product");
			}
			else if(partTable.getSelectionModel().getSelectedItem().getMin()>= partTable.getSelectionModel().getSelectedItem().getInStock()){
				errorMessage.setText("Part unavailable");
			}
			else{
				partTable.getSelectionModel().getSelectedItem().setInStock((partTable.getSelectionModel().getSelectedItem().getInStock())-1);
				productPartsData.add(partTable.getSelectionModel().getSelectedItem());
				errorMessage.setText("");
			}
		});
		addButtonHBox.getChildren().addAll(addButton);
		addButtonHBox.setAlignment(Pos.BASELINE_RIGHT);
		addButtonHBox.setPadding(new Insets(0,25,15,0));

		//Parts in Product List
		for(Product productToAdd:Main.productsData){
			productPartsData.addAll(productToAdd.getParts());
		}
		TableColumn<Part, String> productPartIDColumn = new TableColumn<Part, String>("Part ID");
		productPartIDColumn.setCellValueFactory(new PropertyValueFactory<Part,String>("partID"));
		productPartIDColumn.setMinWidth(150);
		TableColumn<Part, String> productPartNameColumn = new TableColumn<Part, String>("Part Name");
		productPartNameColumn.setMinWidth(150);
		productPartNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
		TableColumn<Part, Integer> productPartInvColumn = new TableColumn<Part, Integer>("Inventory Level");
		productPartInvColumn.setMinWidth(150);
		productPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
		TableColumn<Part, Integer> productPartPriceColumn = new TableColumn<Part, Integer>("Price/Cost per unit");
		productPartPriceColumn.setMinWidth(150);
		productPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		productPartTable.setItems(productPartsData);
		productPartTable.getColumns().addAll(productPartIDColumn,productPartNameColumn,productPartInvColumn,productPartPriceColumn);
		productPartTable.setMaxHeight(150);

		//Remove Part from Product Button
		HBox deleteButtonHBox = new HBox(10);
		Button deleteButton = new Button ("Delete");
		deleteButton.setOnAction(e -> {
			if (partTable.getSelectionModel().getSelectedItem() == null){
				errorMessage.setText("Select part to remove from product");
			}
			else if(partTable.getSelectionModel().getSelectedItem().getMax()<= partTable.getSelectionModel().getSelectedItem().getInStock()){
				errorMessage.setText("Part cannot be removed. Inventory Full");
			}
			else{
				partTable.getSelectionModel().getSelectedItem().setInStock((partTable.getSelectionModel().getSelectedItem().getInStock())+1);
				productPartsData.remove(partTable.getSelectionModel().getSelectedItem());
				if(productPartsData.isEmpty()){
					errorMessage.setText("Product will not be usable without parts added");;
				}
			}
		});
		deleteButtonHBox.getChildren().addAll(deleteButton);
		deleteButtonHBox.setAlignment(Pos.BASELINE_RIGHT);
		deleteButtonHBox.setPadding(new Insets(0,25,15,0));

		productList.getChildren().addAll(partsSearchBar,partTable,addButtonHBox,productPartTable,deleteButtonHBox);

		//SaveButton
		Button saveButton = new Button (saveDeleteButtonText);
		saveButton.setOnAction(e->{
			double totalCost = 0.0;
			for (Part partToAdd:productPartsData){
				totalCost += partToAdd.getPrice();
			}
			if (nameTextField.getText().isEmpty()){
				errorMessage.setText("Please enter a valid part name.");
			}
			else if(!invTextField.getText().matches("[0-9]*") || invTextField.getText().isEmpty()){
				errorMessage.setText("Please enter a valid amount in inventory.");
			}
			else if(!priceTextField.getText().matches("\\d+\\.\\d\\d") || priceTextField.getText().isEmpty()){
				errorMessage.setText("Please enter a valid part price.");
			}
			else if(totalCost>Double.parseDouble(priceTextField.getText())){
				errorMessage.setText("Part Cost is greater than Product Price");
			}
			else if(!minTextField.getText().matches("[0-9]*") || minTextField.getText().isEmpty() || Integer.parseInt(minTextField.getText())>Integer.parseInt(invTextField.getText())){
				errorMessage.setText("Please enter a valid minimum stock.");
			}
			else if(!maxTextField.getText().matches("[0-9]*") || maxTextField.getText().isEmpty()|| Integer.parseInt(minTextField.getText())>Integer.parseInt(maxTextField.getText())){
				errorMessage.setText("Please enter a valid maximum stock.");
			}
			else{
					Random rnd = new Random();
					int newID = 100000 + rnd.nextInt(900000);
						Product newProduct = new Product();
						newProduct.setProductID(newID);
						newProduct.setName(nameTextField.getText());
						newProduct.setInStock(Integer.parseInt(invTextField.getText()));
						newProduct.setPrice(Double.parseDouble(priceTextField.getText()));
						newProduct.setMin(Integer.parseInt(minTextField.getText()));
						newProduct.setMax(Integer.parseInt(maxTextField.getText()));
						for (Part partToAdd:productPartsData){
							newProduct.addPart(partToAdd);
							Main.partsData.get(Main.partsData.indexOf(partToAdd)).setInStock(partToAdd.getInStock());
						}
						Main.productsData.remove(Main.productToModify);
						Main.productsData.add(newProduct);
						window.close();
			}
		});


		Button exitButton = new Button ("Exit");
		exitButton.setOnAction(e -> {
			ArrayList<Part> partsToRemove = new ArrayList<Part>();
			for(Part partToRemove:productPartsData){
				partToRemove.setInStock(partToRemove.getInStock()+1);
				partsToRemove.add(partToRemove);
			}
			productPartsData.removeAll(partsToRemove);
			if(productPartsData.isEmpty()){
		window.close();
			}
		});
		HBox buttonHBox = new HBox(10);
		buttonHBox.getChildren().addAll(saveButton,exitButton);
		buttonHBox.setAlignment(Pos.BASELINE_RIGHT);
		buttonHBox.setPadding(new Insets(0,25,15,0));

		BorderPane layout = new BorderPane();
		layout.setTop(windowLabelHBox);
		layout.setLeft(productInfo);
		layout.setRight(productList);
		layout.setBottom(buttonHBox);

		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}