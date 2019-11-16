package Controller;

import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Map map;

    private StringBuilder consoleTextAreaStringBuilder;

    private Button navigationExploreButton;
    private Button navigationPickupAllButton;

    private Button combatAttackButton;
    private Button combatExamineButton;
    private Button combatEscapeButton;

    private Button puzzleExamineButton;
    private Button puzzleAnswerButton;
    private Button puzzleHintButton;
    private Button puzzleEscapeButton;

    private Button newGameButton;
    private Button saveGameButton;
    private Button loadGameButton;

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
    private GridPane menuGridPane;

    @FXML
    private Tab navigationTab;
    @FXML
    private Tab inventoryTab;
    @FXML
    private Tab combatTab;
    @FXML
    private Tab puzzleTab;
    @FXML
    private Tab menuTab;

    @FXML
    private TabPane tabPane;

    @FXML
    private StackPane imageStackPane;

    @FXML
    private TextArea consoleTextArea;
    @FXML
    private TextArea newGameTextArea;
    @FXML
    private TextArea loadGameTextArea;

    @FXML
    private TextField consoleTextField;

    @FXML
    private AnchorPane beginAnchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        consoleTextAreaStringBuilder = new StringBuilder();

        try {
            map = new Map();
            map.loadNewGame();
        } catch (FileNotFoundException e) {
            consoleTextArea.setText(e.getMessage());
            System.exit(0);
        }

        imageStackPane.getStyleClass().add("imagebg");

        navigationGridPane.getChildren().clear();

        setUpMiniMap();
        updateMiniMap();

        setUpNavigationGridPane();
        setUpInventoryGridPane();
        setUpCombatGridPane();
        setUpPuzzleGridPane();
        setUpMenuGridPane();

        setUpConsoleTextFieldEventHandler();

        beginGameViewButtonsEventHandlerCreateHelper();

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

    private void resetAndUpdateGameView() {
        updateMiniMap();
        setUpNavigationGridPane();
        setUpInventoryGridPane();
        showDefaultView();
        consoleTextAreaStringBuilder.setLength(0);
        roomDescriptionAssembler();
        updateConsoleTextArea();
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

    private void showBeginGameView() {
        beginAnchorPane.setVisible(true);
    }

    private void showDefaultView() {
        tabPane.getTabs().clear();
        tabPane.getTabs().addAll(navigationTab, inventoryTab, menuTab);
        tabPane.getSelectionModel().select(navigationTab);
    }

    private void showBattleView() {
        tabPane.getTabs().clear();
        tabPane.getTabs().addAll(combatTab, inventoryTab);
        tabPane.getSelectionModel().select(combatTab);
    }

    private void showPuzzleView() {
        tabPane.getTabs().clear();
        tabPane.getTabs().addAll(puzzleTab, inventoryTab);
        tabPane.getSelectionModel().select(puzzleTab);
    }

    private void gridPaneButtonsHelperCreateHelper(GridPane gridPane, Button button, int columnIndex, int rowIndex) {
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setValignment(button, VPos.CENTER);
        gridPane.add(button, columnIndex, rowIndex);
    }

    private void beginGameViewButtonsEventHandlerCreateHelper() {
        newGameTextArea.setOnMouseClicked(event -> {
            try {
                map.loadNewGame();
                resetAndUpdateGameView();
            } catch (FileNotFoundException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }
            beginAnchorPane.setVisible(false);
            updateConsoleTextArea();
        });

        loadGameTextArea.setOnMouseClicked(event -> {
            try {
                map.loadSavedGame();
                resetAndUpdateGameView();
            } catch (FileNotFoundException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }
            beginAnchorPane.setVisible(false);
            updateConsoleTextArea();
        });
    }

    private void menuButtonsEventHandlerCreateHelper() {

        newGameButton.setOnAction(event -> {
            showBeginGameView();
        });

        saveGameButton.setOnAction(event -> {
            try {
                map.saveGameProgress();
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append("Game Saved!");
            } catch (FileNotFoundException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append("Unable to save game.");
            }
            updateConsoleTextArea();
        });

        loadGameButton.setOnAction(event -> {
            try {
                map.loadSavedGame();
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append("Game loaded!");
                resetAndUpdateGameView();
            } catch (FileNotFoundException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append("Game failed to load.");
            }
            updateConsoleTextArea();
        });
    }

    private void menuButtonsCreateHelper() {
        newGameButton = new Button("New Game");
        gridPaneButtonsHelperCreateHelper(menuGridPane, newGameButton, 1, 0);

        saveGameButton = new Button("Save Game");
        gridPaneButtonsHelperCreateHelper(menuGridPane, saveGameButton, 1, 2);

        loadGameButton = new Button("Load Game");
        gridPaneButtonsHelperCreateHelper(menuGridPane, loadGameButton, 1, 4);

        menuButtonsEventHandlerCreateHelper();
    }

    private void setUpMenuGridPane() {
        menuButtonsCreateHelper();
    }

    private void puzzleButtonsEventHandlerCreateHelper() {
        puzzleExamineButton.setOnAction(event -> {
            try {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getPuzzleQuestion());
            } catch (InvalidPuzzleException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }

            updateConsoleTextArea();
        });

        puzzleAnswerButton.setOnAction(event -> {
            consoleTextAreaStringBuilder.append("\n");
            consoleTextAreaStringBuilder.append("You have answered: " + consoleTextField.getText());

            try {
                if (map.solveCurrentRoomPuzzle(consoleTextField.getText())) {
                    consoleTextAreaStringBuilder.append("\n");
                    consoleTextAreaStringBuilder.append("Solved! Well done.");
                    showDefaultView();
                } else {
                    map.puzzleReduceAttemptsRemaining();
                    consoleTextAreaStringBuilder.append("\n");
                    consoleTextAreaStringBuilder.append("Wrong!");
                    consoleTextAreaStringBuilder.append("\n" + map.getPuzzleAttemptsRemaining() + " attempts left.");
                    if (map.getPuzzleAttemptsRemaining() <= 0) {
                        consoleTextAreaStringBuilder.append("\n");
                        consoleTextAreaStringBuilder.append("You take " + map.applyPuzzleDamageToPlayer() + " damage.");
                        map.movePlayerToPreviousRoom();
                        consoleTextAreaStringBuilder.append("\n");
                        consoleTextAreaStringBuilder.append("You manage to escape to the previous room.");
                        showDefaultView();
                        setUpNavigationGridPane();
                        updateMiniMap();
                    }
                }
            } catch (InvalidPuzzleException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }

            updateConsoleTextArea();
        });

        puzzleHintButton.setOnAction(event -> {
            try {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getPuzzleHint());
            } catch (InvalidPuzzleException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }

            updateConsoleTextArea();
        });

        puzzleEscapeButton.setOnAction(event -> {
            try {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append("You manage to escape to the previous room.");
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append("You take " + map.applyPuzzleDamageToPlayer() + " damage.");
                map.movePlayerToPreviousRoom();
                showDefaultView();
                setUpNavigationGridPane();
                updateMiniMap();

                if (map.getPlayerHealth() < 1) {
                    consoleTextAreaStringBuilder.append("\nYou have been slain!");
                    showBeginGameView();
                }

            } catch (InvalidPuzzleException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }

            updateConsoleTextArea();
        });

    }

    private void puzzleButtonsCreateHelper() {
        puzzleExamineButton = new Button("Examine");
        gridPaneButtonsHelperCreateHelper(puzzleGridPane, puzzleExamineButton, 0, 0);

        puzzleAnswerButton = new Button("Answer");
        gridPaneButtonsHelperCreateHelper(puzzleGridPane, puzzleAnswerButton, 0, 1);

        puzzleHintButton = new Button("Hint");
        gridPaneButtonsHelperCreateHelper(puzzleGridPane, puzzleHintButton, 0, 2);

        puzzleEscapeButton = new Button("Escape");
        gridPaneButtonsHelperCreateHelper(puzzleGridPane, puzzleEscapeButton, 0, 3);

        puzzleButtonsEventHandlerCreateHelper();
    }

    private void setUpPuzzleGridPane() {
        puzzleButtonsCreateHelper();
    }

    private void battleDescriptionAssembler() {
        try {
            consoleTextAreaStringBuilder.setLength(0);
            consoleTextAreaStringBuilder.append("\nYou deal " + map.getPlayerDamage() + " damage!");
            consoleTextAreaStringBuilder.append("\n" + map.getMonsterName() + " deals " + map.getMonsterDamage() + " damage to you!");
            consoleTextAreaStringBuilder.append("\nHealth:" + map.getPlayerHealth());
            consoleTextAreaStringBuilder.append("\t\t" + map.getMonsterName() + " Health:" + map.getMonsterHealth());
            consoleTextAreaStringBuilder.append("\nDamage:" + map.getPlayerDamage());
            consoleTextAreaStringBuilder.append("\t" + map.getMonsterName() + " Damage:" + map.getMonsterDamage());

        } catch (InvalidMonsterException e) {
            consoleTextAreaStringBuilder.append("\n");
            consoleTextAreaStringBuilder.append(e.getMessage());
        }
    }

    private void combatButtonEventHandlersCreateHelper() {
        combatAttackButton.setOnAction(event -> {
            try {
                map.playerAttacksMonster();
                map.monsterAttacksPlayer();

                battleDescriptionAssembler();

                if (map.getPlayerHealth() < 1) {
                    consoleTextAreaStringBuilder.append("\nYou have been slain!");
                    showBeginGameView();
                }

                if (map.getPlayerHealth() > 0 && map.getMonsterHealth() < 1) {
                    consoleTextAreaStringBuilder.append("\nYou are Victorious!");
                    showDefaultView();
                }
            } catch (InvalidMonsterException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }
            updateConsoleTextArea();
        });

        combatExamineButton.setOnAction(event -> {
            try {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(map.getMonsterDescription());
            } catch (InvalidMonsterException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }
            updateConsoleTextArea();
        });

        combatEscapeButton.setOnAction(event -> {
            map.movePlayerToPreviousRoom();
            consoleTextAreaStringBuilder.append("\n");
            consoleTextAreaStringBuilder.append("You manage to escape to the previous room.");
            showDefaultView();
            setUpNavigationGridPane();
            updateMiniMap();
            updateConsoleTextArea();
        });
    }

    private void combatButtonsCreateHelper() {
        combatAttackButton = new Button("Attack");
        gridPaneButtonsHelperCreateHelper(combatGridPane, combatAttackButton, 0, 0);

        combatExamineButton = new Button("Examine");
        gridPaneButtonsHelperCreateHelper(combatGridPane, combatExamineButton, 0, 1);

        combatEscapeButton = new Button("Escape");
        gridPaneButtonsHelperCreateHelper(combatGridPane, combatEscapeButton, 0, 2);

        combatButtonEventHandlersCreateHelper();
    }

    private void setUpCombatGridPane() {
        combatButtonsCreateHelper();
    }

    private void navigationButtonsNotInFXMLEventHandlerHelper() {
        navigationPickupAllButton.setOnAction(event -> {
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

        navigationExploreButton.setOnAction((event -> {
            roomDescriptionAssembler();
            updateConsoleTextArea();
        }));
    }

    private void navigationButtonsNotInFXMLCreateHelper() {
        navigationExploreButton = new Button("Explore");
        gridPaneButtonsHelperCreateHelper(navigationGridPane, navigationExploreButton, 2, 2);

        navigationPickupAllButton = new Button("PickUp All");
        gridPaneButtonsHelperCreateHelper(navigationGridPane, navigationPickupAllButton, 2, 1);

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
                    try {
                        if (!map.isCurrentRoomPuzzleSolved() && map.getPuzzleAttemptsRemaining() > 0) {
                            showPuzzleView();
                        }
                    } catch (InvalidPuzzleException e) {
                        consoleTextAreaStringBuilder.append("\n");
                        consoleTextAreaStringBuilder.append(e.getMessage());
                    }
                } else {
                    showBattleView();
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

    private void inventoryHealButtonEventHandlerCreateHelper(Button healButton, int index) {
        healButton.setOnAction(event -> {
            try {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append("Used " + map.getPlayerInventory().get(index) + ".");
                map.equipPlayerItem(map.getPlayerInventory().get(index));
                map.playerHeal();
                consoleTextAreaStringBuilder.append("\nHealth restored!");
            } catch (InvalidItemException e) {
                consoleTextAreaStringBuilder.append("\n");
                consoleTextAreaStringBuilder.append(e.getMessage());
            }
            setUpInventoryGridPane();
            updateConsoleTextArea();
        });
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
            if (map.getPlayerInventoryItemType(index).equals("C")) {
                Button healButton = new Button("Heal");
                gridPaneButtonsHelperCreateHelper(inventoryGridPane, healButton, 1, index);
                inventoryHealButtonEventHandlerCreateHelper(healButton, index);
            } else {

                if (map.compareEquipedPlayerItem(map.getPlayerInventory().get(index))) {
                    Button unequipButton = new Button("Unequip");
                    gridPaneButtonsHelperCreateHelper(inventoryGridPane, unequipButton, 1, index);
                    inventoryUnequipButtonEventHandlerCreateHelper(unequipButton, index);
                } else {
                    Button equipButton = new Button("Equip");
                    gridPaneButtonsHelperCreateHelper(inventoryGridPane, equipButton, 1, index);
                    inventoryEquipButtonEventHandlerCreateHelper(equipButton, index);
                }
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
                consoleTextAreaStringBuilder.append("-Commands-" + "\nmove to <roomNumber>" + "\nstats: Player Stats" + "\nI: Inventory" + "\npickup <item>: add <item> to inventory" + "\ndrop <item>: Discard <item> to current room" + "\ninspect <item>: Look at <item>" + "\nunequip: Empty players hand" + "\nequip <item>: Place <item> from inventory to players hand" + "\nheal: Use consumable" + "\nexplore: List items in the room" + "\nclear: Clear all text from console.");
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