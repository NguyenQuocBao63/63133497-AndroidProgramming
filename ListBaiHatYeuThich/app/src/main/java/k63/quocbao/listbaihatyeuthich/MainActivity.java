package k63.quocbao.listbaihatyeuthich;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Hien thi du lieu len Listview

        //B1 Chuan bi nguon du lieu (co the tao san(hardcode)/ lay tu tep/ csdl)
        ArrayList<String> nguonDuLieu =new ArrayList<String>();
        nguonDuLieu.add("Noi nay co anh");
        nguonDuLieu.add("Yeu voi vang");
        nguonDuLieu.add("Dau mua");
        nguonDuLieu.add("Con co be be");
        nguonDuLieu.add("Ngao ngo");

        //B2 Tim tham chieu den ListView
         ListView listViewBH =  (ListView)  findViewById(R.id.lvDSbaihat);

         //B3 Tao addapter
        //3.1 gan voi nguon
        ArrayAdapter<String> baihat_Adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1,
                                                nguonDuLieu);
        //B4 Gan/nap du lieu tu nguon vao ListView
        listViewBH.setAdapter(baihat_Adapter);

        //xu li them
        listViewBH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // i la vi tri cua item duoc chon/click tren ListView
                //lay gia tri cua Item vua cham
                String value = baihat_Adapter.getItem(i);
                // xu ly khac theo yeu cau
                //vidu
                Toast.makeText(MainActivity.this,value, Toast.LENGTH_LONG).show();
            }
        });
    }
}