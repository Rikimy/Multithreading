package sharedVariable;

public class SharedVariableExampleSync {
  public static int sharedVariable = 0;

  public static void go() {

    /*Thread not using a lambda expression
       Thread thread1 = new Thread(new Runnable() {
        public void run() {
          for (int i = 0; i < 1000000; i++) {
            synchronized (SharedVariableExampleSync.class) {
              sharedVariable++;
            }
          }
        }
      });
     */

    // Thread using a lambda expression
    Thread thread1 = new Thread(() -> {
      for (int i = 0; i < 1000000; i++) {
        synchronized (SharedVariableExampleSync.class) {
          sharedVariable++;
        }
      }
    });

    Thread thread2 = new Thread(() -> {
      for (int i = 0; i < 1000000; i++) {
        synchronized (SharedVariableExampleSync.class) {
          sharedVariable++;
        }
      }
    });

    thread1.start();
    thread2.start();

    try {
      thread1.join();
      thread2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("Final value of the shared variable : " + sharedVariable);
  }
}
