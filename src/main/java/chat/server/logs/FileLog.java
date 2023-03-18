package chat.server.logs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class FileLog
{
  private final CurrentTime currentTime;
  private final File logDirectory;
  private final File logFile;
  private static final HashMap<File, FileLog> instances = new HashMap<File, FileLog>();

  private FileLog(File logFile) {
    this.logFile = logFile;
    String homePath = System.getProperty("user.home");
    // Default download dir on Windows. (~/Downloads on other systems).
    logDirectory = new File(homePath, "Downloads");
    currentTime = CurrentTime.getInstance();
  }

  public static synchronized FileLog getInstance(File logFile)
  {
    FileLog instance = instances.get(logFile);

    if (instance == null)
    {
      instance = new FileLog(logFile);
      instances.put(logFile, instance);
    }

    return instance;
  }

  private File getLogFile() {
    return logFile;
  }

  public void log(String message) throws IOException {
    try (FileWriter fileWriter = new FileWriter(getLogFile(), true);
        PrintWriter writer = new PrintWriter(fileWriter)) {
      String logLine = currentTime.getFormattedTime() + " - " + message;
      writer.println(logLine);
    }
  }
}
