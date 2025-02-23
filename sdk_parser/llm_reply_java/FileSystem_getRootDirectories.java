public class FileSystem_getRootDirectories {

    public void test_FileSystem_getRootDirectories() {
        FileSystem fileSystem = FileSystems.getDefault();
        Iterable<Path> rootDirectories = fileSystem.getRootDirectories();

        for (Path path : rootDirectories) {
            Log.i("ROOT_DIRECTORIES", path.toString());
        }
    }
}