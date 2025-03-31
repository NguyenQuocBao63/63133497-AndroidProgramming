package vn.nguyenquocbao.thigk_luyentap;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ActivityCau2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cau2);
        ListView listView = findViewById(R.id.listView);
        ArrayList<String> songList = new ArrayList<>();

        songList.add("Hãy Trao Cho Anh - Sơn Tùng M-TP");
        songList.add("Em Gái Mưa - Hương Tràm");
        songList.add("Nơi Này Có Anh - Sơn Tùng M-TP");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songList);
        listView.setAdapter(adapter);
    }
}