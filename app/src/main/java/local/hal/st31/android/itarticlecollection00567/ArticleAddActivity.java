package local.hal.st31.android.itarticlecollection00567;

import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ST31 評定課題 ITARTICLECOLLECTION
 *
 * ArticleAddActivityクラス
 *
 * @author Gerald Oliver
 */

public class ArticleAddActivity extends AppCompatActivity {
    /**
     * ログに記載するタグ用の文字列。
     */
    private static final String DEBUG_TAG = "ITARTICLECOLLECTION00567";
    /**
     * POST先のURL。
     */
    private static final String ACCESS_URL =
            "https://hal.architshin.com/st31/insertItArticle.php?limit=30";

    /**
     * データベース
     */
    private DatabaseHelper _helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_add);

        _helper = new DatabaseHelper(ArticleAddActivity.this);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.outline_arrow_back_white_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * クリックされているメニューアイコンにより処理を行う
     *
     * @param menu アイコン。
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);

        MenuItem addMenus = menu.findItem(R.id.menuAdd);
        addMenus.setVisible(false);

        MenuItem saveMenus = menu.findItem(R.id.menuSaveDone);
        saveMenus.setVisible(true);

        return true;
    }

    /**
     * 選択されているメニュアイテム
     *
     * @param item 選択されているアイテム。
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean returnVal = true;
        int itemId = item.getItemId();

        TextView lastName = findViewById(R.id.lastName);
        EditText etLastName = findViewById(R.id.etLastName);

        TextView firstName = findViewById(R.id.firstName);
        EditText etFirstName = findViewById(R.id.etFirstName);

        TextView studentId = findViewById(R.id.studentId);
        EditText etStudentId = findViewById(R.id.etStudentId);

        TextView seatNo = findViewById(R.id.seatNo);
        EditText etSeatNo = findViewById(R.id.etSeatNo);

        Button button = findViewById(R.id.btSend);


        switch (itemId) {

            case R.id.menuSaveDone:
                Toast.makeText(ArticleAddActivity.this, R.string.msg_input_title, Toast.LENGTH_SHORT).show();

                lastName.setVisibility(View.VISIBLE);
                etLastName.setVisibility(View.VISIBLE);

                firstName.setVisibility(View.VISIBLE);
                etFirstName.setVisibility(View.VISIBLE);

                studentId.setVisibility(View.VISIBLE);
                etStudentId.setVisibility(View.VISIBLE);

                seatNo.setVisibility(View.VISIBLE);
                etSeatNo.setVisibility(View.VISIBLE);

                button.setVisibility(View.VISIBLE);

                break;


            case android.R.id.home:
                Intent i = new Intent(ArticleAddActivity.this, MainActivity.class);
                overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
                startActivity(i);
                overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
                super.finish();
                break;
            default:
                returnVal = super.onOptionsItemSelected(item);
        }


        return returnVal;
    }

    /**
     * 送信ボタンがクリックしたときの処理メソッド。
     *
     * @param view Viewオブジェクト。
     */
    public void onSendButtonClick(View view) {

        EditText etTitle = findViewById(R.id.etTitle);
        EditText etUrl = findViewById(R.id.etUrl);
        EditText etMessage = findViewById(R.id.etComment);
        EditText etLastName = findViewById(R.id.etLastName);
        EditText etFirstName = findViewById(R.id.etFirstName);
        EditText etStudentId = findViewById(R.id.etStudentId);
        EditText etSeatNo = findViewById(R.id.etSeatNo);

        String title = etTitle.getText().toString();
        String inputUrl = etUrl.getText().toString();
        String msg = etMessage.getText().toString();

        String lastName = etLastName.getText().toString();
        String firstName = etFirstName.getText().toString();
        String studentId = etStudentId.getText().toString();
        String seatNo = etSeatNo.getText().toString();


        if (msg.length() < 1) {
            msg = "";
        }


        if (!lastName.equals("") && !firstName.equals("") && !studentId.equals("") && !seatNo.equals("") && !title.equals("") && !inputUrl.equals("")) {
            sendPostData(ACCESS_URL, title, inputUrl, msg, lastName, firstName, studentId, seatNo);
        } else {
            Toast.makeText(ArticleAddActivity.this, R.string.toast_msg,
                    Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * データを送信するクラス
     */

    private void sendPostData(final String url, String title, String inputUrl, String msg, final String lastName, String firstName, String studentId, String seatNo) {
        Looper mainLooper = Looper.getMainLooper();
        Handler handler = HandlerCompat.createAsync(mainLooper);
        BackgroundExecutor backgroundPostAccess = new BackgroundExecutor(handler, url, title, inputUrl, msg, lastName, firstName, studentId, seatNo);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(backgroundPostAccess);
    }

    /**
     * バックグラウンドスレッドの途中経過をプログレスバーに反映させる処理用クラス。
     */
    /**
     * 非同期でサーバにポストするためのクラス。
     */
    private class BackgroundExecutor implements Runnable {
        /**
         * ハンドラオブジェクト。
         */
        private final Handler _handler;
        /**
         * ポスト先URL。
         */

        private final String _url;

        private final String _title;

        private final String _inputUrl;

        private final String _msg;

        private final String _lastName;

        private final String _firstName;

        private final String _studentId;

        private final String _seatNo;

        /**
         * コンストラクタ。
         * 非同期でサーバにポストするのに必要な情報を取得する。
         *  @param handler   ハンドラオブジェクト。
         * @param url       ポスト先URL。
         * @param title
         * @param inputUrl
         * @param lastName  ユーザの名
         * @param firstName ユーザの性
         * @param studentId 　　学生のID
         * @param seatNo    学生の出席番号
         * @param msg       先生にコメント
         */
        public BackgroundExecutor(Handler handler, String url, String title, String inputUrl, String msg , String lastName, String firstName, String studentId, String seatNo) {
            _handler = handler;
            _url = url;
            _title = title;
            _inputUrl = inputUrl;
            _msg = msg;
            _lastName = lastName;
            _firstName = firstName;
            _studentId = studentId;
            _seatNo = seatNo;

        }

        @WorkerThread
        @Override
        public void run() {

            SQLiteDatabase db = _helper.getWritableDatabase();

            String name = "";
            String studentId = "";
            String seatNo = "";
            String timestamp = "";

            String title = "";

            String postData =
                            "title=" + _title
                            + "&url=" + _inputUrl
                            + "&comment=" + _msg
                    + "&lastname=" + _lastName
                    + "&firstname=" + _firstName
                    + "&studentid=" + _studentId
                    + "&seatno=" + _seatNo;

            HttpURLConnection con = null;
            InputStream is = null;
            String result = "";
            boolean success = false;
            int flag = 0;

            ProgressUpdateExecutor progressUpdateExecutor = new
                    ProgressUpdateExecutor(2);
            _handler.post(progressUpdateExecutor);

            try {

                URL url = new URL(_url);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");

                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                con.setDoOutput(true);

                OutputStream os = con.getOutputStream();
                os.write(postData.getBytes());
                os.flush();
                os.close();
                int status = con.getResponseCode();
                if (status != 200) {
                    throw new IOException("ステータスコード:" + status);

                }
                is = con.getInputStream();
                result = is2String(is);
                JSONObject rootJSON = new JSONObject(result);
                name = rootJSON.getString("name");
                title = rootJSON.getString("title");
                studentId = rootJSON.getString("studentid");
                seatNo = rootJSON.getString("seatno");
                timestamp = rootJSON.getString("timestamp");


                Log.i("TAG", "test log : " + result);
                Log.i("TAG", " msg : " + _msg);
                Log.i("TAG", " title : " + title);

                success = true;
                progressUpdateExecutor = new ProgressUpdateExecutor(3);
                _handler.post(progressUpdateExecutor);
            } catch (SocketTimeoutException ex) {
                flag= 1;
                result = getString(R.string.msg_err_timeout);
                Log.e(DEBUG_TAG, "タイムアウト", ex);
            } catch (MalformedURLException ex) {
                flag= 1;
                result = getString(R.string.msg_err_send);
                Log.e(DEBUG_TAG, "URL変換失敗", ex);
            } catch (IOException ex) {
                flag= 1;
                result = getString(R.string.msg_err_send);
                Log.e(DEBUG_TAG, "通信失敗", ex);
            } catch (JSONException e) {
                flag= 1;
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException ex) {
                    result = getString(R.string.msg_err_send);
                    Log.e(DEBUG_TAG, "InputStream解放失敗", ex);
                }
            }


            if (flag == 0 && !title.equals("ほげ")) {
                ItArticleDAO.insert(db, _title, _inputUrl, _msg, _lastName, _firstName, _studentId, _seatNo , timestamp);
            }


            PostExecutor postExecutor = new PostExecutor(result, success);
            _handler.post(postExecutor);
        }

        /**
         * InputStreamオブジェクトを文字列に変換するメソッド。 変換文字コードはUTF-8。
         *
         * @param is 変換対象のInputStreamオブジェクト。
         * @return 変換された文字列。
         * @throws IOException 変換に失敗した時に発生。
         */
        private String is2String(InputStream is) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,
                    StandardCharsets.UTF_8));
            StringBuffer sb = new StringBuffer();
            char[] b = new char[1024];
            int line;
            while (0 <= (line = reader.read(b))) {
                sb.append(b, 0, line);
            }
            return sb.toString();
        }
    }

    private class ProgressUpdateExecutor implements Runnable {

        /**
         * プログレスバーの値。
         */
        private final int _progress;

        /**
         * コンストラクタ。
         *
         * @param progress プログレスバーの値。
         */
        public ProgressUpdateExecutor(int progress) {
            _progress = progress;
        }

        @UiThread
        @Override
        public void run() {

        }
    }

    /**
     * 非同期でポストした後にUIスレッドでその情報を表示するためのクラス。
     */
    private class PostExecutor implements Runnable {
        /**
         * 取得した文字列情報。
         * 取得に失敗した場合は、その内容を表す文字列。
         */
        private final String _result;
        /**
         * 通信に成功し、無事データを取得したかどうかを表す値。
         */
        private final boolean _success;


        PostExecutor(String result, boolean success) {
            _result = result;
            _success = success;
        }

        @UiThread
        @Override
        public void run() {

            String message = _result;
            if (_success) {
                String name = "";
                String studentId = "";
                String seatNo = "";
                String status = "";
                String msg = "";

                String timestamp = "";

                String title = "";


                try {
                    JSONObject rootJSON = new JSONObject(_result);
                    name = rootJSON.getString("name");
                    studentId = rootJSON.getString("studentid");
                    seatNo = rootJSON.getString("seatno");
                    status = rootJSON.getString("status");
                    msg = rootJSON.getString("msg");

                    timestamp = rootJSON.getString("timestamp");
                    title = rootJSON.getString("title");

                    message = "Title : " + title + "\n" +
                            "StudentId : " + studentId + "\n" +
                            "SeatNo : " + seatNo + "\n" +
                            "Status : " + status + "\n" +
                            "Msg : " + msg + "\n" +
                            "TimeStamp : " + timestamp + "\n";


                } catch (JSONException ex) {

                    message = getString(R.string.msg_err_parse);
                    Log.e(DEBUG_TAG, "JSON解析失敗", ex);
                }
                    message = msg;



                ItDialog dialog = new ItDialog();
                Bundle extras = new Bundle();

                extras.putString("message", message);
                FragmentManager manager = getSupportFragmentManager();
                dialog.setArguments(extras);
                dialog.show(manager, "ItDialog");

                Intent i = new Intent(ArticleAddActivity.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("msg", message);
                i.putExtras(bundle);

                overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
                startActivity(i);
                overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
                finish();


            }

        }
    }


}