/*
 * Copyright (C) 2022 tamer <tamerbadawy499@hotmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.tamer.wordle;

/**
 *
 * @author tamer <tamerbadawy499@hotmail.com>
 * 
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.animation.ScaleTransition;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import javafx.util.Duration;
import java.util.Arrays;
import javafx.animation.PauseTransition;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;


/**
 * Wordle Game App
 */
public class Wordle extends Application {
    
    private final String[] ALLOWED_CHARS = {"A","B","C","D",
        "E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T",
        "U","V","W","X","Y","Z"};
    private final ScaleTransition anime = new ScaleTransition(Duration.millis(150));
    
    private byte curLetterRow = 0;
    private byte curLetterCol = 0;
    private final StringProperty[][] chars = new SimpleStringProperty[6][6];
    private final WordList wordList = new WordList();
    private String curWord;
    private final StringProperty gameStatus = new SimpleStringProperty();
    private boolean isFinished = false;
    
    private void animate(Rectangle rect){
       anime.setNode(rect);
       anime.setByX(0.2f);
       anime.setByY(0.2f);
       anime.setCycleCount(2);
       anime.setAutoReverse(true);
       anime.play();
    }
    
    private void newGame(Rectangle[][] rect){
        curWord = wordList.getRandomWord();
        for(int row = 0; row < 6; row++ ){
            for(int col = 0 ; col < 6; col++){
                chars[row][col].setValue("");
                rect[row][col].setStroke(Color.TRANSPARENT);
            }
        }
        curLetterCol = curLetterRow = 0;
        isFinished = false;
    }
    
    private boolean isWin(){
        String word = "";
        for(var i = 0; i < 6; i ++){
            word = word.concat(this.chars[curLetterRow][i].getValue());
        }
        return curWord.equalsIgnoreCase(word);
    }
    
    private boolean isKnownWord(){
        String word = "";
        for(var i = 0; i < 6; i ++){
            word = word.concat(chars[curLetterRow][i].getValue());
        }
        return wordList.isAvailableWord(word);
    }

    @Override
    public void start(Stage stage) {
        VBox vbox = new VBox(10);
        VBox winBox = new VBox(10);
        Text txtInfo = new Text();
        Text txtWin = new Text();
        Button btnNewGame = new Button("Play Again!");
        Button btnGiveUp = new Button("Give Up!");
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(4);
        gridPane.setVgap(4);
        gridPane.setAlignment(Pos.CENTER);
        Rectangle[][] rect = new Rectangle[6][6];
        Text[][] letters = new Text[6][6];
        DropShadow dropShadow = new DropShadow();
        Hyperlink gitLink = new Hyperlink("https://github.com/tamer-badawy/wordle");
        
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.web("#a4aec4"));
        
        txtInfo.textProperty().bind(gameStatus);
        txtInfo.setFont(Font.font("Roboto",FontWeight.SEMI_BOLD,40));
        txtInfo.setVisible(false);
        txtInfo.setManaged(false);
        for(int row = 0; row < 6; row++ ){
            for(int col = 0 ; col < 6; col++){
                rect[row][col] = new Rectangle(70, 70, Color.web("#a4aec4"));
                rect[row][col].setArcHeight(15);
                rect[row][col].setArcWidth(15);
                rect[ row][col].setStrokeWidth(3);
                letters[row][col] = new Text();
                chars[row][col] = new SimpleStringProperty("");
                letters[row][col].textProperty().bind(chars[row][col]);
                letters[row][col].setFill(Color.WHITESMOKE);
                letters[row][col].setFont(Font.font("Roboto",FontWeight.SEMI_BOLD,30));
                var stack = new StackPane();
                stack.getChildren().addAll(rect[row][col],letters[row][col]);
                gridPane.add(stack, col, row);
            }
        }
        
        
        winBox.setAlignment(Pos.CENTER);
        winBox.setMaxSize(300, 200);
        winBox.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE,null,null)));
        txtInfo.setFont(Font.font("Roboto",FontWeight.SEMI_BOLD,40));
        txtWin.setFill(Color.web("#a4aec4"));
        txtWin.setFont(Font.font("Roboto",FontWeight.SEMI_BOLD,40));
        txtWin.setText("You Win!");
        txtWin.setEffect(dropShadow);
        btnNewGame.setBackground(new Background(new BackgroundFill(Color.web("#a4aec4"),null,null)));
        btnNewGame.setTextFill(Color.WHITESMOKE);
        btnNewGame.setFont(Font.font("Roboto",FontWeight.SEMI_BOLD,25));
        
        btnNewGame.setEffect(dropShadow);
        btnNewGame.setOnAction(eh -> {
            btnNewGame.setEffect(null);
            PauseTransition pause = new PauseTransition(Duration.seconds(0.2));
                pause.setOnFinished(e -> btnNewGame.setEffect(dropShadow));
            pause.play();
            newGame(rect);
            winBox.setVisible(false);
            winBox.setManaged(false);
            txtInfo.setManaged(false);
            txtInfo.setVisible(false);
        });
       
        btnGiveUp.setFocusTraversable(false);
        btnGiveUp.setBackground(new Background(new BackgroundFill(Color.web("#a4aec4"),null,null)));
        btnGiveUp.setTextFill(Color.WHITESMOKE);
        btnGiveUp.setFont(Font.font("Roboto",FontWeight.SEMI_BOLD,25));
        
        btnGiveUp.setEffect(dropShadow);
        btnGiveUp.setOnMouseClicked(eh -> {
            btnGiveUp.setEffect(null);
            PauseTransition pause = new PauseTransition(Duration.seconds(0.2));
                pause.setOnFinished(e -> btnGiveUp.setEffect(dropShadow));
            pause.play();
            isFinished = true;
            txtInfo.setManaged(true);
            txtInfo.setFill(Color.RED);
            txtInfo.setVisible(true);
            gameStatus.set(curWord.toUpperCase());
            txtWin.setText("You Lost!");
            winBox.setVisible(true);
            winBox.setManaged(true);
        });
        winBox.getChildren().addAll(txtWin,btnNewGame);
        winBox.setVisible(false);
        winBox.setManaged(false);
        gitLink.setFocusTraversable(false);
        gitLink.setOnMouseClicked(eh -> {
            getHostServices().showDocument("https://github.com/tamer-badawy/wordle");
        });
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(txtInfo, new StackPane(gridPane,winBox),btnGiveUp, gitLink);
        
        var scene = new Scene(vbox, 800, 700,Color.WHITESMOKE);
        scene.setOnKeyPressed((KeyEvent eh) -> {
            
            if(!isFinished){
            if((eh.getCode() == KeyCode.BACK_SPACE) && curLetterCol > 0){
                curLetterCol--;
                chars[curLetterRow][curLetterCol].set("");
                gameStatus.set("");
                txtInfo.setVisible(false);
                txtInfo.setManaged(false);
                
            }else if(curLetterRow < 6 && curLetterCol == 6){
                if(eh.getCode() == KeyCode.ENTER){
                    if(!isKnownWord()){
                        txtInfo.setManaged(true);
                        txtInfo.setFill(Color.RED);
                        txtInfo.setVisible(true);
                        gameStatus.set("This is unknown word !");
                    }else if(isWin()){
                        isFinished = true;
                        txtInfo.setManaged(true);
                        txtInfo.setFill(Color.GREEN);
                        txtInfo.setVisible(true);
                        gameStatus.set(curWord.toUpperCase());
                        txtWin.setText("You Win!");
                        winBox.setVisible(true);
                        winBox.setManaged(true);
                    }else if(curLetterRow == 5 && curLetterCol == 6){
                        isFinished = true;
                        txtInfo.setManaged(true);
                        txtInfo.setFill(Color.RED);
                        txtInfo.setVisible(true);
                        gameStatus.set(curWord.toUpperCase());
                        txtWin.setText("You Lost!");
                        winBox.setVisible(true);
                        winBox.setManaged(true);
                    }else{
                        for(var i = 0 ; i < 6 ; i++){
                            var index = curWord.indexOf(chars[curLetterRow][i].get().toLowerCase());
                            if(curWord.contains(chars[curLetterRow][i].get().toLowerCase())){
                                if(curWord.substring(i, i+1).equalsIgnoreCase(chars[curLetterRow][i].get()) ){
                                    rect[curLetterRow][i].setStroke(Color.GREEN);
                                }else{
                                    rect[curLetterRow][i].setStroke(Color.BLUE);
                                }
                            }
                        }
                        curLetterRow++;
                        curLetterCol = 0;
                    }
                }
            }else if(curLetterCol < 6 && eh.getCode().isLetterKey()){
                var ch = eh.getText().toUpperCase();
                if(Arrays.stream(ALLOWED_CHARS).anyMatch(ch::equals)){
                    animate(rect[curLetterRow][curLetterCol]);
                    chars[curLetterRow][curLetterCol].set(ch);
                    curLetterCol++;
                
                }
            }
            }
        });
        stage.getIcons().add(new Image("file:icon.png"));
        stage.setTitle("Wordle Game");
       
        stage.setScene(scene);
        stage.show();
        newGame(rect);
    }

    public static void main(String[] args) {
        Wordle.launch();
    }

}