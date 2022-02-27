package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import mvvm.BoardViewModel;
import mvvm.Command.ManagerCommand;


public final class MenuView extends MenuBar {

    private final BoardViewModel boardViewModel;
    private final ManagerCommand managerCommand= ManagerCommand.getInstance();
    private final Menu fileMenu = new Menu("File");
    private final Menu editMenu = new Menu("Edition");
    private final MenuItem addColItem = new MenuItem("Add Column");
    private final MenuItem redoItem=new MenuItem(managerCommand.getRedoName());
    private final MenuItem undoItem=new MenuItem(managerCommand.getUndoName());
    private final MenuItem exitItem = new MenuItem("Exit");


    MenuView(BoardViewModel boardViewModel){
        this.boardViewModel=boardViewModel;
        setMenu();
        addColumn();
        exit();
        configActions();
        configDataBindings();
    }

    private  void setMenu(){
        fileMenu.getItems().addAll(addColItem,exitItem);
        editMenu.getItems().addAll(redoItem,undoItem);
        this.getMenus().addAll(fileMenu,editMenu);
    }

    private void addColumn(){
        addColItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boardViewModel.createColumnAndAddToBoard();
            }
        });
    }

    private void exit(){
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
    }

    private void configActions(){
        redoItem.setOnAction(e->boardViewModel.redo());
        undoItem.setOnAction(e->boardViewModel.undo());
    }

    private void configDataBindings(){
        redoItem.disableProperty().bind(managerCommand.disabledRedoProperty());
        undoItem.disableProperty().bind(managerCommand.disabledUndoProperty());
        redoItem.textProperty().bind(managerCommand.redoNameProperty());
        undoItem.textProperty().bind(managerCommand.undoNameProperty());
    }



}
