package Controller;

import Model.*;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

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

    private ArrayList<Rectangle> rectangleArrayList;

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
    @FXML
    private Rectangle playerHealthRectangle;
    @FXML
    private Rectangle playerMaxHealthRectangle;
    @FXML
    private Rectangle monsterHealthRectangle;
    @FXML
    private Rectangle monsterMaxHealthRectangle;

    @FXML
    private Label monsterNameLabel;

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
    private StackPane imageTopStackPane;
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

        setUpMiniMap();
        setUpNavigationGridPane();
        setUpInventoryGridPane();
        setUpCombatGridPane();
        setUpPuzzleGridPane();
        setUpMenuGridPane();

        setUpConsoleTextFieldEventHandler();

        beginGameViewButtonsEventHandlerCreateHelper();

        hideMonsterHealthBars();
        updateMiniMap();
        showDefaultView();

        roomDescriptionAssembler();
        updateConsoleTextArea();

    }

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

    private void consoleTextAreaStringBuilderHelper(String... messages) {
        for (String message : messages) {
            consoleTextAreaStringBuilder.append("\n");
            consoleTextAreaStringBuilder.append(message);
        }
    }

    private void resetAndUpdateGameView() {
        updateMiniMap();
        updatePlayerHealthHUD();
        setUpNavigationGridPane();
        setUpInventoryGridPane();
        showDefaultView();
        consoleTextAreaStringBuilder.setLength(0);
        roomDescriptionAssembler();
        updateConsoleTextArea();
    }

    private void roomDescriptionAssembler() {
        consoleTextAreaStringBuilder.setLength(0);
        consoleTextAreaStringBuilderHelper(map.getCurrentRoomDescription(), "Items:" + map.getCurrentRoomItems(), "Connections:" + map.getCurrentRoomValidConnections());
    }

    private void showBeginGameView() {
        beginAnchorPane.setVisible(true);
    }

    private void showMonsterHealthBars() {
        monsterHealthRectangle.setVisible(true);
        monsterMaxHealthRectangle.setVisible(true);
        monsterNameLabel.setVisible(true);
    }

    private void hideMonsterHealthBars() {
        monsterHealthRectangle.setVisible(false);
        monsterMaxHealthRectangle.setVisible(false);
        monsterNameLabel.setVisible(false);
    }

    private void showDefaultView() {
        hideMonsterHealthBars();

        tabPane.getTabs().clear();
        tabPane.getTabs().addAll(navigationTab, inventoryTab, menuTab);
        tabPane.getSelectionModel().select(navigationTab);
    }

    private void showCombatView() {
        showMonsterHealthBars();

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

    private void newGameButtonEventHandlerCreateFuctionalityHelper() {
        try {
            map.loadNewGame();
            resetAndUpdateGameView();
        } catch (FileNotFoundException e) {
            consoleTextAreaStringBuilderHelper(e.getMessage());
        }
        beginAnchorPane.setVisible(false);
        updateConsoleTextArea();
    }

    private void loadGameButtonEventHandlerCreateFunctionalityHelper() {
        try {
            map.loadSavedGame();
            resetAndUpdateGameView();
        } catch (FileNotFoundException e) {
            consoleTextAreaStringBuilderHelper(e.getMessage());
        }
        beginAnchorPane.setVisible(false);
        updateConsoleTextArea();
    }

    private void beginGameViewButtonsEventHandlerCreateHelper() {
        newGameTextArea.setOnMouseClicked(event -> newGameButtonEventHandlerCreateFuctionalityHelper());

        loadGameTextArea.setOnMouseClicked(event -> loadGameButtonEventHandlerCreateFunctionalityHelper());
    }

    private void menuButtonsEventHandlerCreateHelper() {

        newGameButton.setOnAction(event -> showBeginGameView());

        saveGameButton.setOnAction(event -> {
            try {
                map.saveGameProgress();
                consoleTextAreaStringBuilderHelper("Game Saved!");
            } catch (FileNotFoundException e) {
                consoleTextAreaStringBuilderHelper("Unable to save game.");
            }
            updateConsoleTextArea();
        });

        loadGameButton.setOnAction(event -> loadGameButtonEventHandlerCreateFunctionalityHelper());
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
                consoleTextAreaStringBuilderHelper(map.getPuzzleQuestion());
            } catch (InvalidPuzzleException e) {
                consoleTextAreaStringBuilderHelper(e.getMessage());
            }

            updateConsoleTextArea();
        });

        puzzleAnswerButton.setOnAction(event -> {
            consoleTextAreaStringBuilderHelper("You have answered: " + consoleTextField.getText());

            try {
                if (map.solveCurrentRoomPuzzle(consoleTextField.getText())) {
                    consoleTextAreaStringBuilderHelper("Solved! Well done.");
                    showDefaultView();
                } else {
                    map.puzzleReduceAttemptsRemaining();
                    consoleTextAreaStringBuilderHelper("Wrong!", map.getPuzzleAttemptsRemaining() + " attempts left.");

                    if (map.getPuzzleAttemptsRemaining() <= 0) {
                        consoleTextAreaStringBuilderHelper("You take " + map.applyPuzzleDamageToPlayer() + " damage.");

                        if (map.getPlayerHealth() < 1) {
                            consoleTextAreaStringBuilderHelper("You have been slain!");
                            tabPane.getTabs().removeAll(navigationTab, inventoryTab, menuTab);
                            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
                            pauseTransition.setOnFinished((event1) -> showBeginGameView());
                            pauseTransition.play();
                        } else {
                            map.movePlayerToPreviousRoom();

                            consoleTextAreaStringBuilderHelper("You manage to escape to the previous room.");

                            setUpNavigationGridPane();
                            updateMiniMap();
                            updatePlayerHealthHUD();
                            showDefaultView();
                        }
                    }
                }
            } catch (InvalidPuzzleException e) {
                consoleTextAreaStringBuilderHelper(e.getMessage());
            }

            consoleTextField.setText("");
            updateConsoleTextArea();
        });

        puzzleHintButton.setOnAction(event -> {
            try {
                consoleTextAreaStringBuilderHelper(map.getPuzzleHint());
            } catch (InvalidPuzzleException e) {
                consoleTextAreaStringBuilderHelper(e.getMessage());
            }

            updateConsoleTextArea();
        });

        puzzleEscapeButton.setOnAction(event -> {
            try {
                consoleTextAreaStringBuilderHelper("You manage to escape to the previous room.", "You take " + map.applyPuzzleDamageToPlayer() + " damage.");

                map.movePlayerToPreviousRoom();
                showDefaultView();
                setUpNavigationGridPane();
                updateMiniMap();
                updatePlayerHealthHUD();

                playerIsSlainScript();

            } catch (InvalidPuzzleException e) {
                consoleTextAreaStringBuilderHelper(e.getMessage());
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

    private void combatDescriptionAssembler() {
        try {
            consoleTextAreaStringBuilder.setLength(0);
            consoleTextAreaStringBuilderHelper("You deal " + map.getPlayerDamage() + " damage!", map.getMonsterName() + " deals " + map.getMonsterDamage() + " damage to you!", "Health:" + map.getPlayerHealth() + "\t\t" + map.getMonsterName() + " Health:" + map.getMonsterHealth());
        } catch (InvalidMonsterException e) {
            consoleTextAreaStringBuilderHelper(e.getMessage());
        }
    }

    private void updatePlayerHealthHUD() {
        if (map.getPlayerHealth() == map.getPlayerMaxHealth()) {
            playerHealthRectangle.setWidth(playerMaxHealthRectangle.getWidth());
        } else {
            playerHealthRectangle.setWidth(playerMaxHealthRectangle.getWidth() * (map.getPlayerHealth() % map.getPlayerMaxHealth()) / 100);
        }
    }

    private void updateMonsterHealthHUD() {
        try {
            if (map.getMonsterHealth() == map.getMonsterMaxHealth()) {
                monsterHealthRectangle.setWidth(monsterMaxHealthRectangle.getWidth());
            } else {
                monsterHealthRectangle.setWidth(monsterMaxHealthRectangle.getWidth() * (map.getMonsterHealth() % map.getMonsterMaxHealth()) / 100);
            }
        } catch (InvalidMonsterException e) {
            e.printStackTrace();
        }
    }

    private void playerIsSlainScript() {
        if (map.getPlayerHealth() < 1) {
            consoleTextAreaStringBuilderHelper("You have been slain!");
            tabPane.getTabs().removeAll(navigationTab, inventoryTab, menuTab, combatTab, puzzleTab);
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
            pauseTransition.setOnFinished((event1) -> showBeginGameView());
            pauseTransition.play();
        }
    }

    private void combatButtonEventHandlersCreateHelper() {
        combatAttackButton.setOnAction(event -> {
            try {
                map.playerAttacksMonster();
                map.monsterAttacksPlayer();

                updatePlayerHealthHUD();
                updateMonsterHealthHUD();

                combatDescriptionAssembler();

                playerIsSlainScript();

                if (!map.isCurrentRoomPuzzleSolved() && map.getPuzzleAttemptsRemaining() > 0 && map.getPlayerHealth() > 0 && map.getMonsterHealth() < 1) {
                    consoleTextAreaStringBuilderHelper("You are Victorious!");
                    showPuzzleView();
                } else if (map.getPlayerHealth() > 0 && map.getMonsterHealth() < 1) {
                    consoleTextAreaStringBuilderHelper("You are Victorious!");
                    showDefaultView();
                }

            } catch (InvalidMonsterException | InvalidPuzzleException e) {
                consoleTextAreaStringBuilderHelper(e.getMessage());
            }
            updateConsoleTextArea();
        });

        combatExamineButton.setOnAction(event -> {
            try {
                consoleTextAreaStringBuilderHelper(map.getMonsterDescription());
            } catch (InvalidMonsterException e) {
                consoleTextAreaStringBuilderHelper(e.getMessage());
            }
            updateConsoleTextArea();
        });

        combatEscapeButton.setOnAction(event -> {
            consoleTextAreaStringBuilderHelper("You manage to escape to the previous room.");
            map.movePlayerToPreviousRoom();
            setUpNavigationGridPane();
            updateMiniMap();
            showDefaultView();
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
                    consoleTextAreaStringBuilderHelper("Picked up " + map.getCurrentRoomItems().get(0) + ".");
                    map.pickupPlayerItem(map.getCurrentRoomItems().get(0));
                }
                setUpInventoryGridPane();
            } catch (InvalidItemException e) {
                consoleTextAreaStringBuilderHelper("Could not pick up all items.", e.getMessage());
            }
            updateConsoleTextArea();
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
                    consoleTextAreaStringBuilderHelper("Door " + map.getCurrentRoomValidConnections().get(index) + " unlocked!");
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
                        consoleTextAreaStringBuilderHelper(e.getMessage());
                    }
                } else {
                    updateMonsterHealthHUD();
                    showCombatView();
                }

            } catch (InvalidRoomException | InvalidItemException e) {
                consoleTextAreaStringBuilderHelper(e.getMessage());
            }

            updateMiniMap();
            setUpNavigationGridPane();
            updateConsoleTextArea();
        }));

    }

    private void navigationButtonCreateHelper(String name, int index) {
        Button button = new Button(name);

        for (int i = 0; i < imageStackPane.getStyleClass().size(); ) {
            imageStackPane.getStyleClass().remove(0);
            imageTopStackPane.getStyleClass().remove(0);
        }

        switch (map.getCurrentRoomPattern()) {
            case 1:
                imageTopStackPane.getStyleClass().add("imagebg0");
                imageStackPane.getStyleClass().add("imagebg4");
                gridPaneButtonsHelperCreateHelper(navigationGridPane, button, index, 4);
                break;
            case 2:
                imageTopStackPane.getStyleClass().add("imagebg1");
                imageStackPane.getStyleClass().add("imagebg1");
                if (index < 1) {
                    gridPaneButtonsHelperCreateHelper(navigationGridPane, button, 2, 0);
                } else {
                    gridPaneButtonsHelperCreateHelper(navigationGridPane, button, 2, 4);
                }
                break;
            case 3:
                imageTopStackPane.getStyleClass().add("imagebg1");
                imageStackPane.getStyleClass().add("imagebg0");
                gridPaneButtonsHelperCreateHelper(navigationGridPane, button, 2, 0);
                break;
            case 4:
                imageTopStackPane.getStyleClass().add("imagebg2");
                imageStackPane.getStyleClass().add("imagebg1");
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
                imageTopStackPane.getStyleClass().add("imagebg2");
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
                consoleTextAreaStringBuilderHelper(map.getPlayerInventory().get(index) + " " + map.inspectItem(map.getPlayerInventory().get(index)));
            } catch (InvalidItemException e) {
                consoleTextAreaStringBuilderHelper(e.getMessage());
            }
            updateConsoleTextArea();
        }));
    }

    private void inventoryHealButtonEventHandlerCreateHelper(Button healButton, int index) {
        healButton.setOnAction(event -> {
            try {
                consoleTextAreaStringBuilderHelper("Used " + map.getPlayerInventory().get(index) + ".", "Health restored!");

                map.equipPlayerItem(map.getPlayerInventory().get(index));
                map.playerHeal();
            } catch (InvalidItemException e) {
                consoleTextAreaStringBuilderHelper(e.getMessage());
            }

            updatePlayerHealthHUD();
            setUpInventoryGridPane();
            updateConsoleTextArea();
        });
    }

    private void inventoryEquipButtonEventHandlerCreateHelper(Button equipButton, int index) {
        equipButton.setOnAction(event -> {
            try {
                map.equipPlayerItem(map.getPlayerInventory().get(index));
                consoleTextAreaStringBuilderHelper(map.getPlayerInventory().get(index) + " equipped.");
                setUpInventoryGridPane();
            } catch (InvalidItemException e) {
                consoleTextAreaStringBuilderHelper(e.getMessage());
            }
            updateConsoleTextArea();
        });
    }

    private void inventoryUnequipButtonEventHandlerCreateHelper(Button equipButton, int index) {
        equipButton.setOnAction(event -> {
            try {
                map.unequipPlayerItem();
                consoleTextAreaStringBuilderHelper(map.getPlayerInventory().get(index) + " unequipped.");
                setUpInventoryGridPane();
            } catch (InvalidItemException e) {
                consoleTextAreaStringBuilderHelper(e.getMessage());
            }
            updateConsoleTextArea();
        });
    }

    private void inventoryDropButtonEventHandlerCreateHelper(Button dropButton, int index) {
        dropButton.setOnAction(event -> {
            try {
                map.dropPlayerItem(map.getPlayerInventory().get(index));
                consoleTextAreaStringBuilderHelper(map.getPlayerInventory().get(index) + " dropped.");
                setUpInventoryGridPane();
            } catch (InvalidItemException e) {
                consoleTextAreaStringBuilderHelper(e.getMessage());
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
                Button healButton = new Button("H");
                gridPaneButtonsHelperCreateHelper(inventoryGridPane, healButton, 1, index);
                inventoryHealButtonEventHandlerCreateHelper(healButton, index);
            } else {
                if (map.compareEquipedPlayerItem(map.getPlayerInventory().get(index))) {
                    Button unequipButton = new Button("U");
                    gridPaneButtonsHelperCreateHelper(inventoryGridPane, unequipButton, 1, index);
                    inventoryUnequipButtonEventHandlerCreateHelper(unequipButton, index);
                } else {
                    Button equipButton = new Button("E");
                    gridPaneButtonsHelperCreateHelper(inventoryGridPane, equipButton, 1, index);
                    inventoryEquipButtonEventHandlerCreateHelper(equipButton, index);
                }
            }
        } catch (InvalidItemException e) {
            printLog("inventoryButtonsCreateHelper shouldn't print?");
            consoleTextAreaStringBuilder.setLength(0);
            consoleTextAreaStringBuilderHelper(e.getMessage());
            updateConsoleTextArea();
        }

        Button dropButton = new Button("D");
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
            consoleTextAreaStringBuilderHelper(e.getMessage());
        }
    }

    private void consoleTextFieldCommands() {
        String command = consoleTextField.getText();
        try {
            if (consoleTextField.getText().equalsIgnoreCase("Commands")) {
                consoleTextAreaStringBuilderHelper("-Commands-" + "\nmove to <roomNumber>" + "\nstats: Player Stats" + "\nI: Inventory" + "\npickup <item>: add <item> to inventory" + "\ndrop <item>: Discard <item> to current room" + "\ninspect <item>: Look at <item>" + "\nunequip: Empty players hand" + "\nequip <item>: Place <item> from inventory to players hand" + "\nheal: Use consumable" + "\nexplore: List items in the room" + "\nclear: Clear all text from console.");
            } else if (command.contains("move to")) {
                consoleTextAreaStringBuilderHelper(map.movePlayerTo(command.split(" ")[2]), map.getCurrentRoomItems().toString(), "Connections: " + map.getCurrentRoomValidConnections());
            } else if (command.equalsIgnoreCase("stats")) {
                consoleTextAreaStringBuilderHelper(map.getPlayerStats());
            } else if (command.equalsIgnoreCase("I")) {
                consoleTextAreaStringBuilderHelper(map.getPlayerInventory().toString());
            } else if (command.contains("pickup")) {
                consoleTextAreaStringBuilderHelper(map.pickupPlayerItem(command.split(" ")[1]));
                setUpInventoryGridPane();
            } else if (command.contains("drop")) {
                consoleTextAreaStringBuilderHelper(map.dropPlayerItem(command.split(" ")[1]));
            } else if (command.contains("inspect")) {
                consoleTextAreaStringBuilderHelper(map.inspectItem(command.split(" ")[1]));
            } else if (command.equalsIgnoreCase("unequip")) {
                consoleTextAreaStringBuilderHelper(map.unequipPlayerItem());
            } else if (command.contains("equip")) {
                consoleTextAreaStringBuilderHelper(map.equipPlayerItem(command.split(" ")[1]));
            } else if (command.equalsIgnoreCase("heal")) {
                consoleTextAreaStringBuilderHelper(map.playerHeal());
            } else if (command.equalsIgnoreCase("explore")) {
                consoleTextAreaStringBuilderHelper(map.getCurrentRoomDescription(), map.getCurrentRoomItems().toString(), "Connections:" + map.getCurrentRoomValidConnections());
            } else if (command.contains("unlock")) {
                consoleTextAreaStringBuilderHelper(map.playerUnlocksDoor(command.split(" ")[1]));
            } else if (command.equalsIgnoreCase("clear")) {
                consoleTextAreaStringBuilder.setLength(0);
            } else {
                consoleTextAreaStringBuilderHelper("Please ented a valid command. \nEnter 'Commands' to see a list of valid commands");
            }
        } catch (GameException e) {
            consoleTextAreaStringBuilderHelper(e.getMessage());
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

        consoleTextField.setOnMouseClicked(event -> consoleTextField.setText(""));
    }

}