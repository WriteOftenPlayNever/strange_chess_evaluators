package dev.wopn.realchess;

public class Pair<A, B> {

    public A first;
    public B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public boolean equals(Pair<A, B> pair) {
        return this.first == pair.first && this.second == pair.second;
    }

    @Override
    public String toString() {
        return "(" + (first == null ? "--" : first.toString()) + "," + (second == null ? "--" : second.toString()) + ")";
    }
}
