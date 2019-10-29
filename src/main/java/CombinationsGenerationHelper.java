import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CombinationsGenerationHelper {

    private void getCombination(List<int[]> combinations, int[] data, int start, int end, int index) {
        if (index == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);
        } else if (start <= end) {
            data[index] = start;
            getCombination(combinations, data, start + 1, end, index + 1);
            getCombination(combinations, data, start + 1, end, index);
        }
    }

    public List<int[]> generate(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        getCombination(combinations, new int[r], 1, n, 0);

        return combinations;
    }

}
