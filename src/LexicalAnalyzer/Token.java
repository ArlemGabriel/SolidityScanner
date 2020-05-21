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
    private String linenumber;
    private String columnnumber;
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLinenumber() {
        return linenumber;
    }

    public void setLinenumber(String linenumber) {
        this.linenumber = linenumber;
    }

    public String getColumnnumber() {
        return columnnumber;
    }

    public void setColumnnumber(String columnnumber) {
        this.columnnumber = columnnumber;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
