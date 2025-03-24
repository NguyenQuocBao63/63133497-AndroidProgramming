package k63.quocbao.appmonan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Tim listview
        ListView lsDSMonAn = (ListView) findViewById(R.id.lvDSMonAn);
        // chuan bi nguon du lieu
        ArrayList<MonAn> dsMonAn = new ArrayList<MonAn>();
        MonAn m1 new MonAn("Com Tam Suon", 25000,"Mo ta o day", R.drawable.cts);
        

    }

}