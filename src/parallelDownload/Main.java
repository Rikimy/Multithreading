package parallelDownload;

public class Main {

  public static void main(String[] args) {
    // Compare the time between parallel threads and non-parallel threads
    long startTime = System.currentTimeMillis();
    MultiThreadedDownloader.go1();
    long endTime = System.currentTimeMillis();
    System.out.println("Time elapsed : " + (endTime - startTime) + " ms");

    startTime = System.currentTimeMillis();
    MultiThreadedDownloader.go2();
    endTime = System.currentTimeMillis();
    System.out.println("Time elapsed : " + (endTime - startTime) + " ms");

  }

}
