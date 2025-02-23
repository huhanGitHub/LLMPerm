import java.nio.file.*;
import java.nio.file.spi.FileSystemProvider;
import java.util.List;

public class FileSystemProvider_copy {

    public void test_FileSystemProvider_Copy() {
        try {
            List<FileSystemProvider> providersList = FileSystemProvider.installedProviders();
            FileSystemProvider provider = null;

            for (FileSystemProvider p : providersList) {
                if (p.getScheme().equalsIgnoreCase("file")) {
                    provider = p;
                    break;
                }
            }

            if (provider != null) {
                Path copyFrom = Paths.get("path-to-source-file");
                Path copyTo = Paths.get("path-to-destination-file");

                provider.copy(copyFrom, copyTo, StandardCopyOption.COPY_ATTRIBUTES, 
                              StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception ex) {
            System.out.println("Exception occurred: " + ex.toString());
        }
    }
}