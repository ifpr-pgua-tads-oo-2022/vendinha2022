package ifpr.pgua.eic.vendinha2022.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class BaseController {
    
    public void showMessage(AlertType tipo, String msg){
        Alert alert = new Alert(tipo, msg);
        alert.showAndWait();
    }


}
