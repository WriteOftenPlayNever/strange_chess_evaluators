package dev.wopn.realchess;

import java.util.ArrayList;
import java.util.List;

public class MADCollection {

    public List<Move> moveList;
    public List<Move> attackList;
    public List<Move> defendList;

    public MADCollection() {
        moveList = new ArrayList<>();
        attackList = new ArrayList<>();
        defendList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "MADCollection{" +
                "\nmoveList=" + moveList +
                ", \nattackList=" + attackList +
                ", \ndefendList=" + defendList +
                '}';
    }
}
