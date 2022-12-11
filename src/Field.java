import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Field {
    char[][] cells;
    int masterX = -1, masterY = -1;
    private char[][] cache;


    public Field(char[][] cells) {
        this.cells = cells;
        for (int y = 0; y < cells.length; y++)
            for (int x = 0; x < cells[y].length; x++)
                if (cells[y][x] == 'Ч') {
                    masterX = x;
                    masterY = y;
                    break;
                }
        cache = new char[masterY + 1][masterX + 1];
//        Arrays.stream(cache)
//                .forEach(row
//                        -> Arrays.fill(row, '?'));
    }

    public Field(char[][] cells, int masterX, int masterY) {
        this.cells = cells;
        this.masterX = masterX;
        this.masterY = masterY;
        cache = new char[masterY + 1][masterX + 1];
//        Arrays.stream(cache)
//                .forEach(row
//                        -> Arrays.fill(row, '?'));
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
        System.out.printf("Хозяин находится в координатах %s:%s%n%n", masterX, masterY);
    }

    public char checkCellReachability(int x, int y) {

        if (cache[y][x] != '\u0000') {
            System.out.printf("cache requested: [%d, %d] -> %s%n", x, y, cache[y][x]);
            return cache[y][x];
        }

        char label;

        boolean L = x > 0 && cells[y][x - 1] != '*';
        boolean U = y > 0 && cells[y - 1][x] != '*';

        if (L) {
            label = U ? 'B' : 'L';
        } else {
            label = U ? 'U' : 'N';
        }

        cache[y][x] = label;
        System.out.printf("cache written: [%d, %d] <- %s%n", x, y, cache[y][x]);

        return label;
    }


    public void printAccessibilityMask() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++)
                sb.append(
                    (cells[y][x] == '-' || cells[y][x] == 'Ч') &&
                                        y < masterY &&
                                        x < masterX ?
                        cache[y][x] :
                            cells[y][x]
                ).append(" ");
            sb.append("\n");
        }
        System.out.println(sb);
    }

    public boolean[][] findPath(int x, int y, boolean verbose) {

        if (verbose)
            System.out.printf("searching path for %d:%d\n", x, y);

        boolean[][] path = new boolean[cells.length][cells[0].length];
        boolean[][] downPath = null, rightPath = null;
        char approachability = checkCellReachability(x, y);

        if (approachability == 'U' || approachability == 'B') {
            if (x == 0 && y == 1) {
                path[y][x] = true;
                printPathMask(path);
                return path;
            }
            downPath = findPath(x, y - 1, verbose);
        }

        if (approachability == 'L' || approachability == 'B') {
            if (y == 0 && x == 1) {
                path[y][x] = true;
                printPathMask(path);
                return path;
            }
            rightPath = findPath(x - 1, y, verbose);
        }

        if (rightPath != null || downPath != null) {
            path = rightPath != null ? rightPath : downPath;
            path[y][x] = true;

            if (verbose) {
                System.out.printf("path of %d:%d found from %s%n",
                        x, y, rightPath != null ? "left" : "above");
                printPathMask(path);
            }

            return path;
        }

        if (verbose) {
            System.out.printf("path of %d:%d not found%n", x, y);
            printPathMask(path);
        }
        return null;
    }


    public String solution(boolean verbose) {
        boolean[][] path = findPath(masterX, masterY, verbose);

        if (path == null)
            return "К сожалению, щенок не нашёл пути до хозяина.";

        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++)
                sb.append(
                        x <= masterX && y <= masterY &&
                        (x != masterX || y != masterY) &&
                        path[y][x] ?
                            "х" :
                                cells[y][x]
                         )
                  .append(" ");
            sb.append("\n");
        }
        return sb.toString();
    }

    public String solution() {
        return solution(false);
    }

    public void find_and_print() {
        System.out.println(solution(true));
    }

    public void printPathMask(boolean[][] path) {
        StringBuilder sb = new StringBuilder();

        for (boolean[] row : path) {
            for (boolean cell : row)
                sb.append(cell ? "х" : "-").append(" ");
            sb.append("\n");
        }
        System.out.println(sb);
    }


}
