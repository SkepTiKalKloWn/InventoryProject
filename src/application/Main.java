package application;
	
import javafx.application.*;
import javafx.collections.*;
import javafx.collections.transformation.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.geometry.*;


public class Main extends Application {
	private final TableView <Part>partTable = new TableView<Part>();
    static ObservableList<Part> partsData = FXCollections.observableArrayList();
	private FilteredList<Part> mainFilteredPartsData = new FilteredList<>(partsData, p -> true);
	private final TableView<Product> productTable = new TableView<Product>();
    static ObservableList<Product> productsData = FXCollections.observableArrayList();
	FilteredList<Product> filteredProductsData = new FilteredList<>(productsData, p -> true);
    static Part partToModify;
    static Product productToModify;
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
			//Error Label
		Label errorLabel = new Label ("");
		errorLabel.setPadding(new Insets(0,30,0,0));
			//Main Screen Label
		Label title = new Label("Inventory Management System");
			root.setTop(title);
			title.setPadding(new Insets(50,0,25,100));
			
			//Gridpane for parts section of Window
			GridPane partsList = new GridPane();
		     for (int i = 0; i < 4; i++) {
		         ColumnConstraints column = new ColumnConstraints(150);
		         partsList.getColumnConstraints().add(column);
		     }
			Label partsLabel = new Label("Parts");
			partsLabel.setPadding(new Insets (0,20,20,20));
			partsList.add(partsLabel, 0, 0);
			
			//Search Button in parts window
			HBox partsSearchBar = new HBox(10);
			partsSearchBar.setAlignment(Pos.BOTTOM_CENTER);
			partsSearchBar.setPadding(new Insets(0,0,10,0));
			TextField partsSearchField = new TextField();
			partsSearchField.setOnKeyReleased(e->{
				partsSearchField.textProperty().addListener((observable,oldValue,newValue) -> {
					mainFilteredPartsData.setPredicate(part -> {
						// If filter text is empty, display all persons.
						errorLabel.setText("");
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
			partsList.add(partsSearchBar, 2, 0,2,1);
			
			//Parts Table
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
			partTable.setItems(mainFilteredPartsData);
			partTable.getColumns().addAll(partIDColumn,partNameColumn,partInvColumn,partPriceColumn);
			partsList.add(partTable, 0,1,4,1);
			
			
			//Buttons for Parts List
			Button partsAddButton = new Button("Add");
			partsAddButton.setOnAction(e -> {
				errorLabel.setText("");
				AddPartWindow.display();
				});
			Button partsModifyButton = new Button("Modify");
			partsModifyButton.setOnAction(e -> {
				errorLabel.setText("");
				if (partTable.getSelectionModel().getSelectedItem() == null){
					AddPartWindow.display();
				}
				else{
				partToModify = partTable.getSelectionModel().getSelectedItem();
				ModifyPartWindow.display();
				}
			});
			Button partsDeleteButton = new Button("Delete");
			partsDeleteButton.setOnAction(e ->{
				errorLabel.setText("");
				partsData.remove(partTable.getSelectionModel().getSelectedItem());
			});
			HBox partsButtonBox = new HBox(50);
			partsButtonBox.setPadding (new Insets(5,0,0,0));
			partsButtonBox.getChildren().addAll(partsAddButton,partsModifyButton,partsDeleteButton);
			partsList.add(partsButtonBox, 2, 3,3,1);
			partsList.setPadding(new Insets(50,25,0,75));
			root.setLeft(partsList);
			
			//Gridpane for products section of Window
			GridPane productsList = new GridPane();
		     for (int i = 0; i < 4; i++) {
		         ColumnConstraints column = new ColumnConstraints(150);
		         productsList.getColumnConstraints().add(column);
		     }			
		     Label productsLabel = new Label("Products");
			productsLabel.setPadding(new Insets (0,20,20,20));
			productsList.add(productsLabel, 0, 0);
			
			//Search Button in products window
			HBox productsSearchBar = new HBox(10);
			productsSearchBar.setAlignment(Pos.BOTTOM_CENTER);
			productsSearchBar.setPadding(new Insets(0,0,10,0));
			TextField productsSearchField = new TextField();
			productsSearchField.setOnKeyReleased(e->{
				productsSearchField.textProperty().addListener((observable,oldValue,newValue) -> {
					filteredProductsData.setPredicate(product -> {
						errorLabel.setText("");
							// If filter text is empty, display all persons.
		                	if (newValue == null || newValue.isEmpty()) {
		                		return true;
		                	}

		                	// Compare first name and last name of every person with filter text.
		                	String lowerCaseFilter = newValue.toLowerCase();

		                	if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
		                		return true; // Filter matches first name.
		                	}
		                	return false; // Does not match.
		            	});
				});
			});
			productsSearchField.setPrefWidth(200);
			productsSearchBar.getChildren().addAll(productsSearchField);
			productsList.add(productsSearchBar, 2, 0,2,1);

			
			
			//Product ID Table
			TableColumn<Product, String> productIDColumn = new TableColumn<Product, String>("Product ID");
			productIDColumn.setCellValueFactory(new PropertyValueFactory<Product,String>("productID"));
			productIDColumn.setMinWidth(150);
			TableColumn<Product, String> productNameColumn = new TableColumn<Product, String>("Product Name");
			productNameColumn.setMinWidth(150);
			productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));		
			TableColumn<Product, Integer> productInvColumn = new TableColumn<Product, Integer>("Inventory Level");
			productInvColumn.setMinWidth(150);
			productInvColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));		
			TableColumn<Product, Integer> productPriceColumn = new TableColumn<Product, Integer>("Price/Cost per unit");
			productPriceColumn.setMinWidth(150);
			productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
			productTable.setItems(productsData);
			productTable.getColumns().addAll(productIDColumn,productNameColumn,productInvColumn,productPriceColumn);
			productsList.add(productTable, 0,1,4,1);
			
			//Buttons for Products List
			Button productsAddButton = new Button("Add");
			productsAddButton.setOnAction(e ->{
				errorLabel.setText("");
				AddProductWindow.display();
				});
			Button productsModifyButton = new Button("Modify");
			productsModifyButton.setOnAction(e -> {
				errorLabel.setText("");
				if (productTable.getSelectionModel().getSelectedItem() == null){
					AddPartWindow.display();
				}
				else{
				productToModify = productTable.getSelectionModel().getSelectedItem();
				ModifyProductWindow.display();
				}
			});
			Button productsDeleteButton = new Button("Delete");
			productsDeleteButton.setOnAction(e ->{
				if(!productTable.getSelectionModel().getSelectedItem().getParts().isEmpty()){
					errorLabel.setText("Parts must be removed from product before product can be deleted");
				}
			else{
				productsData.remove(productTable.getSelectionModel().getSelectedItem());
			}
			});
			HBox productsButtonBox = new HBox(25);
			productsButtonBox.setPadding (new Insets(5,0,0,0));
			productsButtonBox.getChildren().addAll(productsAddButton,productsModifyButton,productsDeleteButton);
			productsList.add(productsButtonBox, 2, 3,3,1);
			productsList.setPadding(new Insets(50,75,0,0));
			root.setRight(productsList);
			
			//Exit Button
			Button exitButton = new Button("Exit");
			exitButton.setOnAction(e -> Platform.exit());
			HBox bottomBox = new HBox();
			bottomBox.setPadding(new Insets(20,175,100,0));
			bottomBox.getChildren().addAll(errorLabel, exitButton);
			bottomBox.setAlignment(Pos.CENTER_RIGHT);
			root.setBottom(bottomBox);
			
			Scene scene = new Scene(root,1400,600);
			primaryStage.setScene(scene);
			primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
