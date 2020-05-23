/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LexicalAnalyzer;

import java.util.ArrayList;

/*Objective: This class was created to generate the table
of the lexical analyzer and to print it on console*/
public class TableGenerator {
    private ArrayList<ArrayList<String>> table;
    
    TableGenerator(){
        table = new ArrayList<ArrayList<String>>();
    }
    public void initializeTable(ArrayList<String> row){
        table.add(row);
    }
    public void printTable(int spacing){
        ArrayList<Integer> maxLengths = findMaxLengths();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < table.get(0).size(); i++) {
            for (int j = 0; j < table.size(); j++) {
                String currentValue = table.get(j).get(i);
                sb.append(currentValue);
                for (int k = 0; k < (maxLengths.get(j) - currentValue.length() + spacing); k++) {
                    sb.append(' ');
                }
            }
            sb.append('\n');
        }
        System.out.println(sb);
    }
    private ArrayList<Integer> findMaxLengths(){
        ArrayList<Integer> maxLengths = new ArrayList<>();
        for (ArrayList<String> row : table) {
            int maxLength = 0;
            for (String value : row) {
                if (value.length() > maxLength) {
                    maxLength = value.length();
                }
            }
            maxLengths.add(maxLength);
        }
        return maxLengths;
    }
   
}
