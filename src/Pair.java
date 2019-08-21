public class Pair<L,R> {

    private L left;
    private R right;

    public Pair (L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getKey() {
        return left;
    }
    public R getValue() {
        return right;
    }
}
