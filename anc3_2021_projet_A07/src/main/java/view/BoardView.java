package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Board;
import model.Column;
import mvvm.BoardViewModel;


public final class BoardView extends VBox{


    private final BoardViewModel boardViewModel;
    private final TitleView boardTitle;
    private final ListView<Column> columnList = new ListView<>();
    private final VBox head=new VBox();
    private final MenuView menuBar;
    private final HBox foot= new HBox();
    private final Button add;
    private final Button deleteSelected;
    final KeyCombination keyCombCtrZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN);
    final KeyCombination keyCombCtrY = new KeyCodeCombination(KeyCode.Y, KeyCombination.SHORTCUT_DOWN);


    public BoardView(Stage primaryStage, Board board){
        this(primaryStage,new BoardViewModel(board));
    }

    public BoardView(Stage primaryStage, BoardViewModel boardViewModel) {
        this.boardViewModel=boardViewModel;
        this.boardTitle=new TitleView(boardViewModel);
        this.menuBar= new MenuView(boardViewModel);
        this.add= new Button("Add Column");
        this.deleteSelected= new Button("Delete Selected");
        ListColumns();
        setAll();
        configDataBindings();
        addColumn();
        DeleteSelectedColumn();
        Scene scene = new Scene(this);
        primaryStage.setTitle("Trello");
        primaryStage.setScene(scene);
        setKeyComb();
    }

    private void setKeyComb() {
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (keyCombCtrZ.match(event) ) {
                    boardViewModel.undo();
                }
                if(keyCombCtrY.match(event)){
                    boardViewModel.redo();
                }
            }
        });
    }

    private void ListColumns(){
        columnList.setCellFactory(view -> new ListCell<>(){
            @Override
            protected void updateItem(Column column, boolean b){
                super.updateItem(column, b);
                ColumnView columnView = null;
                if(column != null){
                    columnView = new ColumnView(column, boardViewModel);
                }
                setGraphic(columnView);
            }
        });
    }

    private void addColumn(){
        add.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                boardViewModel.createColumnAndAddToBoard();
            }
        });
    }
    private void DeleteSelectedColumn(){
        deleteSelected.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                boardViewModel.removeColumnSelected();
            }
        });

    }

    // head's board
    private void setHead(){
        head.setPadding(new Insets(0,0,10,0));
        head.setSpacing(5);
        boardTitle.setPrefWidth(100);
        head.getChildren().add(menuBar);
        head.getChildren().add(boardTitle);
        head.setBackground(new Background(new BackgroundFill(Color.	LIGHTSLATEGREY, null, null)));
    }

    // body's board
    private void setColumnList() {
        columnList.setOrientation(Orientation.HORIZONTAL);
        columnList.setMinWidth(1000);
        columnList.setMinHeight(550);
    }

    //foot's board
    private void setFoot(){
        foot.getChildren().add(add);
        foot.getChildren().add(deleteSelected);
        foot.setPadding(new Insets(10,10,10,10));
    }

    private void setAll(){
        setHead();
        setColumnList();
        setFoot();
        this.getChildren().add(head);
        this.getChildren().add(columnList);
        this.getChildren().add(foot);
        this.setPrefWidth(1500);
        this.setPrefHeight(700);
        setPadding(new Insets(10, 10, 10, 10));
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
    }

    //Bidings
    private void configDataBindings(){
        columnList.itemsProperty().bindBidirectional(boardViewModel.columnsProperty());
        deleteSelected.disableProperty().bind(boardViewModel.selectedToDeleteProperty());
        boardViewModel.selectedColumnBinding(columnList.getSelectionModel().selectedIndexProperty());
    }


}


