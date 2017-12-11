package top.sharex.oes.util;

/**
 * @author danielyang
 * @Date 2017/11/16 17:31
 */
public class Pair<K, V> {
    K v1;
    V v2;

    private Pair(K v1, V v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public static <K, V> Pair<K, V> newInstance(K v1, V v2) {
        return new Pair<>(v1, v2);
    }


}
