package Controller;

import Model.GameException;
import Model.InvalidPuzzleException;
import Model.InvalidRoomException;
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

        consoleTextAreaStringBuilder.append(map.getCurrentRoomDescription());
        consoleTextAreaStringBuilder.append("\n");
        consoleTextAreaStringBuilder.append(map.getCurrentRoomItems());
        consoleTextAreaStringBuilder.append("\n");
        consoleTextAreaStringBuilder.append("Connections:" + map.getCurrentRoomConnections());

        updateConsoleTextArea();
    }

    @FXML
    public void printLog(String message) {
        System.out.println(new Date().toString() + "\n" + message);
    }

    private void returnToResults() {
        //consoleTextArea.appendText("");
        consoleTextArea.layout();
        consoleTextArea.setScrollTop(Double.MAX_VALUE);
        //consoleTextArea.selectPositionCaret(consoleTextArea.getLength());
        //consoleTextArea.deselect(); //removes the highlighting
    }

    private void updateConsoleTextArea() {
        consoleTextArea.setText(consoleTextAreaStringBuilder.toString());
        returnToResults();
    }

    private void consoleTextFieldEventHandlerSetUp() {
        consoleTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    consoleTextFieldCommands();
                    consoleTextField.setText("");
                    updateConsoleTextArea();
                }
            }
        });
    }

    private void consoleTextFieldCommands() {
        String command = consoleTextField.getText().toLowerCase();
        try {
            if (consoleTextField.getText().equalsIgnoreCase("Commands")) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append("-Commands-" + "\nmove to <roomNumber>" + "\nstats: Player Stats" + "\nI: Inventory" + "\npickup <item>: add <item> to inventory" + "\ndrop <item>: Discard <item> to current room" + "\ninspect <item>: Look at <item>" + "\nunequip: Empty players hand" + "\nequip <item>: Place <item> from inventory to players hand" + "\nheal: Use consumable" + "\nexplore: List items in the room");
            } else if (command.contains("move to")) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.movePlayerTo(command.split(" ")[2]));
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getCurrentRoomItems());
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append("Connections: " + map.getCurrentRoomConnections());
            } else if (command.equalsIgnoreCase("stats")) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getPlayerStats());
            } else if (command.equalsIgnoreCase("I")) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getPlayerInventory().toString());
            } else if (command.contains("pickup")) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.pickupPlayerItem(command.split(" ")[1]));
            } else if (command.contains("drop")) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.dropPlayerItem(command.split(" ")[1]));
            } else if (command.contains("inspect")) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.inspectItem(command.split(" ")[1]));
            } else if (command.equalsIgnoreCase("unequip")) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.unequipPlayerItem());
            } else if (command.contains("equip")) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.equipPlayerItem(command.split(" ")[1]));
            } else if (command.equalsIgnoreCase("heal")) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.playerHeal());
            } else if (command.equalsIgnoreCase("explore")) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getCurrentRoomDescription());
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getCurrentRoomItems());
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append("Connections:" + map.getCurrentRoomConnections());
            } else if (command.contains("unlock")) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.playerUnlocksDoor(command.split(" ")[1]));
            } else if (command.equalsIgnoreCase("clear")) {
                consoleTextAreaStringBuilder.setLength(0);
            } else {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append("Please ented a valid command. \nEnter 'Commands' to see a list of valid commands");
            }
        } catch (InvalidRoomException e) {
            consoleTextAreaStringBuilder.append("\nRoom Exception from Commands");
            consoleTextAreaStringBuilder.append(e.getMessage());
        } catch (GameException e) {
            consoleTextAreaStringBuilder.append("\nGame Exception from Commands");
            consoleTextAreaStringBuilder.append(e.getMessage());
        }

        try {
            if (map.isCurrentRoomPuzzleSolved()) {
            } else {
                consoleTextAreaStringBuilder.append("\nPuzzle Unsolved!");
                if (map.getPuzzleAttempts() > 0) {
                    consoleTextAreaStringBuilder.append("\n");
                    consoleTextAreaStringBuilder.append(map.getPuzzleHint());
                }
            }
        } catch (InvalidPuzzleException e) {
            consoleTextAreaStringBuilder.append("\nPuzzle Exception");
        }


        if (map.isCurrentRoomMonsterDead()) {

        } else {
            consoleTextAreaStringBuilder.append("\nMonster Alive!");
        }

    }

    private boolean findAndActivatePuzzle() throws InvalidPuzzleException {
        for (int i = 0; i < map.getPuzzleAttempts() && map.isCurrentRoomPuzzleSolved() == false; i++) {
            consoleTextAreaStringBuilder.append(map.getPuzzleQuestion());

            String answer = consoleTextField.getText();
            if (answer.equalsIgnoreCase("hint")) {
                consoleTextAreaStringBuilder.append(map.getPuzzleHint());
                i--; //this is so using the hint does not use any of the attempts
            } else if (answer.equalsIgnoreCase("exit")) {
                return false;
            } else {
                consoleTextAreaStringBuilder.append(map.solveCurrentRoomPuzzle(answer));
            }

        }

        if (map.isCurrentRoomPuzzleSolved() == false) {
            return false;
        }
        return true;
    }

    private boolean findAndActivateMonster() {
        while (map.isCurrentRoomMonsterDead() == false) {
            try {
                consoleTextAreaStringBuilder.append("-Player-\n" + map.getPlayerStats() + "\n-Monster-\n" + map.getMonsterStats());
                Thread.sleep(10);
                consoleTextAreaStringBuilder.append("Enter Command:");

                String command = consoleTextField.getText();

                if (command.equalsIgnoreCase("commands")) {
                    consoleTextAreaStringBuilder.append("\n-Commands-" + "\nAttack: Attack the monster" + "\nHeal: Give yourself some health" + "\nStudy: Study the monster" + "\nI: Inventory" + "\nInspect <item>: Look at <item>" + "\nUnequip: Empty players hand" + "\nEquip <item>: Place <item> from inventory to players hand");

                } else if (command.equalsIgnoreCase("attack")) {
                    consoleTextAreaStringBuilder.append(map.playerAttacksMonster());

                    if (map.isCurrentRoomMonsterDead() == false) {
                        consoleTextAreaStringBuilder.append(map.monsterAttacksPlayer());
                    }

                    if (map.getPlayerHealth() <= 0) {
                        return true;
                    }

                } else if (command.equalsIgnoreCase("heal")) {
                    consoleTextAreaStringBuilder.append(map.playerHeal());

                    if (map.isCurrentRoomMonsterDead() == false) {
                        consoleTextAreaStringBuilder.append(map.monsterAttacksPlayer());
                    }

                } else if (command.equalsIgnoreCase("study")) {
                    consoleTextAreaStringBuilder.append(map.getMonsterDescription());
                } else if (command.equalsIgnoreCase("i")) {
                    consoleTextAreaStringBuilder.append(map.getPlayerInventory().toString());
                } else if (command.contains("inspect")) {
                    consoleTextAreaStringBuilder.append(map.inspectItem(command.split(" ")[1]));
                } else if (command.equalsIgnoreCase(("unequip"))) {
                    consoleTextAreaStringBuilder.append(map.unequipPlayerItem());
                } else if (command.contains("equip")) {
                    consoleTextAreaStringBuilder.append(map.equipPlayerItem(command.split(" ")[1]));
                } else if (command.equalsIgnoreCase("exit")) {
                    return false;
                } else {
                    consoleTextAreaStringBuilder.append("Please ented a valid command. \nEnter 'Commands' to see a list of valid commands");
                }
            } catch (GameException e) {
                consoleTextAreaStringBuilder.append(e.getMessage());
            } catch (InterruptedException e) {
                consoleTextAreaStringBuilder.append("THIS SHOULD NEVER PRINT");
            }

        }
        return true;
    }

}