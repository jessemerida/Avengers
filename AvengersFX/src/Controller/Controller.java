package Controller;

import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Map map;

    private StringBuilder consoleTextAreaStringBuilder;

    private Button exploreButton;
    private Button pickupAllButton;

    @FXML
    private GridPane navigationGridPane;

    @FXML
    private GridPane inventoryGridPane;

    @FXML
    private StackPane imageStackPane;

    @FXML
    private TextArea consoleTextArea;

    @FXML
    private TextField consoleTextField;

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
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        navigationGridPane.getChildren().clear();
        imageStackPane.getStyleClass().add("imagebg4");

        setUpNavigationGridPane();
        initializeNavigationButtonsNotInFXML();

        setUpConsoleTextFieldEventHandler();

        setUpButtonsNotInFXMLEventHandler();


        roomDescriptionAssembler();
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

    private void roomDescriptionAssembler() {
        consoleTextAreaStringBuilder.setLength(0);
        consoleTextAreaStringBuilder.append("\n");
        consoleTextAreaStringBuilder.append(map.getCurrentRoomDescription());
        consoleTextAreaStringBuilder.append("\n");
        consoleTextAreaStringBuilder.append(map.getCurrentRoomItems());
        consoleTextAreaStringBuilder.append("\n");
        consoleTextAreaStringBuilder.append(map.getCurrentRoomValidConnections());
    }

    private void initializeNavigationButtonsNotInFXML() {
        exploreButton = new Button("Explore");
        initializeNavigationButtonsNotInFXMLHelper(exploreButton, 2, 2);
        pickupAllButton = new Button("PickUp All");
        initializeNavigationButtonsNotInFXMLHelper(pickupAllButton, 2, 1);
    }

    private void initializeNavigationButtonsNotInFXMLHelper(Button buttonsNotInFXML, int columnIndex, int rowIndex) {
        GridPane.setHalignment(buttonsNotInFXML, HPos.CENTER);
        GridPane.setValignment(buttonsNotInFXML, VPos.CENTER);
        navigationGridPane.add(buttonsNotInFXML, columnIndex, rowIndex);
    }

    private void setUpButtonsNotInFXMLEventHandler() {
        pickupAllButton.setOnAction(event -> {
            try {
                for (int i = 0; i < map.getCurrentRoomItems().size(); ) {
                    consoleTextAreaStringBuilder.append("\n");
                    consoleTextAreaStringBuilder.append("Picked up " + map.getCurrentRoomItems().get(0) + ".");
                    map.pickupPlayerItem(map.getCurrentRoomItems().get(0));
                    updateConsoleTextArea();
                }
                setUpInventoryGridPane();
            } catch (InvalidItemException e) {
                consoleTextAreaStringBuilder.setLength(0);
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append("Could not pick up all items.");
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
                updateConsoleTextArea();
            }
        });

        exploreButton.setOnAction((event -> {
            roomDescriptionAssembler();
            updateConsoleTextArea();
        }));
    }

    private void navigationButtonEventHandlerCreateHelper(Button button, int index) {
        button.setOnAction((event -> {
            try {
                map.movePlayerTo(map.getCurrentRoomValidConnections().get(index));
                roomDescriptionAssembler();
            } catch (InvalidRoomException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }
            updateConsoleTextArea();
            setUpNavigationGridPane();
        }));

    }

    private void navigationButtonCreateHelper(String name, int index) {
        Button button = new Button(name);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setValignment(button, VPos.CENTER);
        navigationButtonEventHandlerCreateHelper(button, index);
        navigationGridPane.add(button, index, 4);
    }

    private void setUpNavigationGridPane() {
        navigationGridPane.getChildren().clear();
        ArrayList<String> connections = map.getCurrentRoomValidConnections();
        for (int i = 0; i < connections.size(); i++) {
            navigationButtonCreateHelper(connections.get(i), i);
        }
    }

    private void inventoryNameButtonEventHandlerCreateHelper(Button nameButton, int index) {
        nameButton.setOnAction((event -> {
            try {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getPlayerInventory().get(index));
                updateConsoleTextArea();
            } catch (InvalidItemException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }
        }));
    }

    private void inventoryEquipButtonEventHandlerCreateHelper(Button equipButton, int index) {
        equipButton.setOnAction(event -> {
            try {
                map.equipPlayerItem(map.getPlayerInventory().get(index));
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getPlayerInventory().get(index) + " equipped.");
                updateConsoleTextArea();
                setUpInventoryGridPane();
            } catch (InvalidItemException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }
        });
    }

    private void inventoryUnequipButtonEventHandlerCreateHelper(Button equipButton, int index) {
        equipButton.setOnAction(event -> {
            try {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getPlayerInventory().get(index) + " unequipped.");
                map.unequipPlayerItem();
                updateConsoleTextArea();
                setUpInventoryGridPane();
            } catch (InvalidItemException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }
        });
    }

    private void inventoryDropButtonEventHandlerCreateHelper(Button dropButton, int index) {
        dropButton.setOnAction(event -> {
            try {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getPlayerInventory().get(index) + " dropped.");
                map.dropPlayerItem(map.getPlayerInventory().get(index));
                updateConsoleTextArea();
                setUpInventoryGridPane();
            } catch (InvalidItemException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }
        });
    }

    private void inventoryButtonsCreateHelper(String name, int index) {
        Button nameButton = new Button(name);
        inventoryGridPane.add(nameButton, 0, index);
        inventoryNameButtonEventHandlerCreateHelper(nameButton, index);

        try {
            if (map.compareEquipedPlayerItem(map.getPlayerInventory().get(index))) {
                Button unequipButton = new Button("Unequip");
                inventoryGridPane.add(unequipButton, 1, index);
                inventoryUnequipButtonEventHandlerCreateHelper(unequipButton, index);
            } else {
                Button equipButton = new Button("Equip");
                inventoryGridPane.add(equipButton, 1, index);
                inventoryEquipButtonEventHandlerCreateHelper(equipButton, index);
            }
        } catch (InvalidItemException e) {
            consoleTextAreaStringBuilder.setLength(0);
            consoleTextAreaStringBuilder.append("\n");
            consoleTextAreaStringBuilder.append(e.getMessage());
            updateConsoleTextArea();
        }

        Button dropButton = new Button("Drop");
        inventoryDropButtonEventHandlerCreateHelper(dropButton, index);
        inventoryGridPane.add(dropButton, 2, index);
    }

    private void setUpInventoryGridPane() {
        inventoryGridPane.getChildren().clear();
        try {
            ArrayList<String> inventory = map.getPlayerInventory();
            for (int i = 0; i < inventory.size(); i++) {
                inventoryButtonsCreateHelper(inventory.get(i), i);
            }
        } catch (InvalidItemException e) {
            consoleTextAreaStringBuilder.append("\n");
            consoleTextAreaStringBuilder.append(e.getMessage());
        }
    }

    private void setUpConsoleTextFieldEventHandler() {
        consoleTextField.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                consoleTextAreaStringBuilder.setLength(0);
                consoleTextFieldCommands();
                consoleTextField.setText("");
                updateConsoleTextArea();
            }
        });
    }

    private void consoleTextFieldCommands() {
        String command = consoleTextField.getText();
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
                consoleTextAreaStringBuilder.append("Connections: " + map.getCurrentRoomValidConnections());
            } else if (command.equalsIgnoreCase("stats")) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getPlayerStats());
            } else if (command.equalsIgnoreCase("I")) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getPlayerInventory().toString());
            } else if (command.contains("pickup")) {
                consoleTextAreaStringBuilder.append("\n");
                String string = command.split(" ")[1];
                consoleTextAreaStringBuilder.append(map.pickupPlayerItem(string));
                setUpInventoryGridPane();
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
                consoleTextAreaStringBuilder.append("Connections:" + map.getCurrentRoomValidConnections());
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
            consoleTextAreaStringBuilder.append("\nRoom Exception from Commands\n");
            consoleTextAreaStringBuilder.append(e.getMessage());
        } catch (GameException e) {
            consoleTextAreaStringBuilder.append("\nGame Exception from Commands\n");
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
            consoleTextAreaStringBuilder.append("\nPuzzle Exception from Puzzle Part");
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