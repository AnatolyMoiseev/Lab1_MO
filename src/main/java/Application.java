import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Application {

    private static List<List<Float>> inputExtendMatrix() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите количество строк расширенной матрицы: ");
        int sizeM = scanner.nextInt();

        System.out.print("Введите количество столбцов расширенной матрицы: ");
        int sizeN = scanner.nextInt();

        List<List<Float>> extendedMatrix = new ArrayList<>();

        System.out.println("Введите расширенную матрицу:");
        for (int i = 0; i < sizeM; i++) {
            List<Float> matrixRow = new ArrayList<>();
            for (int j = 0; j < sizeN; j++) {
                matrixRow.add(scanner.nextFloat());
            }
            extendedMatrix.add(matrixRow);
        }

        return extendedMatrix;
    }

    private static void outputResult(List<List<Float>> extendedMatrix) {
        extendedMatrix
                .stream()
                .map(element -> Arrays.deepToString(element.toArray()))
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        GaussJordan gaussJordan = new GaussJordan();
        List<List<Float>> extendedMatrix = inputExtendMatrix();

        extendedMatrix.removeIf(gaussJordan::isNullRow);
        gaussJordan.setExtendMatrix(extendedMatrix);

        outputResult(gaussJordan.getBaseView());


        //CombinationsGenerationHelper helper = new CombinationsGenerationHelper();
        //List<int[]> combinations = helper.generate(5,2);
        //
        //for (int[] combination : combinations) {
        //    System.out.println(Arrays.toString(combination));
        //}
    }

}
