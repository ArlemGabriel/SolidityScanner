/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler;

import java.util.ArrayList;

/*Objective: This class was created to create tokens
that has the posibility to store all the occurrences
that they have by line along the file analized */

public class TokenOccurrence extends Token{
    private int occurrences;
    private ArrayList<ArrayList<Integer>> totaloccurrences;

    public TokenOccurrence() {
        totaloccurrences = new ArrayList<>();
    }

    public int getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }
    public void insertOccurrence(int line,int occurrence){
        ArrayList<Integer> newoccurrence = new ArrayList<>();
        newoccurrence.add(line);
        newoccurrence.add(occurrence);
        totaloccurrences.add(newoccurrence);
    }
    public ArrayList<ArrayList<Integer>> getTotalOccurrences(){
        return totaloccurrences;
    }
    public ArrayList<Integer> getOccurrence(int position){
        return totaloccurrences.get(position);
    }
    
}
