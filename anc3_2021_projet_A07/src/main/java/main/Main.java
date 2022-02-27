package main;
import javafx.application.Application;
import javafx.stage.Stage;
import model.*;
import view.BoardView;

public class Main extends Application {

    private static DAO<Board> dataBoard=new BoardDAO();

    @Override
    public void start(Stage primaryStage) throws Exception {
       DBTrello db=new DBTrello();
       Board board= dataBoard.find(1);

       BoardView view= new BoardView(primaryStage,board);
       primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);

    }


}
