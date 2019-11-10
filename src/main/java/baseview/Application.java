package baseview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private static void outputResult(List<List<List<String>>> result) {

        result.stream()
                .map(element ->
                        Arrays.deepToString(element.stream().map(row ->
                               Arrays.deepToString(row.toArray())
                                        .replace(",", " ") + "\n").toArray())
                                .replace(",", "") + "\n")
                .forEach(System.out::println);


    }

    private static boolean isNotSupportingPlane(List<List<Float>> govno) {
        return govno.stream().anyMatch(penis -> penis.get(penis.size() - 1) < 0);
    }

    private static List<List<List<Float>>> getAllSupportingPlanes(List<List<List<Float>>> result) {
        result.removeIf(Application::isNotSupportingPlane);
        return result;
    }

    public static void main(String[] args) {
        GaussJordan gaussJordan = new GaussJordan();
        List<List<Float>> extendedMatrix = inputExtendMatrix();

        extendedMatrix.removeIf(gaussJordan::isNullRow);
        gaussJordan.setExtendMatrix(extendedMatrix);

        List<List<List<Float>>> allBaseViews = gaussJordan.getAllBaseViews();



        System.out.println("\nБазисные виды системы:");
        outputResult(allBaseViews.stream().map(element -> element.stream().map(row -> row.stream().map(elementF -> String.format("%.3f", elementF)).collect(Collectors.toList())).collect(Collectors.toList())).collect(Collectors.toList()));

        System.out.println("\nОпорные планы:");
        //outputResult(getAllSupportingPlanes(allBaseViews));
    }

}
