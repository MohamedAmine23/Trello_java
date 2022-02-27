package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import model.Card;
import mvvm.CardViewModel;
import mvvm.ColumnViewModel;
import java.util.Optional;


public final class CardView extends BorderPane {

    private CardViewModel cardViewModel;
    private final TitleView cardTitle;
    private final Button btnUp;
    private final Button btnDown;
    private final Button btnLeft;
    private final Button btnRight;
    private final Insets margin = new Insets(20);
    private final ContextMenu contextMenu;
    private final MenuItem delete;


    public CardView(Card card, ColumnViewModel columnViewModel){this(new CardViewModel(card,columnViewModel));}

    public CardView( CardViewModel cardViewModel){
        this.cardViewModel = cardViewModel;
        this.cardTitle = new TitleView(cardViewModel);
        this.contextMenu=new ContextMenu();
        this.delete= new MenuItem("Delete");
        this.btnUp = new Button(" ↑ ");
        this.btnDown = new Button(" ↓ ");
        this.btnLeft = new Button(" ← ");
        this.btnRight = new Button(" → ");
        setAll();
        configDataBindings();
        configActions();
    }

    private void setAll(){
        setContextMenu();
        setBtnUp();
        setBtnDown();
        setBtnRight();
        setBtnLeft();
        setTitle();
        setPadding(new Insets(10, 10, 10, 10));
        setBackground(new Background(new BackgroundFill(Color.	LIGHTSTEELBLUE, null, null)));
    }
    private void setBtnUp(){
        setTop(btnUp);
        setAlignment(btnUp,Pos.CENTER);
        setMargin(btnUp, margin);
    }
    private void setBtnDown(){
        setBottom(btnDown);
        setAlignment(btnDown,Pos.CENTER);
        setMargin(btnDown, margin);
    }

    private void setBtnRight(){
        setRight(btnRight);
        setAlignment(btnRight,Pos.CENTER_RIGHT);
        setMargin(btnRight, margin);
    }
    private void setBtnLeft(){
        setLeft(btnLeft);
        setAlignment(btnLeft,Pos.CENTER_LEFT);
        setMargin(btnLeft, margin);
    }
    public void setTitle(){
        setCenter(cardTitle);
        setPrefWidth(80);
        setAlignment(cardTitle,Pos.CENTER);
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
                    cardViewModel.remove();
                }
            }
        });
        contextMenu.getItems().add(delete);
        this.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(cardTitle, event.getScreenX(), event.getScreenY());
            }
        });
    }


    //BIDING
    private void configDataBindings(){
        btnRight.disableProperty().bind(cardViewModel.disabledRightProperty());
        btnLeft.disableProperty().bind(cardViewModel.disabledLeftProperty());
        btnUp.disableProperty().bind(cardViewModel.disabledUpProperty());
        btnDown.disableProperty().bind(cardViewModel.disabledDownProperty());
    }

    private void configActions() {
        btnUp.setOnAction(e -> cardViewModel.moveCardToUp());
        btnDown.setOnAction(e -> cardViewModel.moveCardToDown());
        btnRight.setOnAction(e -> cardViewModel.moveCardToRight());
        btnLeft.setOnAction(e -> cardViewModel.moveCardToLeft());
    }



}
