import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static int[][] arrs;
    public static Random rnd;
    private static int rowCount = 10;
    private static int columnCount = 10;
    public static int max;

    public static void main(String[] args) throws InterruptedException {
        arrs = new int[rowCount][columnCount];
        rnd = new Random();

        var defaultTime = DefaultAlgorithm();
        var poolAsyncTime = PoolAsyncAlgorithm();

        System.out.println("Default time = " + defaultTime + " ms");
        System.out.println("Pool Async time = " + poolAsyncTime + " ms");
        System.out.println("___________________________");
    }

    //region Pool Async
    private static ThreadPoolExecutor executorService;
    private static ReentrantLock generatorLocker = new ReentrantLock();
    private static Condition poolIsFull = generatorLocker.newCondition();
    private static int threadCount;

    private static long PoolAsyncAlgorithm() throws InterruptedException {
        var startTime = System.currentTimeMillis();

        threadCount = 10;
        executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);

        InitArrsPoolAsync();
        PrintArrsPoolAsync();
        max = FindMaxPoolAsync();
        DivisionArrsElemsPoolAsync(max);
        PrintArrsPoolAsync();
        executorService.shutdown();

        System.out.println("___________________________");
        return System.currentTimeMillis() - startTime;
    }

    private static void InitArrsPoolAsync() throws InterruptedException {
        for (int i = 0; i < arrs.length; i++){
            generatorLocker.lockInterruptibly();
            try {
                while (executorService.getQueue().size() > threadCount) {
                    poolIsFull.await();
                }
                executorService.execute(new PoolAsyncRunnable(i, PoolAsyncRunnable.FuncEnum.FillRow));
                if (executorService.getQueue().size() <= threadCount) {
                    poolIsFull.signal();
                }
            } finally {
                generatorLocker.unlock();
            }
        }
    }

    private static void PrintArrsPoolAsync() throws InterruptedException {
        for (int i = 0; i < arrs.length; i++){
            generatorLocker.lockInterruptibly();
            try {
                while (executorService.getQueue().size() > threadCount) {
                    poolIsFull.await();
                }
                executorService.execute(new PoolAsyncRunnable(i, PoolAsyncRunnable.FuncEnum.PrintRow));
                if (executorService.getQueue().size() <= threadCount) {
                    poolIsFull.signal();
                }
            } finally {
                generatorLocker.unlock();
            }
        }
        System.out.println();
    }

    private static int FindMaxPoolAsync() {
        max = arrs[0][0];
        for (int i = 0; i < arrs.length; i++)
            executorService.execute(new PoolAsyncRunnable(i, PoolAsyncRunnable.FuncEnum.FindMaxInRow));
        System.out.println("Max: " + max);
        return max;
    }

    private static void DivisionArrsElemsPoolAsync(int value) {
        for (int i = 0; i < arrs.length; i++)
            executorService.execute(new PoolAsyncRunnable(i, PoolAsyncRunnable.FuncEnum.DivisionRowElems));
    }

    //endregion

    //region Default
    private static long DefaultAlgorithm() {
        var startTime = System.currentTimeMillis();

        InitArrs();
        PrintArrs();
        int max = FindMax();
        DivisionArrsElems(max);
        PrintArrs();

        System.out.println("___________________________");
        return System.currentTimeMillis() - startTime;
    }

    private static void InitArrs() {
        for (int i = 0; i < arrs.length; i++)
            for (int j = 0; j < arrs[i].length; j++)
                arrs[i][j] = rnd.nextInt(999999) + 1;
    }

    private static int FindMax() {
        int max = arrs[0][0];
        for (int i = 0; i < arrs.length; i++)
            for (int j = 0; j < arrs[i].length; j++)
                if (arrs[i][j] > max)
                    max = arrs[i][j];

        System.out.println("Max: " + max);
        return max;
    }

    private static void DivisionArrsElems(int value) {
        for (int i = 0; i < arrs.length; i++)
            for (int j = 0; j < arrs[i].length; j++)
                arrs[i][j] = arrs[i][j] / value;
    }

    private static void PrintArrs() {
        for (int i = 0; i < arrs.length; i++) {
            for (int j = 0; j < arrs[i].length; j++)
                System.out.print(arrs[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    //endregion
}

