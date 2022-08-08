package Tools;

import javafx.scene.control.Alert;

public class CustomAlerts extends Alert {
    public CustomAlerts(AlertType alertType) {
        super(alertType);
    }

//    public static Alert deathAlert(){
//
//    }
//    public static Alert treasureAlert(){
//
//    }
//    public static Alert leaveIG(){
//        Alert warnIG = new Alert(Alert.AlertType.CONFIRMATION);
//        Text alertText = new Text("Are you sure you want to leave the game in progress ? If you do, you won't be able to continue it later");
//        alertText.setWrappingWidth(270);
//        warnIG.getDialogPane().setContent(alertText);
//        warnIG.setTitle("Leave Game ?");
//        Optional<ButtonType> result = warnIG.showAndWait();
//        if (result.get() == ButtonType.OK) {
//            switchToMain(actionEvent);
//        }
//        return warnIG;
//    }
}
