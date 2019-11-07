package baseview;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.util.Pair;
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
                Float masterElement = row.get(index);
                if (Math.abs(masterElement) <= EPSILON)
                    return new ArrayList<>();
                masterRow = row.stream().map(rowElement -> rowElement / masterElement).collect(Collectors.toList());
                pizdec.set(pizdec.indexOf(row), masterRow);
            }

            for (List<Float> slaveRow : pizdec) {
                if (!masterRow.equals(slaveRow)) {
                    int indexOf = masterRow.indexOf(1f);
                    List<Float> zhopa = masterRow
                            .stream()
                            .map(masterRowElement -> masterRowElement * -slaveRow.get(indexOf))
                            .collect(Collectors.toList());

                    List<Float> zaebala = slaveRow
                            .stream()
                            .map(slaveRowElement -> slaveRowElement += zhopa.get(slaveRow.indexOf(slaveRowElement)))
                            .collect(Collectors.toList());

                    pizdec.set(pizdec.indexOf(slaveRow), zaebala);
                }
            }
        }
        pizdec.removeIf(this::isNullRow);

        return pizdec;
    }

    public List<List<List<Float>>> getAllBaseViews() {
        SubsetsGenerationHelper helper = new SubsetsGenerationHelper();
        List<List<Integer>> subsets = helper.generate(extendMatrix.get(0).size() - 1, extendMatrix.size());

        List<Pair<Integer, Integer>> zeros = getMatrixZeros();

        for (Pair<Integer, Integer> zero : zeros) {
            subsets.removeIf(subset -> isIndexOfZero(subset, zero));
        }

        return subsets.stream().map(this::getBaseView).collect(Collectors.toList());
    }

    private boolean isIndexOfZero(List<Integer> subset, Pair<Integer, Integer> zero) {
        for (int i = 0; i < subset.size(); i++) {
            if (subset.get(i).equals(zero.getValue()) && i == zero.getKey()) {
                return true;
            }
        }
        return false;
    }

    private List<Pair<Integer, Integer>> getMatrixZeros() {
        List<Pair<Integer, Integer>> zeros = new ArrayList<>();

        for (int i = 0; i < extendMatrix.size(); i++) {
            List<Float> el = extendMatrix.get(i);
            for (int i1 = 0; i1 < el.size(); i1++) {
                if (Math.abs(el.get(i1)) <= EPSILON) {
                    Pair<Integer, Integer> pair = new Pair<>(i, i1);
                    zeros.add(pair);
                }
            }
        }

        return zeros;
    }

    private boolean isOneColumn(List<Float> column) {
        return true;
    }

    private boolean isBaseView() {
        return true;
    }

}
