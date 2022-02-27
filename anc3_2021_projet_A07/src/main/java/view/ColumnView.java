package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.Card;
import model.Column;
import mvvm.BoardViewModel;
import mvvm.ColumnViewModel;
import java.util.Optional;


public final class ColumnView extends VBox {

    private final ColumnViewModel columnViewModel;
    private final TitleView columnTitle;
    private final Button btnLeft;
    private final Button btnRight;
    private final ListView<Card> cardList = new ListView<>();
    private final BorderPane head=new BorderPane();
    private final HBox foot= new HBox();
    private final Button add;
    private final CheckBox supp;
    private final ContextMenu contextMenu;
    private final MenuItem delete;


    public ColumnView(Column column, BoardViewModel boardViewModel){
        this(new ColumnViewModel(column, boardViewModel));
    }

    public ColumnView( ColumnViewModel columnViewModel) {
        this.columnViewModel=columnViewModel;
        this.columnTitle=new TitleView(columnViewModel);
        this.add= new Button("Add Card");
        this.supp=new CheckBox("Suppr");
        this.contextMenu=new ContextMenu();
        this.delete= new MenuItem("Delete");
        this.btnLeft=new Button("←");
        this.btnRight=new Button("→");
        ListCards();
        setAll();
        configDataBindings();
        configActions();
    }

    private void ListCards(){
        cardList.setCellFactory(view -> new ListCell<>(){
            @Override
            protected void updateItem(Card card, boolean b){
                super.updateItem(card, b);
                CardView cardView = null;
                if(card != null){
                    cardView = new CardView(card,columnViewModel);
                }
                setGraphic(cardView);
            }
        });
    }

    // head's column
    private void setHead(){
        head.setCenter(columnTitle);
        head.setAlignment(columnTitle,Pos.CENTER);
        head.setLeft(btnLeft);
        head.setAlignment(btnLeft,Pos.CENTER_LEFT);
        head.setRight(btnRight);
        head.setAlignment(btnRight,Pos.CENTER_RIGHT);
        head.setBottom(supp);
        head.setAlignment(supp,Pos.BOTTOM_CENTER);
        head.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
    }

    private void setFoot(){
        foot.getChildren().add(add);
        foot.setPadding(new Insets(10,10,10,10));
    }
    private void setAll(){
        addCard();
        setContextMenu();
        setHead();
        setFoot();
        this.getChildren().add(head);
        cardList.setMinHeight(600);
        this.getChildren().addAll(cardList);
        this.getChildren().add(foot);
        setSpacing(10);
        setPadding(new Insets(10, 10, 10, 10));
        this.setPrefWidth(300);
        this.setPrefHeight(800);
    }


    private void configDataBindings(){
        btnLeft.disableProperty().bind(columnViewModel.columnLeftDisabledProperty());
        btnRight.disableProperty().bind(columnViewModel.columnRightDisabledProperty());
        cardList.itemsProperty().bindBidirectional(columnViewModel.cardsProperty());
        columnViewModel.selectedCardBinding(cardList.getSelectionModel().selectedIndexProperty());
        supp.selectedProperty().bindBidirectional(columnViewModel.selectionProperty());
    }

    private void configActions() {
        btnLeft.setOnAction(e -> columnViewModel.moveColumnToLeft());
        btnRight.setOnAction(e -> columnViewModel.moveColumnToRight());
    }

    private void addCard(){
        add.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                columnViewModel.createCardAndAddToColumn();
            }
        });
    }
    private void setContextMenu() {
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(" Delete Confirmation");
                alert.setHeaderText("Confirmation ");
                alert.setContentText("Are you sure ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    columnViewModel.remove();
                }
            }
        });
        contextMenu.getItems().add(delete);

        head.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(head, event.getScreenX(), event.getScreenY());
            }
        });
    }



}
