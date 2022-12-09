import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Field {
    char[][] cells;
    int masterX = -1, masterY = -1;

    public Field(char[][] cells) {
        this.cells = cells;
    }

    public Field(char[][] cells, int masterX, int masterY) {
        this.cells = cells;
        this.masterX = masterX;
        this.masterY = masterY;
    }

    public static Field parse(String field) {
        int x = -1;
        int y = -1;
        String[] lines = field.split("\n");
        Arrays.setAll(lines,
                i -> lines[i].replaceAll("\\s+", ""));

        if (lines.length < 1 || lines[0].isBlank())
            throw new IllegalArgumentException("Поле не найдено!");


        if (!lines[0].startsWith("Щ"))
            throw new IllegalArgumentException("В начале этого поля нет щенка!");

        List<char[]> rows = new ArrayList<>(lines.length);
        int width = lines[0].length();

        for (int i = 0; i < lines.length; i++) {
            String s = lines[i];

            if (s.length() != width)
                throw new IllegalArgumentException("Поле не прямоугольное!");

            if (s.indexOf('Ч') > -1) {
                if (x != -1)
                    throw new IllegalArgumentException("На этом поле более одного хозяина!");
                x = s.indexOf('Ч');
                y = i;
            }
            rows.add(s.toCharArray());
        }

        if (x == -1)
            throw new IllegalArgumentException("На этом поле нет хозяина!");

        return new Field(rows.toArray(new char[0][]), x, y);
    }

    public void print() {
        System.out.println("Кактусовое поле, на котором щенок ищет хозяина:");
        StringBuilder sb = new StringBuilder();
        for (char[] line : cells) {
            for (char c : line) sb.append(c).append(" ");
            sb.append('\n');
        }
        System.out.print(sb);
        System.out.printf("Хозяин находится в координатах %s:%s%n", masterX, masterY);
    }

    public boolean isReachableFromLeft(int x, int y) {
        return x > 0 && cells[y][x - 1] != '*';
    }
    public boolean isReachableFromAbove(int x,  int y) {
        return y > 0 && cells[y - 1][x] != '*';
    }

    public char checkCellReachability(int x, int y) {
        return isReachableFromLeft(x, y) ?
                'L' :
                 isReachableFromAbove(x, y) ?
                    'U' :
                         'N';
    }

    public void printAccessibilityMask() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++)
                sb.append(
                    cells[i][j] == '-' || cells[i][j] == 'Ч' ?
                        checkCellReachability(j, i) :
                            cells[i][j]
                ).append(" ");
            sb.append("\n");
        }
        System.out.println(sb);
    }

}
