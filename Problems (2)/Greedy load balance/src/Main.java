import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        List<Integer> first = new ArrayList<>(count);
        first.add(0);
        List<Integer> second = new ArrayList<>(count);
        second.add(0);
        for (int i = 0; i < count; i++) {
            int id = scanner.nextInt();
            int weight = scanner.nextInt();
            if (first.get(0) <= second.get(0)) {
                first.add(id);
                first.set(0, first.get(0) + weight);
            } else {
                second.add(id);
                second.set(0, second.get(0) + weight);
            }
        }
        System.out.println(first.stream().skip(1).map(String::valueOf).collect(Collectors.joining(" ")));
        System.out.println(second.stream().skip(1).map(String::valueOf).collect(Collectors.joining(" ")));
    }
}