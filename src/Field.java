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
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++)
                sb.append(
                    cells[y][x] == '-' || cells[y][x] == 'Ч' ?
                        checkCellReachability(x, y) :
                            cells[y][x]
                ).append(" ");
            sb.append("\n");
        }
        System.out.println(sb);
    }

    public boolean[][] findPath(int x, int y) {
        System.out.printf("searching path for %d:%d\n", x, y);
        boolean[][] path = new boolean[cells.length][cells[0].length];
        boolean[][] downPath = null, rightPath = null;

        if (isReachableFromAbove(x, y)) {
            if (x == 0 && y == 1) {
                path[y][x] = true;
                return path;
            }
            downPath = findPath(x, y - 1);
        }

        if (isReachableFromLeft(x, y)) {
            if (y == 0 && x == 1) {
                path[y][x] = true;
                return path;
            }
            rightPath = findPath(x - 1, y);
        }

        if (rightPath != null || downPath != null) {
            path = rightPath != null ? rightPath : downPath;
            path[y][x] = true;
            System.out.printf("path of %d:%d found from %s%n", x, y, rightPath != null ? "left" : "above");
            return path;
        }
        System.out.printf("path of %d:%d not found%n", x, y);
        return null;
    }


    public void printPath(boolean[][] path) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++)
                sb.append(i < masterX &&
                          j < masterY &&
                          path[i][j] ?
                            "х" :
                                cells[i][j]
                         )
                  .append(" ");
            sb.append("\n");
        }

        System.out.println(sb);
    }

    public void find_and_print() {
        printPath(findPath(masterX, masterY));
    }


}
