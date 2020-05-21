/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LexicalAnalyzer;

/**
 *
 * @author Arlem
 */
public class Token {
    private String type;
    private int linenumber;
    private int columnnumber;
    private String value;
    
    Token(){
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLinenumber() {
        return linenumber;
    }

    public void setLinenumber(int linenumber) {
        this.linenumber = linenumber;
    }

    public int getColumnnumber() {
        return columnnumber;
    }

    public void setColumnnumber(int columnnumber) {
        this.columnnumber = columnnumber;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
