package local.hal.st31.android.itarticlecollection00567;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * ST31 評定課題 ITARTICLECOLLECTION
 *
 *  *  データベースヘルパー
 *
 * @author Gerald Oliver
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * データベースファイル名の定数フィールド。
     */
    private static final String DATABASE_NAME = "post.db";
    /**
     * バージョン情報の定数フィールド。
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * コンストラクタ。
     *
     * @param context コンテキスト。
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * データベースファイル名の定数フィールド。
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE article (");
        sb.append("_id INTEGER PRIMARY KEY AUTOINCREMENT,");
        sb.append("title TEXT NOT NULL,");
        sb.append("url TEXT NOT NULL,");
        sb.append("comment TEXT NOT NULL,");
        sb.append("lastname TEXT NOT NULL,");
        sb.append("firstname TEXT NOT NULL,");
        sb.append("studentid TEXT NOT NULL,");
        sb.append("seatno TEXT NOT NULL,");
        sb.append("timestamp TEXT NOT NULL");
        sb.append(");");
        String sql = sb.toString();

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}


