package com.example;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.tv.TvContract;
import android.net.Uri;
import android.util.Log;

public class TvContract_requestChannelBrowsable {

    public void test_TvContract_requestChannelBrowsable(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = TvContract.Channels.CONTENT_URI;
        String[] projection = {TvContract.Channels.COLUMN_BROWSABLE};
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null; 

        Cursor cursor = null;
        try {
            cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
            if (cursor != null && cursor.moveToNext()) {
                int index = cursor.getColumnIndexOrThrow(TvContract.Channels.COLUMN_BROWSABLE);
                int isBrowsable = cursor.getInt(index);
                Log.d("TvContract", "Channel is browsable: " + (isBrowsable == 1));
            }
        } catch (Exception e) {
            Log.e("TvContract", "Failed to retrieve channel browsable status.", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}