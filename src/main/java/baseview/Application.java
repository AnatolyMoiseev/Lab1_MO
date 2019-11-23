package baseview;

import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    private static List<Float> inputTargetFunction(Integer sizeRow) {
        Scanner scanner = new Scanner(System.in);
        List<Float> result;

        do {
            System.out.print("Введите коэфициенты целевой функции: ");
            String targetFunction = scanner.nextLine();
            Pattern pattern = Pattern.compile(" ");
            result = pattern.splitAsStream(targetFunction).map(Float::valueOf).collect(Collectors.toList());

            if (result.size() > sizeRow) {
                System.out.println("Количество коэфициентов не может быть больше количества неизвестных! ");
            }
        } while (result.size() > sizeRow);

        return result;
    }

    private static void outputResult(List<List<List<Float>>> result) {
        result.stream()
                .map(element ->
                        Arrays.deepToString(element.stream().map(row ->
                               Arrays.deepToString(row.toArray())
                                        .replace(",", " ") + "\n").toArray())
                                .replace(",", "") + "\n")
                .forEach(System.out::println);
    }

    private static void outputSup(List<List<Float>> result) {
        System.out.println(Arrays.deepToString(result.stream().map(row ->
                Arrays.deepToString(row.toArray())
                        .replace(",", " ") + "\n").toArray()).replace(",", ""));
    }

    private static boolean isNotSupportingPlan(List<List<Float>> govno) {
        return govno.stream().anyMatch(penis -> penis.get(penis.size() - 1) < 0);
    }

    private static List<List<List<Float>>> getAllSupportingPlans(List<List<List<Float>>> result) {
        result.removeIf(Application::isNotSupportingPlan);
        return result;
    }

    private static List<Float> getBaseSolution(List<List<Float>> supportingPlan) {
        List<Float> result = new ArrayList<>();

        for (List<Float> row : supportingPlan) {
            for (int j = 0; j < row.size() - 1; j++) {
                Float element = row.get(j);
                if (element - 1f <= GaussJordan.EPSILON) {
                    result.add(row.indexOf(element), row.get(row.size() - 1));
                } else {
                    result.add(0f);
                }
            }
        }

        return result;
    }

    private static List<List<Float>> getOptimalPlan(List<List<List<Float>>> allSupportingPlans, List<Float> targetFunction) {
        Float max = 0f;
        Float a = 0f;
        int maxI = 0;

        for (List<List<Float>> supportingPlan : allSupportingPlans) {
            List<Float> baseSolution = getBaseSolution(supportingPlan);
            for (Float koef : targetFunction) {
                if (koef > GaussJordan.EPSILON) {
                    a += koef * baseSolution.get(1);
                }
            }
            if (a > max) {
                max = a;
                maxI = allSupportingPlans.indexOf(supportingPlan);
            }
        }

        return allSupportingPlans.get(maxI);
    }

    public static void main(String[] args) {
        GaussJordan gaussJordan = new GaussJordan();
        List<List<Float>> extendedMatrix = inputExtendMatrix();

        extendedMatrix.removeIf(gaussJordan::isNullRow);
        gaussJordan.setExtendMatrix(extendedMatrix);

        List<List<List<Float>>> allBaseViews = gaussJordan.getAllBaseViews();

        System.out.println("\nБазисные виды системы:");
        outputResult(allBaseViews.stream().map(element ->
                element.stream().map(row -> row.stream().map(elementF ->
                        Precision.round(elementF, 3)).collect(Collectors.toList())).collect(Collectors.toList())).collect(Collectors.toList()));

        System.out.println("\nОпорные планы:");
        List<List<List<Float>>> allSupportingPlans = getAllSupportingPlans(allBaseViews);
        outputResult(allSupportingPlans.stream().map(element ->
                element.stream().map(row -> row.stream().map(elementF ->
                        Precision.round(elementF, 3)).collect(Collectors.toList())).collect(Collectors.toList())).collect(Collectors.toList()));

        List<Float> targetFunction = inputTargetFunction(extendedMatrix.get(0).size() - 1);

        System.out.println("\nОптимальный план:");
        List<List<Float>> optimalPlan = getOptimalPlan(allSupportingPlans, targetFunction);
        outputSup(optimalPlan.stream().map(row -> row.stream().map(elementF ->
                        Precision.round(elementF, 3)).collect(Collectors.toList())).collect(Collectors.toList()));

    }

}
