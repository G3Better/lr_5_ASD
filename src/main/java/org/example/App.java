package org.example;

public class App {
    /**
     * Инициализируем переменные
     * */
    static int N = 7;
    /**
     * Количество городов
     * */
    static int k = 0;
    /**
     * Промежуточная перемменная k
     * */
    static int T = 0;
    /**
     * Промежуточная переменная для хранения общего растояния
     * */
    static byte[][] Way = new byte[N][N];
    /**
     * Матрица для определения пути
     * */

    /**
     * Основная функция (main)
     * */
    public static void main(String[] args)
    {
        /**
         * Матрица смежности графа
         * */
        int[][] matr = {
                {0,25,5,24,5,3,16},
                {25,0,4,19,27,5,4},
                {5,4,0,3,4,5,18},
                {24,19,3,0,23,4,5},
                {5,27,4,23,0,13,5},
                {3,5,5,4,13,0,2},
                {16,4,18,5,5,2,0}
        };
        /**
         * Задаём матрицу флагов, где каждому флагу будет определённо своё значение
         * 0-число, 1-прочерк, 2-вычеркнутая строка или столбец
         * */
        byte[][] Mflag = new byte[N][N];
        /**
         * Выставляем прочерки на основной диагонали (пересечения город и город)
         * */
        for (int i = 0; i < N; i++)
            Mflag[i][i] = 1;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
                if (i == j) {
                    System.out.print(" -");
                } else {
                    System.out.print(" " + matr[i][j]);
                }
            System.out.println();
        }
        /**
         * Вызываем основной метод, в котором будут проходить необходимые вычесления и подсчёт результата
         * */
        kjor((int[][])matr.clone(), (byte[][])Mflag.clone());
        /**
         * Выводим полученный путь и его длину
         * */
        System.out.println("Весь путь (список вершин пройдённых при проходе графа в глубину): ");
        int l=0;
        for (int i = 0; i < N; i++)
        {
            int j;
            for(j=0;j<N;j++)
                if (Way[l][j] == 1)
                {
                    System.out.print(" "+(j+1));
                    l = j;
                    break;
                }
        }
        System.out.println();
        System.out.println("Общая длина пути: " + T);
    }
    /**
     * Основная функция, в которой будет производиться расчёт*/
    static void kjor(int[][] matr, byte[][] Mflag)
    {
        /**
         * Начинаем подсчёт добавляем k единицу и вызываем функцию Change,
         * в которой будут проходить изменения для поиска пути
         * */
        k++;
        Change(matr, Mflag);
        int[] zero = Zero(matr, Mflag);
        /**
         * Отмечаем координаты начало пути, заьем выбираем ребро для удаления
         * */
        Way[zero[0]][zero[1]] = 1;
        if (Mflag[zero[1]][zero[0]] != 2)
            Mflag[zero[1]][zero[0]] = 1;
        System.out.println("Выбрано ребро: "+ (zero[0]+1) +
                (zero[1]+1));
        for (int i = 0; i < N; i++)
        {
            Mflag[zero[0]][i] = 2;
            Mflag[i][zero[1]] = 2;
        }
        /**
         * Промежуточный вывод приведенной матрицы
         */
        for (int i = 0; i < N; i++)
        {
            boolean flag = false;
            for(int r=0;r<N;r++)
                if (Mflag[i][r] != 2)
                {
                    flag = true;
                    break;
                }
            if (flag)
            {
                for (int j = 0; j < N; j++)
                {
                    if (Mflag[i][j] == 1) System.out.print(" -");
                    if (Mflag[i][j] == 0) System.out.print(" " + matr[i][j]);
                }
                System.out.println();
            }
        }
        System.out.println();
        /**
         * Продолжаем рекурсивно вызывать функцию kjor до тех пор, пока не посетим все "города"
         * */
        if (k < N ) kjor((int[][])matr.clone(),
                (byte[][])Mflag.clone());
    }
    /**
     * Change функция, в которой будет происходить проведение матрицы по строкам и столбцам, а также подсчёт пути
     * */
    static void Change(int[][] matr, byte[][] Mflag)
    {
        /**
         * Инициализиурем переменную s, которая будет хранить промежуточный путь на данном этапе
         * */
        int s = 0;
        /**
         * Циклически проходим по матрице флагов, до первой вычеркнутой строки
         * */
        for (int i = 0; i < N; i++)
        {
            boolean flag = false;
            for (int r = 0; r < N; r++)
                if (Mflag[i][r] != 2)
                {
                    flag = true;
                    break;
                }
            /**
             * Далее вычитаем минимум из каждого элемента строки
             * */
            if (flag)
            {
                /**
                 * min = 9999 это значенеи мимимума по умолчанию, необходимо для правильной работы алгоритма
                 * */
                int min = 9999;
                for (int j = 0; j < N; j++)
                    if (matr[i][j] < min &&Mflag[i][j] == 0) min = matr[i][j];
                s += min;
                for (int j = 0; j < N; j++)
                    if (Mflag[i][j] == 0)
                        matr[i][j] -= min;
            }
        }
        /**
         * Циклически проходим по матрице флагов, до первого вычеркнутого столбца
         */
        for (int i = 0; i < N; i++)
        {
            boolean flag = false;
            for (int r = 0; r < N; r++)
                if (Mflag[r][i] != 2)
                {
                    flag = true;
                    break;
                }
            /**
             * Далее вычитаем минимум из каждого элемента столбца
             * */
            if (flag)
            {
                /**
                 * min = 9999 это значенеи мимимума по умолчанию, необходимо для правильной работы алгоритма
                 * */
                int min = 9999;
                for (int j = 0; j < N; j++)
                    if (matr[j][i] < min &&Mflag[j][i] == 0) min = matr[j][i];
                s += min;
                for (int j = 0; j < N; j++)
                    if (Mflag[j][i] == 0)
                        matr[j][i] -= min;
            }
        }
        /**
         * Полученное промежуточное значение пути добавляем в общий путь T
         * */
        T += s;
        System.out.println();
    }
    /**
     * Функция Zero служит для обработки (оценки) нулей
     * */
    static int[] Zero(int[][] matr, byte[][] Mflag)
    {
        /**
         * Задаём переменные
         * int[] zero - это координаты первого нуля
         * */
        int[] zero = { -1, -1 };
        double max = 0;
        /**
         * Циклически проходимся по дополнительной матрице в поиске и оценке нулей
         * */
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            {
                if (matr[i][j] == 0 &&Mflag[i][j] == 0)
                {
                    /**
                     * min_i и min_j имеют значение по умолчанию 10000 для правильной работы алгоритма (можно корректировать)
                     * */
                    double min_i = 10000, min_j = 10000;
                    for (int k = 0; k < N; k++)
                    {
                        if (k != i &&matr[k][j] <min_j&&Mflag[k][j] == 0)
                            min_j = matr[k][j];
                        if (k != j &&matr[i][k] <min_i&&Mflag[i][k] == 0)
                            min_i = matr[i][k];
                    }
                    if (max <min_i + min_j)
                    {
                        max = min_i + min_j;
                        zero[0] = i;
                        zero[1] = j;
                    }
                }
            }
        /**
         * Возвращаем координаты полученного нуля
         * */
        return zero;
    }
}
