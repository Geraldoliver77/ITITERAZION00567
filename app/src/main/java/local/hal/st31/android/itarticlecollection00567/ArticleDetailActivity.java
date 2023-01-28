package local.hal.st31.android.itarticlecollection00567;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import local.hal.st31.android.itarticlecollection00567.databinding.ActivityMainBinding;

/**
 * ST31 評定課題 ITARTICLECOLLECTION
 * <p>
 * ArticleDetailActivityクラス
 *
 * @author Gerald Oliver
 */
public class ArticleDetailActivity extends AppCompatActivity {

    /**
     * ログに記載するタグ用の文字列。
     */
    private static final String DEBUG_TAG = "ITARTICLECOLLECTION00567";

    /**
     * POST先のURL。
     */
    private static final String ACCESS_URL = "https://hal.architshin.com/st31/getOneArticle.php";

    private String idString = "";

    private String urls = "";

    /**
     * モデルクラス。
     */
    ItArticle it = new ItArticle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Intent intent = getIntent();
        idString = intent.getStringExtra("idNo");

        new ArticleDetailActivity.fetchData().start();

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

        MenuItem browseMenus = menu.findItem(R.id.menuBrowser);
        browseMenus.setVisible(true);

        return true;
    }


    @Override
    public void onRestart() {
        super.onRestart();

    }

    /**
     * JSONデータをパースするクラス
     */

    private class fetchData extends Thread {

        ItArticle it = new ItArticle();
        String data = "";
        String name = "";

        TextView idText = (TextView) findViewById(R.id.idText);
        TextView titleText = (TextView) findViewById(R.id.titleText);
        TextView urlText = (TextView) findViewById(R.id.urlText);
        TextView commentText = (TextView) findViewById(R.id.commentText);
        TextView studentText = (TextView) findViewById(R.id.studentIdText);
        TextView seatText = (TextView) findViewById(R.id.seatText);
        TextView nameText = (TextView) findViewById(R.id.nameText);
        TextView dateText = (TextView) findViewById(R.id.createdText);

        @Override
        public void run() {

            System.out.println("its in");
            try {

                URL url = new URL(ACCESS_URL + "?id=" + idString);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    data = data + line;
                }

                if (!data.isEmpty()) {

                    JSONObject jsonObject = new JSONObject(data);
                    String status = jsonObject.getString("status");
                    String msg = jsonObject.getString("msg");
                    JSONArray list = jsonObject.getJSONArray("article");

                    System.out.println(status);
                    System.out.println(msg);
                    System.out.println(list);

                    for (int i = 0; i < list.length(); i++) {

                        JSONObject listContent = list.getJSONObject(i);

                        it.setId(listContent.getString("id"));

                        it.setTitle(listContent.getString("title"));

                        it.setUrls(listContent.getString("url"));

                        urls=listContent.getString("url");

                        it.setComment(listContent.getString("comment"));

                        it.setStudentId(listContent.getString("student_id"));

                        it.setSeatNo(listContent.getString("seat_no"));

                        it.setLast_name(listContent.getString("last_name"));

                        it.setFirst_name(listContent.getString("first_name"));

                        it.setCreatedAt(listContent.getString("created_at"));

                        name = it.getLast_name() + " " + it.getFirst_name();

                    }

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            idText.setText(it.getId());
                            titleText.setText(it.getTitle());
                            urlText.setText(it.getUrls());
                            commentText.setText(it.getComment());
                            studentText.setText(it.getStudentId());
                            seatText.setText(it.getSeatNo());
                            nameText.setText(name);
                            dateText.setText(it.getCreatedAt());
                        }
                    });
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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

        switch (itemId) {
            case R.id.menuBrowser:

                System.out.println("url is " + urls);

                Uri uri = Uri.parse(urls);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;

            case android.R.id.home:
                Intent i = new Intent(ArticleDetailActivity.this, MainActivity.class);
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

}