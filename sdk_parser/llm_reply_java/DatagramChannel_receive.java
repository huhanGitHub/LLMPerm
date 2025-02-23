public class DatagramChannel_receive {
    public void test_DatagramChannel_receive() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DatagramChannel datagramChannel = DatagramChannel.open();

                    // Bind the DatagramChannel to a specific address
                    datagramChannel.bind(new InetSocketAddress("localhost", 8888));

                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                    // Use DatagramChannel's receive method
                    SocketAddress senderAddress = datagramChannel.receive(byteBuffer);
                    byteBuffer.flip();
                    while(byteBuffer.hasRemaining()){
                        System.out.println((char) byteBuffer.get());
                    }

                    // Don't forget to close the DatagramChannel when you're done using it
                    datagramChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}