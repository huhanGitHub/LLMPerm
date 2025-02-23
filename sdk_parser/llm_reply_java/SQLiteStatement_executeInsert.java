public class SQLiteStatement_executeInsert {
    public long test_SQLiteStatement_executeInsert() {
        SQLiteDatabase db = null;
        SQLiteStatement stmt = null;
        long result = -1;

        try {
            // Assuming you have 'MyDBHelper' that extends 'SQLiteOpenHelper'
            MyDBHelper dbHelper = new MyDBHelper(this);

            // Get a writable database
            db = dbHelper.getWritableDatabase();

            // Define an insert statement
            String sql = "INSERT INTO users (username, email) VALUES (?, ?)";

            // Prepare the statement
            stmt = db.compileStatement(sql);

            // Bind the values
            stmt.bindString(1, "JohnDoe");
            stmt.bindString(2, "johndoe@mail.com");

            // Execute insert statement
            result = stmt.executeInsert();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Clean up
            if (stmt != null) {
                stmt.close();
            }
            if (db != null) {
                db.close();
            }
        }

        // Return the ID of the new record
        return result;
    }
}