package sharedVariable;

public class SharedVariableExampleNonSync {

  public static int sharedVariable = 0;
  public static void go() {

    Thread thread1 = new Thread(() -> {
        for (int i = 0; i < 1000000; i++) {
          sharedVariable++;
        }
    });

    Thread thread2 = new Thread(() -> {
      for (int i = 0; i < 1000000; i++) {
        sharedVariable++;
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
