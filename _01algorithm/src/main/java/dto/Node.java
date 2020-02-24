package dto;

import lombok.ToString;

/**
 * Couple Parents
 */

@ToString
public class Node{
    private Integer[] intNodeArr = new Integer[2];
    public Node() {
    }
    public Integer getIndex (int index){
        return this.intNodeArr[index];
    }
    public void setIndex(int index, int num){
        this.intNodeArr[index] = num;
    }


}
