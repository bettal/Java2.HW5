/**
 * Java.2.Lesson.5.Homework
 *
 * @autor Stanislav Lyashov
 */
public class Main {
    private static final int SIZE = 10000000;
    private static final int h = SIZE / 2;

    public static void main(String[] args) {

        Main test2 = new Main();
        test2.twoThreads();

        Main test1 = new Main();
        test1.oneThread();
    }

    public float[] mathFunction(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        return arr;
    }

    public void oneThread() {
        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) arr[i] = 1.0f;
        long time = System.currentTimeMillis();
        mathFunction(arr);
        System.out.println("Однопоточный метод " + (System.currentTimeMillis() - time));
    }

    public void twoThreads() {
        float[] arr = new float[SIZE];
        float[] arr1 = new float[h];
        float[] arr2 = new float[h];

        for (int i = 0; i < arr.length; i++) arr[i] = 1.0f;

        long time = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, h);
        System.arraycopy(arr, h, arr2, 0, h);

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                float[] a1 = mathFunction(arr1);
                System.arraycopy(a1, 0, arr1, 0, a1.length);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                float[] a2 = mathFunction(arr2);
                System.arraycopy(a2, 0, arr2, 0, a2.length);
            }
        });

        t2.start();
        t1.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);
        System.out.println("Двух-поточный метод " + (System.currentTimeMillis() - time));
    }
}

