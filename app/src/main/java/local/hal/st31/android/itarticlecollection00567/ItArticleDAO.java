package local.hal.st31.android.itarticlecollection00567;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

/**
 * ST31 評定課題 ITARTICLECOLLECTION
 *
 *  DAOクラス
 *
 * @author Gerald Oliver
 */



public class ItArticleDAO {
    /**
     * 情報を登録するメソッド。
     *
     * @param db      SQLiteDatabaseオブジェクト。
     * @return id
     */

    public static long insert(SQLiteDatabase db, String title, String url,
                              String comment, String lastName, String firstName , String studentId , String seatNo , String timeStamp  ) {
        String sql = "INSERT or REPLACE INTO article (title, url ,comment, lastname , firstname , studentid , seatno , timestamp) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, title);
        stmt.bindString(2, url);
        stmt.bindString(3, comment);
        stmt.bindString(4, lastName);
        stmt.bindString(5, firstName);
        stmt.bindString(6, studentId);
        stmt.bindString(7, seatNo);
        stmt.bindString(8, timeStamp);
        long ids = stmt.executeInsert();
        return ids;
    }

}
