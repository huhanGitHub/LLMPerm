public class ConsumerIrManager_transmit {
    private void test_ConsumerIrManager_transmit() {
        if(irManager.hasIrEmitter()) {
            // Contains the IR pattern and frequency to transmit
            int frequency = 38000;
            int[] pattern = {1901, 4453, 625, 1614, 625, 1586, 625, 1614, 625, 4463, 625, 1586, 625, 1614, 625, 1586, 625, 1586, 625, 1586, 625, 1614, 625, 1586, 625, 1614, 625, 1586, 625, 1614, 625, 1586, 625, 3923, 625, 1586, 625, 1564};

            try {
                // Transmit the pattern at the frequency
                irManager.transmit(frequency, pattern);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}