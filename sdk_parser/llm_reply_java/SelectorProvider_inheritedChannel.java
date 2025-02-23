public class SelectorProvider_inheritedChannel {
    public void test_SelectorProvider_inheritedChannel() {
        try {
            // Get the default SelectorProvider
            SelectorProvider provider = SelectorProvider.provider();

            // Open a new SocketChannel
            SocketChannel socketChannel = provider.openSocketChannel();

            // Check if the SocketChannel is open
            if (socketChannel.isOpen()) {
                System.out.println("Socket channel is open");
            } else {
                System.out.println("Socket channel is not open");
            }

            // Close the SocketChannel
            socketChannel.close();

            // Try to use the SelectorProvider to open another SocketChannel
            try {
                SocketChannel anotherSocketChannel = provider.openSocketChannel();
                System.out.println("Another socket channel is open");
                anotherSocketChannel.close();
            } catch (IOException e) {
                System.out.println("Unable to open another socket channel");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}