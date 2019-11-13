//test
package Controller;

import Model.GameException;
import Model.Map;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Map map;

    private StringBuilder consoleTextAreaStringBuilder;

    @FXML
    private TextField consoleTextField;

    @FXML
    private TextArea consoleTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        consoleTextAreaStringBuilder = new StringBuilder();

        try {
            map = new Map();
        } catch (GameException e) {
            consoleTextArea.setText(e.getMessage());
        } catch (FileNotFoundException e) {
            consoleTextArea.setText(e.getMessage());
            System.exit(0);
        }

        consoleTextFieldEventHandlerSetUp();

        consoleTextAreaStringBuilder.append(map.getCurrentRoomDescription()).append("\n");
        consoleTextAreaStringBuilder.append(map.getCurrentRoomItems()).append("\n");
        consoleTextAreaStringBuilder.append("Connections:" + map.getCurrentRoomConnections()).append("\n");

        updateConsoleTextArea();
    }

    @FXML
    public void printToConsole() {
        System.out.println(new Date().toString() + "\nYou have printed to console!");
    }

    private void updateConsoleTextArea() {
        consoleTextArea.setText(consoleTextAreaStringBuilder.toString());
    }

    private void consoleTextFieldEventHandlerSetUp() {
        consoleTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    consoleTextFieldCommands();
                    updateConsoleTextArea();
                }
            }
        });
    }

    private void consoleTextFieldCommands() {
        String command = consoleTextField.getText();

        if (command.equalsIgnoreCase("Commands")) {
            consoleTextAreaStringBuilder.append("-Commands-" + "\nmove to <roomNumber>" + "\nstats: Player Stats" + "\nI: Inventory" + "\npickup <item>: add <item> to inventory" + "\ndrop <item>: Discard <item> to current room" + "\ninspect <item>: Look at <item>" + "\nunequip: Empty players hand" + "\nequip <item>: Place <item> from inventory to players hand" + "\nheal: Use consumable" + "\nexplore: List items in the room");
            consoleTextAreaStringBuilder.append("\n");
            updateConsoleTextArea();
        }
    }

}