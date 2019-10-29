import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SubsetsGenerationHelper {

    private void permute(List<int[]> subsets, int n, int[] elements) {
        if(n == 1) {
            int[] subset = elements.clone();
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

    private void swap(int[] input, int a, int b) {
        int tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    public List<int[]> generate(int n, int r) {
        List<int[]> subsets = new ArrayList<>();

        CombinationsGenerationHelper helper = new CombinationsGenerationHelper();
        List<int[]> combinations = helper.generate(n, r);

        for (int[] combination : combinations) {
            permute(subsets, r, combination);
        }

        return subsets;
    }

}
