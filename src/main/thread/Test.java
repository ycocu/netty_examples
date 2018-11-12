package main.thread;

import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        Task task = new Task();
        RunTask task1 = new RunTask();
        //Future<Integer> result = executor.submit(task);
        Future<?> result = executor.submit(task1);
        executor.shutdown();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        System.out.println("主线程在执行任务");

        try {
            Thread.sleep(3000);
            System.out.println(result.cancel(true));
            System.out.println("取消线程");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("XXXX");
        } finally {
/*
            try {
                System.out.println("task运行结果"+result.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

*/
        }

        System.out.println("所有任务执行完毕");
    }
}

class RunTask implements Runnable {
    //@Override
    public void run() {
        System.out.println("子线程在进行计算");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            //return;
        }
        int sum = 0;
        for(int i=0;i<100;i++)
            sum += i;
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("子线程运行结束 sum=" + sum);

        //return sum;
    }
}
class Task implements Callable<Integer> {
    //@Override
    public Integer call() throws Exception {
        System.out.println("子线程在进行计算");
        Thread.sleep(10000);
        int sum = 0;
        for(int i=0;i<100;i++)
            sum += i;
        return sum;

    }
}
