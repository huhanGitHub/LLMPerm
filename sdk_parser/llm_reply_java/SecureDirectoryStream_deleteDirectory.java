import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class SecureDirectoryStream_deleteDirectory {
    public void test_SecureDirectoryStream_deleteDirectory(Path dirPath) throws IOException {
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dirPath);

        if (directoryStream instanceof SecureDirectoryStream) {
            SecureDirectoryStream<Path> secureDirectoryStream = (SecureDirectoryStream<Path>) directoryStream;

            Files.walkFileTree(dirPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    secureDirectoryStream.deleteEntry(file.getFileName());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    if (exc != null) throw exc;
                    if (!dir.equals(dirPath)) {
                        secureDirectoryStream.deleteEntry(dir.getFileName());
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }
}