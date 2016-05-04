import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Александр on 03.05.2016.
 */
public class FileCollector {
    private static int currentNumberOfPackages = 0;
    private static int numberOfPackages = -1;
    private static String pathToFilesDirectory;
    private static String resFileName = "";
    private static String servHomePath;


    public static void addPackage(FilePackage pack) {
        if (isFileCollected()) {
            return;
        }
        if (pack.isLast()) {
            numberOfPackages = pack.getNumberOfPackage();
        }
        try {

            FileOutputStream fileStream = new FileOutputStream(new File(pathToFilesDirectory + "\\" + pack.getNumberOfPackage() + ".txt"));
            fileStream.write(pack.getData());
            fileStream.close();
            currentNumberOfPackages++;
            if (isFileCollected()) {
                File resultDirectory = new File(servHomePath + "\\resultFiles");
                if (!resultDirectory.isDirectory()) {
                    resultDirectory.mkdir();
                }
                File tempFilesDirectory = new File(pathToFilesDirectory);
                File[] packageFiles = tempFilesDirectory.listFiles();
                Arrays.sort(packageFiles, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                File resultFile = new File(resultDirectory.getAbsolutePath() + "\\" + resFileName);
                FileOutputStream resultFileStream =  new FileOutputStream(resultFile, false);

                for (File packageFile : packageFiles) {
                    FileInputStream inputStream = new FileInputStream(packageFile);
                    if (packageFile.length() > Integer.MAX_VALUE) {
                        inputStream.close();
                        continue;
                    }
                    byte[] packageData = new byte[(int) packageFile.length()];
                    inputStream.read(packageData);
                    resultFileStream.write(packageData);
                    inputStream.close();
                }
                resultFileStream.close();
                for (File file : tempFilesDirectory.listFiles()) {
                    file.delete();
                }
                tempFilesDirectory.delete();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    static public boolean isFileCollected() {
        return currentNumberOfPackages == numberOfPackages;
    }

    static public boolean isStartCollect() {
        return currentNumberOfPackages != 0;
    }

    static public void resetCollector(String serverHomePath, String resultFileName) {
        currentNumberOfPackages = 0;
        numberOfPackages = -1;
        resFileName = resultFileName;
        servHomePath = serverHomePath;
        pathToFilesDirectory = serverHomePath + "\\tempDirectory";
        File tempFilesDirectory = new File(pathToFilesDirectory);
        if (tempFilesDirectory != null && tempFilesDirectory.listFiles() != null) {
            for (File file : tempFilesDirectory.listFiles()) {
                file.delete();
            }
        } else {
            tempFilesDirectory.mkdir();
        }
    }


}
