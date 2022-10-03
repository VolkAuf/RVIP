public class PoolAsyncRunnable implements Runnable {
    private int row;

    public enum FuncEnum {
        FillRow,
        PrintRow,
        FindMaxInRow,
        DivisionRowElems
    }

    private FuncEnum selectedFunc;

    public PoolAsyncRunnable(int rowNumber, FuncEnum selectedFunc) {
        row = rowNumber;
        this.selectedFunc = selectedFunc;
    }

    @Override
    public void run() {
        switch (selectedFunc) {
            case FillRow -> {
                FillRow();
            }
            case PrintRow -> {
                PrintRow();
            }
            case FindMaxInRow -> {
                FindMaxInRow();
            }
            case DivisionRowElems -> {
                DivisionRowElems();
            }
        }
    }

    public void FillRow() {
        for (int i = 0; i < Main.arrs[row].length; i++)
            Main.arrs[row][i] = Main.rnd.nextInt(999999) + 1;
    }

    public void PrintRow() {
        for (int i = 0; i < Main.arrs[row].length; i++)
            System.out.print(Main.arrs[row][i] + " ");
        System.out.println();
    }

    public void FindMaxInRow() {
        for (int i = 0; i < Main.arrs[row].length; i++)
            if (Main.arrs[row][i] > Main.max)
                Main.max = Main.arrs[row][i];
    }

    public void DivisionRowElems() {
        for (int i = 0; i < Main.arrs[row].length; i++)
            Main.arrs[row][i] = Main.arrs[row][i] / Main.max;
    }
}