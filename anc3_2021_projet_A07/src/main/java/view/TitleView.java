package view;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import mvvm.NameableViewModel;



public final class TitleView extends StackPane {

    private final Label lbTitle;
    private final TextField tfTitle;
    private NameableViewModel nameableViewModel;

    TitleView(){
        this.lbTitle = new Label();
        this.tfTitle = new TextField("New Title");
        configComponents();
        configActions();
    }

    public TitleView(NameableViewModel titleViewModel){
        this();
        this.nameableViewModel=titleViewModel;
        configDataBindings();
    }


    private void configComponents() {
        lbTitle.setVisible(true);
        lbTitle.setFont(new Font("Arial", 20));
        tfTitle.setVisible(false);
        this.getChildren().addAll(lbTitle,tfTitle);
    }


    private void configActions(){
        configActionLabel();
        configActionTextfield();
    }

    private void configActionLabel(){
        lbTitle.setOnMouseClicked((MouseEvent e)->{
            if (e.getClickCount() == 2)
                switchEdition();
        });
    }

    private void configActionTextfield(){
        tfTitle.setOnAction(e->{
                nameableViewModel.editName(tfTitle.getText());
                switchEdition();
        });
    }

    private void switchEdition() {
        ObservableList<Node> childs = this.getChildren();
        if (childs.size() > 1) {
            Node topNode = childs.get(childs.size()-1);
            Node newTopNode = childs.get(childs.size()-2);
            topNode.setVisible(false);
            topNode.toBack();
            newTopNode.setVisible(true);
        }
    }

    private void configDataBindings(){
        lbTitle.textProperty().bindBidirectional(nameableViewModel.nameProperty());
        tfTitle.textProperty().bindBidirectional(nameableViewModel.nameProperty());
    }


}