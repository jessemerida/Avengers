package Controller;

import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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

    private Button attackButton;
    private Button examineMonsterButton;
    private Button escapeButton;

    @FXML
    private Rectangle rec1;
    @FXML
    private Rectangle rec2;
    @FXML
    private Rectangle rec3;
    @FXML
    private Rectangle rec4;
    @FXML
    private Rectangle rec5;
    @FXML
    private Rectangle rec6;
    @FXML
    private Rectangle rec7;
    @FXML
    private Rectangle rec8;
    @FXML
    private Rectangle rec9;
    @FXML
    private Rectangle rec10;

    ArrayList<Rectangle> rectangleArrayList;

    @FXML
    private GridPane navigationGridPane;
    @FXML
    private GridPane inventoryGridPane;
    @FXML
    private GridPane combatGridPane;
    @FXML
    private GridPane puzzleGridPane;

    @FXML
    private Tab navigationTab;
    @FXML
    private Tab inventoryTab;
    @FXML
    private Tab combatTab;
    @FXML
    private Tab puzzleTab;

    @FXML
    private TabPane tabPane;

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

        imageStackPane.getStyleClass().add("imagebg");

        navigationGridPane.getChildren().clear();

        setUpMiniMap();
        updateMiniMap();

        setUpNavigationGridPane();
        setUpInventoryGridPane();
        setUpCombatGridPane();

        setUpConsoleTextFieldEventHandler();

        showDefaultView();

        roomDescriptionAssembler();
        updateConsoleTextArea();
    }

    @FXML
    private void printLog(String message) {
        System.out.println(new Date().toString() + "\n" + message);
    }

    private void setUpMiniMap() {
        rectangleArrayList = new ArrayList<>();
        rectangleArrayList.add(rec1);
        rectangleArrayList.add(rec2);
        rectangleArrayList.add(rec3);
        rectangleArrayList.add(rec4);
        rectangleArrayList.add(rec5);
        rectangleArrayList.add(rec6);
        rectangleArrayList.add(rec7);
        rectangleArrayList.add(rec8);
        rectangleArrayList.add(rec9);
        rectangleArrayList.add(rec10);
    }

    private void updateMiniMap() {
        int index = Integer.parseInt(map.getCurrentRoomID());

        for (int i = 0; i < rectangleArrayList.size(); i++) {
            if (index < 11) {
                if (map.getIsVisited(i)) {
                    rectangleArrayList.get(i).setFill(Color.GRAY);
                } else {
                    rectangleArrayList.get(i).setFill(Color.WHITE);
                }
            } else if (index < 21) {
                if (map.getIsVisited(i + 10)) {
                    rectangleArrayList.get(i).setFill(Color.GRAY);
                } else {
                    rectangleArrayList.get(i).setFill(Color.WHITE);
                }
            } else {
                if (map.getIsVisited(i + 20)) {
                    rectangleArrayList.get(i).setFill(Color.GRAY);
                } else {
                    rectangleArrayList.get(i).setFill(Color.WHITE);
                }
            }
        }


        if (index > 20) {
            index -= 20;
        }
        if (index > 10) {
            index -= 10;
        }

        rectangleArrayList.get(index - 1).setFill(Color.web("#8effaa"));
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

    private void showDefaultView() {
        tabPane.getTabs().clear();
        tabPane.getTabs().addAll(navigationTab, inventoryTab);
    }

    private void showBattleView() {
        tabPane.getTabs().clear();
        tabPane.getTabs().addAll(combatTab, inventoryTab);
    }

    private void gridPaneButtonsHelperCreateHelper(GridPane gridPane, Button button, int columnIndex, int rowIndex) {
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setValignment(button, VPos.CENTER);
        gridPane.add(button, columnIndex, rowIndex);
    }

    private void combatButtonEventHandlersCreateHelper() {
        attackButton.setOnAction(event -> {
            consoleTextAreaStringBuilder.append("\n");
            consoleTextAreaStringBuilder.append("Mad deeps.");
            updateConsoleTextArea();
        });

        examineMonsterButton.setOnAction(event -> {
            try {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getMonsterDescription());
            } catch (InvalidMonsterException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }
            updateConsoleTextArea();
        });

        escapeButton.setOnAction(event -> {
            consoleTextAreaStringBuilder.append("\n");
            consoleTextAreaStringBuilder.append("RUN AWAY! Doesn't work yet.");
            updateConsoleTextArea();
        });
    }

    private void combatButtonsCreateHelper() {
        attackButton = new Button("Attack");
        gridPaneButtonsHelperCreateHelper(combatGridPane, attackButton, 0, 0);

        examineMonsterButton = new Button("Examine");
        gridPaneButtonsHelperCreateHelper(combatGridPane, examineMonsterButton, 0, 1);

        escapeButton = new Button("Escape");
        gridPaneButtonsHelperCreateHelper(combatGridPane, escapeButton, 0, 2);
        combatButtonEventHandlersCreateHelper();
    }

    private void setUpCombatGridPane() {
        combatButtonsCreateHelper();
    }

    private void navigationButtonsNotInFXMLEventHandlerHelper() {
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

    private void navigationButtonsNotInFXMLCreateHelper() {
        exploreButton = new Button("Explore");
        gridPaneButtonsHelperCreateHelper(navigationGridPane, exploreButton, 2, 2);

        pickupAllButton = new Button("PickUp All");
        gridPaneButtonsHelperCreateHelper(navigationGridPane, pickupAllButton, 2, 1);

        navigationButtonsNotInFXMLEventHandlerHelper();
    }

    private void navigationButtonEventHandlerCreateHelper(Button button, int index) {
        button.setOnAction((event -> {
            try {
                if (map.getIfPlayerEquippedKeyItem() && map.isConnectionLocked(map.getCurrentRoomValidConnections().get(index))) {
                    map.playerUnlocksDoor(map.getCurrentRoomValidConnections().get(index));
                    consoleTextAreaStringBuilder.append("\n");
                    consoleTextAreaStringBuilder.append("Door " + map.getCurrentRoomValidConnections().get(index) + " unlocked!");
                    setUpInventoryGridPane();
                } else {
                    map.movePlayerTo(map.getCurrentRoomValidConnections().get(index));
                    roomDescriptionAssembler();
                }

                if (map.isCurrentRoomMonsterDead()) {

                } else {
                    consoleTextAreaStringBuilder.append("\nMonster Alive");
                    //showBattleView();
                }

            } catch (InvalidRoomException e) {
                consoleTextAreaStringBuilder.append("\nRoomException\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            } catch (InvalidItemException e) {
                consoleTextAreaStringBuilder.append("\nItemException\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }
            updateMiniMap();
            updateConsoleTextArea();
            setUpNavigationGridPane();
        }));

    }

    private void navigationButtonCreateHelper(String name, int index) {
        Button button = new Button(name);

        for (int i = 0; i < imageStackPane.getStyleClass().size(); ) {
            imageStackPane.getStyleClass().remove(0);
        }

        switch (map.getCurrentRoomPattern()) {
            case 1:
                imageStackPane.getStyleClass().add("imagebg4");
                gridPaneButtonsHelperCreateHelper(navigationGridPane, button, index, 4);
                break;
            case 2:
                imageStackPane.getStyleClass().add("imagebg2");
                if (index < 1) {
                    gridPaneButtonsHelperCreateHelper(navigationGridPane, button, 2, 0);
                } else {
                    gridPaneButtonsHelperCreateHelper(navigationGridPane, button, 2, 4);
                }
                break;
            case 3:
                imageStackPane.getStyleClass().add("imagebg1");
                gridPaneButtonsHelperCreateHelper(navigationGridPane, button, 2, 0);
                break;
            case 4:
                imageStackPane.getStyleClass().add("imagebg3");
                if (index < 2) {
                    if (index < 1) {
                        gridPaneButtonsHelperCreateHelper(navigationGridPane, button, 2, 0);
                    } else {
                        gridPaneButtonsHelperCreateHelper(navigationGridPane, button, 4, 0);
                    }
                } else {
                    gridPaneButtonsHelperCreateHelper(navigationGridPane, button, 2, 4);
                }
                break;
            case 5:
                imageStackPane.getStyleClass().add("imagebg0");
                if (index < 1) {
                    gridPaneButtonsHelperCreateHelper(navigationGridPane, button, 2, 0);
                } else {
                    gridPaneButtonsHelperCreateHelper(navigationGridPane, button, 4, 0);
                }
                break;
            default:
                gridPaneButtonsHelperCreateHelper(navigationGridPane, button, index, 4);
                break;
        }

        navigationButtonEventHandlerCreateHelper(button, index);
    }

    private void setUpNavigationGridPane() {
        navigationGridPane.getChildren().clear();
        navigationButtonsNotInFXMLCreateHelper();
        ArrayList<String> connections = map.getCurrentRoomValidConnections();
        for (int i = 0; i < connections.size(); i++) {
            navigationButtonCreateHelper(connections.get(i), i);
        }
    }

    private void inventoryNameButtonEventHandlerCreateHelper(Button nameButton, int index) {
        nameButton.setOnAction((event -> {
            try {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getPlayerInventory().get(index) + " ");
                consoleTextAreaStringBuilder.append(map.inspectItem(map.getPlayerInventory().get(index)));
            } catch (InvalidItemException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }
            updateConsoleTextArea();
        }));
    }

    private void inventoryEquipButtonEventHandlerCreateHelper(Button equipButton, int index) {
        equipButton.setOnAction(event -> {
            try {
                map.equipPlayerItem(map.getPlayerInventory().get(index));
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getPlayerInventory().get(index) + " equipped.");
                setUpInventoryGridPane();
            } catch (InvalidItemException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }
            updateConsoleTextArea();
        });
    }

    private void inventoryUnequipButtonEventHandlerCreateHelper(Button equipButton, int index) {
        equipButton.setOnAction(event -> {
            try {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getPlayerInventory().get(index) + " unequipped.");
                map.unequipPlayerItem();
                setUpInventoryGridPane();
            } catch (InvalidItemException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }
            updateConsoleTextArea();
        });
    }

    private void inventoryDropButtonEventHandlerCreateHelper(Button dropButton, int index) {
        dropButton.setOnAction(event -> {
            try {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getPlayerInventory().get(index) + " dropped.");
                map.dropPlayerItem(map.getPlayerInventory().get(index));
                setUpInventoryGridPane();
            } catch (InvalidItemException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }
            updateConsoleTextArea();
        });
    }

    private void inventoryButtonsCreateHelper(String name, int index) {
        Button nameButton = new Button(name);
        gridPaneButtonsHelperCreateHelper(inventoryGridPane, nameButton, 0, index);
        inventoryNameButtonEventHandlerCreateHelper(nameButton, index);

        try {
            if (map.compareEquipedPlayerItem(map.getPlayerInventory().get(index))) {
                Button unequipButton = new Button("Unequip");
                gridPaneButtonsHelperCreateHelper(inventoryGridPane, unequipButton, 1, index);
                inventoryUnequipButtonEventHandlerCreateHelper(unequipButton, index);
            } else {
                Button equipButton = new Button("Equip");
                gridPaneButtonsHelperCreateHelper(inventoryGridPane, equipButton, 1, index);
                inventoryEquipButtonEventHandlerCreateHelper(equipButton, index);
            }
        } catch (InvalidItemException e) {
            printLog("inventoryButtonsCreateHelper shouldn't print?");
            consoleTextAreaStringBuilder.setLength(0);
            consoleTextAreaStringBuilder.append("\n");
            consoleTextAreaStringBuilder.append(e.getMessage());
            updateConsoleTextArea();
        }

        Button dropButton = new Button("Drop");
        gridPaneButtonsHelperCreateHelper(inventoryGridPane, dropButton, 2, index);
        inventoryDropButtonEventHandlerCreateHelper(dropButton, index);
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

    private void setUpConsoleTextFieldEventHandler() {
        consoleTextField.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                consoleTextAreaStringBuilder.setLength(0);
                consoleTextFieldCommands();
                consoleTextField.setText("");
                updateConsoleTextArea();
            }
        });

        consoleTextField.setOnMouseClicked(event -> {
            consoleTextField.setText("");
        });
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