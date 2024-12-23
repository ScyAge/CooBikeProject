package listchooser;

import java.util.List;

/**
 * The list chosser
 * @param <T> - the type of list
 */
public interface ListChooser<T> {

    /**
     * Choose the element of the list
     * @param list - List
     * @return the element
     */
    T choose(List<? extends T> list);
}
