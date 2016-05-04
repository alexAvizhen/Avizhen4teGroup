
/**
 * Created by Александр on 03.05.2016.
 */
public class FilePackage {
    private int numberOfPackage;
    private String checksum;
    private byte[] data;
    private boolean isLast;

    public FilePackage(int numberOfPackage, String checksum, byte[] data, boolean isLast) {
        this.numberOfPackage = numberOfPackage;
        this.checksum = checksum;
        this.data = data;
        this.isLast = isLast;
    }

    public int getNumberOfPackage() {
        return numberOfPackage;
    }

    public String getChecksum() {
        return checksum;
    }

    public byte[] getData() {
        return data;
    }

    public boolean isLast() {
        return isLast;
    }
}
