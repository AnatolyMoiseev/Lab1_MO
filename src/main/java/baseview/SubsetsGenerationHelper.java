package baseview;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SubsetsGenerationHelper {

    private void permute(List<List<Integer>> subsets, int n, List<Integer> elements) {
        if(n == 1) {
            List<Integer> subset = new ArrayList<>(elements);
            subsets.add(subset);
        } else {
            for(int i = 0; i < n - 1; i++) {
                permute(subsets, n - 1, elements);
                if(n % 2 == 0) {
                    swap(elements, i, n - 1);
                } else {
                    swap(elements, 0, n - 1);
                }
            }
            permute(subsets, n - 1, elements);
        }
    }

    private void swap(List<Integer> input, int a, int b) {
        int tmp = input.get(a);
        input.set(a, input.get(b));
        input.set(b, tmp);
    }

    public List<List<Integer>> generate(int n, int r) {
        List<List<Integer>> subsets = new ArrayList<>();

        CombinationsGenerationHelper helper = new CombinationsGenerationHelper();
        List<List<Integer>> combinations = helper.generate(n, r);

        combinations.forEach(combination -> permute(subsets, r, combination));

        return subsets;
    }

}
