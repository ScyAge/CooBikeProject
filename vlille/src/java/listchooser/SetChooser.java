package listchooser;


import java.util.Set;

/**
 * The interface setChooser
 * @param <T> - type of the set
 */
public interface SetChooser<T> {

    /**
     * Choose the element of the set
     * @param set - the set
     * @return a element
     */
    T choose(Set<? extends T> set);
}
