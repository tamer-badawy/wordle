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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.IOException;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Random;


public class WordList {
    
    private int wordLen;
    private static ArrayList<String> words;
    private ArrayList<String> filteredWords;
    
    
    public WordList(){
        this(6);
    }
    
    public WordList(int wordLen){
        Gson gson  = new Gson();
        try(InputStream is = getClass().getResourceAsStream("/words.json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is))
                ){
            words = gson.fromJson(reader,
                new TypeToken<ArrayList<String>>(){}.getType());            
        }catch(IOException ex){
            ex.printStackTrace();
        }
        this.wordLen = wordLen;
        this.updateFilteredWords();
    }
    
    public void setWordLength(int wordLen){
        this.wordLen = wordLen;
        this.updateFilteredWords();
    }
    
    private void updateFilteredWords(){
        Predicate<String> byLength = (w)-> w.length() == this.wordLen;
        this.filteredWords = words.stream().filter(byLength)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    public String getRandomWord(){
        Random rand = new Random();
        var index = rand.ints(0, this.filteredWords.size()).findFirst().getAsInt();
        return this.filteredWords.get(index);
    }
    
    public boolean isAvailableWord(String word){
        return this.filteredWords.contains(word.toLowerCase());
    }
}
