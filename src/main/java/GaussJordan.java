import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

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
        List<Float> tmpRow = new ArrayList<>();
        List<List<Float>> resultMatrix = new ArrayList<>();

        for (List<Float> row : extendMatrix) {
            for (Float element : row) {
                if (Math.abs(element) >= EPSILON && !row.equals(masterRow)) {
                    tmpRow = row.stream().map(rowElement -> rowElement / element).collect(Collectors.toList());
                    masterRow = row;
                    break;
                }
            }
            for (List<Float> slaveRow : extendMatrix) {
                if (!row.equals(slaveRow)) {
                    int indexOf = tmpRow.indexOf(1f);
                    tmpRow.forEach(tmpRowElement -> tmpRowElement = tmpRowElement * -row.get(indexOf));
                    List<Float> finalTmpRow = tmpRow;
                    slaveRow.forEach(slaveRowElement -> slaveRowElement += finalTmpRow.get(slaveRow.indexOf(slaveRowElement)));
                }
                resultMatrix.add(slaveRow);
            }
        }
        resultMatrix.removeIf(this::isNullRow);

        return resultMatrix;
    }

}
