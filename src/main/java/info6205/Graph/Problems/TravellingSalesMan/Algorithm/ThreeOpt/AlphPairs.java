package info6205.Graph.Problems.TravellingSalesMan.Algorithm.ThreeOpt;

public enum AlphPairs {
    pairOne(1, 2), pairTwo(2, 3), pairThree(1, 3);

    AlphPairs(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int x;
    int y;
}
