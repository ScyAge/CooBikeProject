package listchooser;

import java.util.List;
import java.util.Random;

/**
 * The class RandomListChooser
 * @param <T> - The type of list
 */
public class RandomListChooser<T> implements ListChooser<T> {
    private static final Random RANDOM = new Random();

    @Override
    public T choose(List<? extends T> list) {
        if (list.isEmpty()) {
            return null;
        }
        return list.get(RANDOM.nextInt(list.size()));
    }
}
