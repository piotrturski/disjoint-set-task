package net.piotrturski.findunion;

import com.google.common.base.Preconditions;
import org.jgrapht.alg.util.UnionFind;

import java.util.Collections;
import java.util.stream.IntStream;

public class Network {

    private final UnionFind<Integer> unionFind = new UnionFind<>(Collections.emptySet());

    public Network(int size) {
        Preconditions.checkArgument(size > 0, "Positive network size expected");
        IntStream.rangeClosed(1, size).forEach(unionFind::addElement);
    }

    /**
     * you can connect x with x
     */
    public void connect(int x, int y) {
        unionFind.union(x, y);
    }

    public boolean query(int x, int y) {
        Integer xRoot = unionFind.find(x);
        Integer yRoot = unionFind.find(y);
        return xRoot.equals(yRoot);
    }

}
