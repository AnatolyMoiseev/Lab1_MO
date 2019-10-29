import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GaussJordan {

    private static float EPSILON = 0.000000001f;

    private List<List<Float>> extendMatrix;

    boolean isNullRow(List<Float> row) {
        return row.stream().filter(rowElement -> Math.abs(rowElement) <= EPSILON).count() == row.size();
    }

    private List<Float> addPositiveNull(List<Float> row) {
        return row
                .stream()
                .map(element -> element + 0.0f)
                .collect(Collectors.toList());
    }

    public List<List<Float>> getBaseView(int[] subset) {
        List<Float> masterRow = new ArrayList<>();
        List<List<Float>> pizdec = extendMatrix;
        int k = 0;

        for (List<Float> row : pizdec) {
            //for (Float element : row) {
            //    if (Math.abs(element) >= EPSILON && /*element == 1f &&*/ !row.equals(masterRow)) {
            //        masterRow = row.stream().map(rowElement -> rowElement / element).collect(Collectors.toList());
            //        extendMatrix.set(extendMatrix.indexOf(row), masterRow);
            //        break;
            //    }
            //}

            if (!row.equals(masterRow)) {
                int index = subset[k++];
                masterRow = row.stream().map(rowElement -> rowElement / row.get(index)).collect(Collectors.toList());
                pizdec.set(pizdec.indexOf(row), masterRow);
            }

            for (List<Float> slaveRow : pizdec) {
                if (!masterRow.equals(slaveRow)) {
                    int indexOf = masterRow.indexOf(1f);
                    List<Float> zhopa = masterRow
                            .stream()
                            .map(masterRowElement ->
                                    masterRowElement = masterRowElement * -slaveRow.get(indexOf))
                            .collect(Collectors.toList());

                    List<Float> zaebala = slaveRow
                            .stream()
                            .map(slaveRowElement ->
                                    slaveRowElement += zhopa.get(slaveRow.indexOf(slaveRowElement)))
                            .collect(Collectors.toList());

                    pizdec.set(pizdec.indexOf(slaveRow), zaebala);
                }
            }
        }
        pizdec.removeIf(this::isNullRow);

        return pizdec;
    }

    public List<List<List<Float>>> getAllBaseViews() {
        List<List<List<Float>>> result = new ArrayList<>();
        SubsetsGenerationHelper helper = new SubsetsGenerationHelper();
        List<int[]> subsets = helper.generate(extendMatrix.get(0).size(), extendMatrix.size());

        for (int[] subset : subsets) {
            result.add(getBaseView(subset));
        }

        return result;
    }

    private boolean isOneColumn(List<Float> column) {
        return true;
    }

    private boolean isBaseView() {
        return true;
    }

}
