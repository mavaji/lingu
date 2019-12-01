package tfarsdat.service;

/**
 * @author Vahid Mavaji
 */
public class Distance {


    // allocates a 2D array of integers
    private static int[][] create_matrix(int Row, int Col) {
        int[][] array = new int[Row][Col];
        return array;
    }

    public static String diff(int[][] matrix, int m, int n, String x, String y) {
        if (m == 0 && n == 0)
            return "";
        if (m == 0) {
            return diff(matrix, m, n - 1, x, y) + "#i:" + y.charAt(n - 1) + ",p:" + x.charAt(m - 2) + ",n:" + x.charAt(m);
        } else if (n == 0) {
            return diff(matrix, m - 1, n, x, y) + "#d:" + x.charAt(m - 1) + ",p:" + x.charAt(m - 2) + ",n:" + x.charAt(m);
        } else if (x.charAt(m - 1) == y.charAt(n - 1)) {
            return diff(matrix, m - 1, n - 1, x, y) + "#s";
        } else {
            int t = matrix[m][n] - 1;
            if (t == matrix[m - 1][n]) {
                return diff(matrix, m - 1, n, x, y) + "#d:" + x.charAt(m - 1) + ",p:" + x.charAt(m - 2) + ",n:" + x.charAt(m);
            } else if (t == matrix[m][n - 1]) {
                return diff(matrix, m, n - 1, x, y) + "#i:" + y.charAt(n - 1) + ",p:" + x.charAt(m - 2) + ",n:" + x.charAt(m);
            } else if (t == matrix[m - 1][n - 1]) {
                return diff(matrix, m - 1, n - 1, x, y) + "#r:" + x.charAt(m - 1) + "->" + y.charAt(n - 1) + ",p:" + x.charAt(m - 2) + ",n:" + x.charAt(m);
            } else
                return "error";
        }
    }

    // computes the Levenshtein distance between two strings
// "x" represent the pattern and "y" represent the text
// "m" is the pattern length and "n" is the text length
    public static String LD(String x, int m, String y, int n) {
        // if the length of the second string is zero
        // then the distance between the two strings will
        // be equal to the length of the first string
        // and vis-versa
        // if the length of both strings is equal to zero
        // then the distance between this two strings will
        // simply be zero
        if (n == 0) {
            return diff(new int[][]{{0}}, m, n, x, y);
        } else if (m == 0) {
            return diff(new int[][]{{0}}, m, n, x, y);
        }

        // creating a matrix of m+1 rows and n+1 columns
        int[][] matrix = create_matrix(m + 1, n + 1);

        // initialising the first row of the matrix
        for (int i = 0; i <= n; ++i) {
            matrix[0][i] = i;
        }

        // initialising the first column of the matrix
        for (int i = 0; i <= m; ++i) {
            matrix[i][0] = i;
        }

        // complementary variables for computing the "Levenshtein distance"
        int above_cell, left_cell, diagonal_cell, cost;

        // starting the main process for computing
        // the distance between the two strings "x" and "y"
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                // if the current two characters
                // of both strings are the same
                // then, the corresponding cost value
                // will be zero,otherwise it will be 1
                if (x.charAt(i - 1) == y.charAt(j - 1)) {
                    cost = 0;
                } else {
                    cost = 1;
                }
                // current cell of the matrix: matrix[i][j]

                // finds the above cell to the current cell
                above_cell = matrix[i - 1][j];

                // finds the left cell to the current cell
                left_cell = matrix[i][j - 1];

                // finds the diagonally above cell to the current cell
                diagonal_cell = matrix[i - 1][j - 1];

                // computes the current value of the "edit distance" and place
                // the result into the current matrix cell
                matrix[i][j] = Math.min(Math.min(above_cell + 1, left_cell + 1), diagonal_cell + cost);
            }
        }
        // placing the final result into a variable
        int result = matrix[m][n];
        String differ = diff(matrix, m, n, x, y);

        // freeing memory that has been used
        // for the "matrix variable"

        // returning result of the search
        return differ;
    }
}
