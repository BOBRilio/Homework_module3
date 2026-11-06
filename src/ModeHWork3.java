import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ModeHWork3 {
    static private <T> Map<T, Long> frequency(T[] items){
        return Arrays.stream(items).collect(Collectors.groupingBy(
                n -> n,
                Collectors.counting()
        ));
    }
    static void main () {
        String[] words = {"up", "down", "up", "up", "left"};
        Integer[] numbers = {3, 1, 3, 3, 7, 1};
        System.out.println(ModeHWork3.frequency(words));
        System.out.println(ModeHWork3.frequency(numbers));
    }

}
