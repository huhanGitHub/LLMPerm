public class MediaDrm_getNewestAvailablePackageCertificateRawBytes {
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void test_MediaDrm_getNewestAvailablePackageCertificateRawBytes() {
        byte[] sessionId = null;
        byte[] expected = new byte[0];
        MediaDrm.KeyRequest keyRequest = null;
        
        MediaDrm mediaDrm = Mockito.mock(MediaDrm.class);
        UUID uuid = new UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L);
        
        try {
            sessionId = mediaDrm.openSession();
            expected = mediaDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID);
            keyRequest = mediaDrm.getKeyRequest(sessionId, expected, "video/mp4", MediaDrm.KEY_TYPE_OFFLINE, null);
            byte[] actual = mediaDrm.provideKeyResponse(sessionId, keyRequest.getData());
            
            // assert the expected and actual values
            Assert.assertArrayEquals(expected, actual);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sessionId != null) {
                mediaDrm.closeSession(sessionId);
            }
        }
    }
}