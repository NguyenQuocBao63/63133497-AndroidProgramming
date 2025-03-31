package vn.nguyenquocbao.a63133497_nguyenquocbao_tgk;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ActivityChucNang5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chuc_nang5);
        ListView listView = findViewById(R.id.listView);
        ArrayList<String> songList = new ArrayList<>();

        songList.add("Hãy Trao Cho Anh - Sơn Tùng M-TP");
        songList.add("Em Gái Mưa - Hương Tràm");
        songList.add("Nơi Này Có Anh - Sơn Tùng M-TP");
        songList.add("Cơn Mưa Ngang Qua - Sơn Tùng M-TP");
        songList.add("Có Chắc Yêu Là Đây - Sơn Tùng M-TP");
        songList.add("Tình Yêu Mang Theo - JayKii");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedSong = songList.get(position);
                Toast.makeText(ActivityChucNang5.this, "Bạn đã chọn: " + selectedSong, Toast.LENGTH_SHORT).show();
            }
        });
    }
}