package View;

import ViewModel.MyViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import sample.Main;


import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;

public abstract class Controller implements Observer {
    protected MyViewModel viewModel;

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
    }

    protected void closeProgram() {
        Stage s = Main.getStage();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            s.close();
            viewModel.close();
        }

    }

    protected void newGame() throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader();
        Stage s = Main.getStage();
        Parent root = fxmlLoader.load(getClass().getResource("../View/NewGame.fxml").openStream());
        s.setScene(new Scene(root, 600, 400));
        NewGameController ng = fxmlLoader.getController();
        viewModel=Main.getViewModel();
        ng.setViewModel(viewModel);
        viewModel.addObserver(ng);
        Main.setStage(s);
        s.show();
    }


    protected ArrayList<String> getTitlesOfFiles(){
        ArrayList<String> mazeTitles = new ArrayList<>();
        File dir = new File(".");
        File[] filesList = dir.listFiles();
        for (File file : filesList) {
            if (isMeta(file.getName())){
                mazeTitles.add(file.getName());
            }
        }
        return mazeTitles;
    }

    protected void loadGame() throws IOException {
        FXMLLoader fxmlLoader1 = new FXMLLoader();
        Stage d = Main.getStage();
        Parent root = fxmlLoader1.load(getClass().getResource("../View/Load.fxml").openStream());
        Scene scene = new Scene(root,758,500);
        scene.getStylesheets().add(getClass().getResource("../View/Load.css").toExternalForm());
        d.setScene(scene);
        LoadController lc = fxmlLoader1.getController();
        lc.setViewModel(viewModel);

        Main.setStage(d);
        d.show();
    }


    protected boolean isMeta(String name){
        if (name.length()>7){

            if(name.charAt(name.length()-1)=='A' && name.charAt(name.length()-2)=='T' && name.charAt(name.length()-3)=='A' ){
                return true;
            }
        }
        return false;
    }

}
