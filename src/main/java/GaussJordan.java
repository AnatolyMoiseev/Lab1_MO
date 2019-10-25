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

    public List<List<Float>> getBaseView() {
        List<Float> masterRow = new ArrayList<>();

        for (List<Float> row : extendMatrix) {
            for (Float element : row) {
                if (Math.abs(element) >= EPSILON && element == 1f && !row.equals(masterRow)) {
                    masterRow = row.stream().map(rowElement -> rowElement / element).collect(Collectors.toList());
                    extendMatrix.set(extendMatrix.indexOf(row), masterRow);
                    break;
                }
            }
            for (List<Float> slaveRow : extendMatrix) {
                if (!masterRow.equals(slaveRow)) {
                    int indexOf = masterRow.indexOf(1f);
                    List<Float> zhopa = masterRow
                            .stream()
                            .map(masterRowElement ->
                                    masterRowElement = masterRowElement * -slaveRow.get(indexOf))
                            .collect(Collectors.toList());
                    List<Float> piska = slaveRow
                            .stream()
                            .map(slaveRowElement ->
                                    slaveRowElement += zhopa.get(slaveRow.indexOf(slaveRowElement)))
                            .collect(Collectors.toList());

                    extendMatrix.set(extendMatrix.indexOf(slaveRow), piska);
                }
            }
        }
        extendMatrix.removeIf(this::isNullRow);

        return extendMatrix;
    }

}
