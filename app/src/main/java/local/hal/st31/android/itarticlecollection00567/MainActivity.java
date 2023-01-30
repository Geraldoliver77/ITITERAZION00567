package local.hal.st31.android.itarticlecollection00567;

import static android.content.ContentValues.TAG;

import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import local.hal.st31.android.itarticlecollection00567.databinding.ActivityMainBinding;

/**
 * ST31 評定課題 ITARTICLECOLLECTION
 * <p>
 * MAIN ACTIVITYクラス
 *
 * @author Gerald Oliver
 */

public class MainActivity extends AppCompatActivity {


    /**
     * ログに記載するタグ用の文字列。
     */
    private static final String DEBUG_TAG = "ITARTICLECOLLECTION00567";

    /**
     * POST先のURL。
     */
    private static final String ACCESS_URL = "https://hal.architshin.com/st31/getItArticlesList.php?limit=50";

    ActivityMainBinding binding;
    ArrayList<String> userInfoList;
    ArrayAdapter<String> listAdapter;

    Handler mainHandler = new Handler();

    ListView listView;

    ArrayList<String> userId;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeUserList();
        new fetchData().start();


        Bundle extras = getIntent().getExtras();
        String message = "";
        if (extras != null) {
            message = extras.getString("msg");
            ItDialog dialog = new ItDialog();
            extras.putString("message", message);
            FragmentManager manager = getSupportFragmentManager();
            dialog.setArguments(extras);
            dialog.show(manager, "ItDialog");
        }

        Log.i("TAG", " bundle : " + message);


    }

    @Override
    public void onRestart() {
        super.onRestart();
        // do some stuff here
    }


    /**
     * 追加アイコンがクリックされたら
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean returnVal = true;
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menuAdd:
                Intent intent = new Intent(MainActivity.this, ArticleAddActivity.class);
                overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
                overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
                startActivity(intent);
                break;
            default:
                returnVal = super.onOptionsItemSelected(item);
        }

        return returnVal;
    }

    /**
     * メニューアイテムを指定するクラス
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    /**
     * ユーザ配列に値を入れる
     */
    private void initializeUserList() {
        userInfoList = new ArrayList<>();
        listView = findViewById(R.id.userList);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userInfoList);

        userId = new ArrayList<>();

        for (String str : userInfoList) {
            listAdapter.add(str);
        }
        int test = 0;
        // adapterをListViewにセット
        listView.setAdapter(listAdapter);

        /**
         * リストビューがクリックされている時点
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String theId = "";

                for (int i = 0; i < userId.size(); i++) {

                    if (i == id) {
                        theId = userId.get(i);
                    }

                }

//                Toast.makeText(MainActivity.this, theId, Toast.LENGTH_SHORT).show();

                // 次のアクティビティへ
                Intent intent = new Intent(MainActivity.this, ArticleDetailActivity.class);
                intent.putExtra("idNo", theId);
                overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
                overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
                startActivity(intent);

            }
        });

    }

    /**
     * JSONデータをパースするクラス
     */

    private class fetchData extends Thread {

        String data = "";

        @Override
        public void run() {

            try {
                URL url = new URL(ACCESS_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    data = data + line;
                }

                if (!data.isEmpty()) {
                    String id = "";
                    String title = "";
                    String urls = "";
                    String comment = "";
                    String studentId = "";
                    String seatNo = "";
                    String last_name = "";
                    String first_name = "";
                    String createdAt = "";

                    JSONObject jsonObject = new JSONObject(data);
                    String status = jsonObject.getString("status");
                    String msg = jsonObject.getString("msg");
                    JSONArray list = jsonObject.getJSONArray("list");
                    userInfoList.clear();

                    System.out.println(status);
                    System.out.println(msg);
                    System.out.println(list);

                    for (int i = 0; i < list.length(); i++) {

                        JSONObject listContent = list.getJSONObject(i);
                        id = listContent.getString("id");
                        title = listContent.getString("title");
                        urls = listContent.getString("url");
                        comment = listContent.getString("comment");
                        studentId = listContent.getString("student_id");
                        seatNo = listContent.getString("seat_no");
                        last_name = listContent.getString("last_name");
                        first_name = listContent.getString("first_name");
                        createdAt = listContent.getString("created_at");

                        String showToDisplay = title + "\n" + "\n" + last_name +" " + first_name;
                        userInfoList.add(showToDisplay);
                        userId.add(id);

                    }
                }
                System.out.println(" userInfoList :" + userInfoList);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mainHandler.post(new Runnable() {
                @Override
                public void run() {

                    listAdapter.notifyDataSetChanged();

                }
            });

        }
    }


}





