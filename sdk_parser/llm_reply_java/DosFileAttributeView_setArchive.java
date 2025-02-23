public class DosFileAttributeView_setArchive {
    public void test_DosFileAttributeView_setArchive(String filePath) throws IOException {
        File file = new File(filePath);
        Path path = file.toPath();
        DosFileAttributeView view = Files.getFileAttributeView(path, DosFileAttributeView.class);
        if(view != null) {
            // Set Archive flag for the file
            view.setArchive(true);
            System.out.println("Successfully set 'Archive' attribute for the file");
        } else {
            System.out.println("FileStore does not support DOS attributes");
        }
    }
}