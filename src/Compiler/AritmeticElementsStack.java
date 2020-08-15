/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package precedencia;

import java.util.ArrayList;

/**
 *
 * @author Arlem Gabriel
 */
public class AritmeticElementsStack
{
  /** Member variables **/
  private ArrayList<AritmeticElement> tokens;

  /** Constructors **/
  public AritmeticElementsStack() {
    tokens = new ArrayList<AritmeticElement>();
  }

  /** Accessor methods **/
  public boolean isEmpty() {
    return tokens.size() == 0;
  }
  public AritmeticElement top() {
    return tokens.get(tokens.size()-1);
  }

  /** Mutator methods **/
  public void push(AritmeticElement t) {
    tokens.add(t);
  }
  public void pop() {
    tokens.remove(tokens.size()-1);
  }
}
