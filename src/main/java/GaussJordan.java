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

    private List<List<Float>> extendMatrix;

    boolean isNullRow(List<Float> row) {
        return row.stream().filter(rowElement -> rowElement == 0f).count() == row.size();
    }

    public List<List<Float>> getBaseView() {
        List<Float> masterRow = new ArrayList<>();
        List<Float> tmpRow = new ArrayList<>();
        for (List<Float> row : extendMatrix) {
            for (Float element : row) {
                if (Math.abs(element) >= 0.000000001 && !row.equals(masterRow)) {
                    tmpRow = row.stream().map(rowElement -> rowElement / element).collect(Collectors.toList());
                    masterRow = row;
                    break;
                }
            }
            for (List<Float> slaveRow : extendMatrix) {
                if (!row.equals(slaveRow)) {
                    int indexOf = tmpRow.indexOf(1f);
                    tmpRow.stream().map(tmpRowElement -> tmpRowElement * -row.get(indexOf));
                }
            }

        }

        extendMatrix.removeIf(this::isNullRow);

        return extendMatrix;
    }

}
