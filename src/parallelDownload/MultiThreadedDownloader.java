package parallelDownload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadedDownloader {

  private static final String[] FILE_URLS = {
      "https://search.maven.org/remotecontent?filepath=com/h2database/h2/2.1.214/h2-2.1.214.jar",
      "https://github.com/h2database/h2database/releases/download/version-2.1.214/h2-setup-2022-06-13.exe",
      "https://github.com/h2database/h2database/releases/download/version-2.1.214/h2-2022-06-13.zip",
      "https://github.com/h2database/h2database/releases/download/version-2.1.212/h2-setup-2022-04-09.exe",
      "https://github.com/h2database/h2database/releases/download/version-2.1.212/h2-2022-04-09.zip"};

  // Method to download files in serial
  public static void go1() {
    List<DownloadThread> downloadThreads = new ArrayList<>();
    for (int i = 0; i < FILE_URLS.length; i++) {
      DownloadThread downloadThread = new DownloadThread(FILE_URLS[i], "file" + (i + 1) + ".zip");
      downloadThreads.add(downloadThread);
      downloadThread.start();
    }

    // Wait for all threads to finish
    for (DownloadThread downloadThread : downloadThreads) {
      try {
        downloadThread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println("All files were downloaded successfully.");
  }

  // Method to download files in parallel
  public static void go2() {
    List<DownloadThread> downloadThreads = new ArrayList<>();
    for (int i = 0; i < FILE_URLS.length; i++) {
      DownloadThread downloadThread = new DownloadThread(FILE_URLS[i], "file" + (i + 1) + ".zip");
      downloadThreads.add(downloadThread);
      downloadThread.start();

      try {
        downloadThread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    System.out.println("All files were downloaded successfully.");
  }

  private static class DownloadThread extends Thread {

    private final String fileUrl;
    private final String fileName;

    public DownloadThread(String fileUrl, String fileName) {
      this.fileUrl = fileUrl;
      this.fileName = fileName;
    }

    public void run() {
      try {
        URL url = new URL(fileUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
          InputStream inputStream = httpConn.getInputStream();
          FileOutputStream outputStream = new FileOutputStream(fileName);

          byte[] buffer = new byte[1024];
          int bytesRead;
          while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
          }
          outputStream.close();
          inputStream.close();
          System.out.println("File " + fileName + " downloaded successfully.");
        } else {
          System.out.println("File " + fileName + " could not be downloaded.");
        }
        httpConn.disconnect();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}