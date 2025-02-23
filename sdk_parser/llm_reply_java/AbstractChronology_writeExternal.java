public class AbstractChronology_writeExternal {
    @Test
    public void test_AbstractChronology_writeExternal() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            AbstractChronology ac = IsoChronology.INSTANCE;

            oos.writeObject(ac);
            oos.flush();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);

            AbstractChronology readAc = (AbstractChronology) ois.readObject();

            assertEquals(ac.getId(), readAc.getId());
            assertEquals(ac.getCalendarType(), readAc.getCalendarType());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}