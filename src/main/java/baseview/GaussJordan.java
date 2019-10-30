package baseview;

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

    private static final float EPSILON = 0.000000001f;

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

    private List<List<Float>> getBaseView(List<Integer> subset) {
        List<List<Float>> pizdec = new ArrayList<>(extendMatrix);
        List<Float> masterRow = new ArrayList<>();
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
                int index = subset.get(k++);
                masterRow = row.stream().map(rowElement -> rowElement / row.get(index)).collect(Collectors.toList());
                pizdec.set(pizdec.indexOf(row), masterRow);
            }

            for (List<Float> slaveRow : pizdec) {
                if (!masterRow.equals(slaveRow)) {
                    int indexOf = masterRow.indexOf(1f);
                    try {
                        List<Float> zhopa = masterRow
                                .stream()
                                .map(masterRowElement -> masterRowElement * -slaveRow.get(indexOf))
                                .collect(Collectors.toList());

                        List<Float> zaebala = slaveRow
                                .stream()
                                .map(slaveRowElement -> slaveRowElement += zhopa.get(slaveRow.indexOf(slaveRowElement)))
                                .collect(Collectors.toList());

                        pizdec.set(pizdec.indexOf(slaveRow), zaebala);
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        System.out.println(indexOf);
                        System.out.println(masterRow.toString());
                    }
                }
            }
        }
        pizdec.removeIf(this::isNullRow);

        return pizdec;
    }

    public List<List<List<Float>>> getAllBaseViews() {
        SubsetsGenerationHelper helper = new SubsetsGenerationHelper();
        List<List<Integer>> subsets = helper.generate(extendMatrix.get(0).size() - 1, extendMatrix.size());

        List<Integer> zeros = getMatrixZeros();

        subsets.removeIf(this::isIndexOfZero);

        return subsets.stream().map(this::getBaseView).collect(Collectors.toList());
    }

    private boolean isIndexOfZero(List<Integer> row) {
        return getMatrixZeros().stream().anyMatch(row::contains);
    }

    private List<Integer> getMatrixZeros() {
        return extendMatrix.stream().map(floats -> floats.indexOf(0f)).collect(Collectors.toList());
    }

    private boolean isOneColumn(List<Float> column) {
        return true;
    }

    private boolean isBaseView() {
        return true;
    }

}
