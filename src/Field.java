import java.util.Arrays;

public class Field {
    char[][] cells;

    public Field(char[][] cells) {
        this.cells = cells;
    }

    public static Field parse(String field) {
        return new Field(Arrays.stream(field.split("\n"))
                .map(String::toCharArray)
                .toArray(char[][]::new));
    }

    public void print() {
        StringBuilder sb = new StringBuilder();
        for (char[] line : cells) {
            for (char c : line) sb.append(c);
            sb.append('\n');
        }
        System.out.print(sb);
    }

    public boolean hasCactus(int x, int y) {
        return cells[x][y] == '*';
    }
}
