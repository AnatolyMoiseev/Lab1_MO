package baseview;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<List<Integer>> generate(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        getCombination(combinations, new int[r], 0, n - 1, 0);

        return toList(combinations);
    }

    private List<List<Integer>> toList(List<int[]> combinations) {
        return combinations.stream()
                .map(value ->
                        Arrays.stream(value).boxed().collect(Collectors.toList())).collect(Collectors.toList());
    }

}
