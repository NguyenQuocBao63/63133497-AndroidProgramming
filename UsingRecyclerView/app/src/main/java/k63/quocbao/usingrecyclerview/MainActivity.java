package k63.quocbao.usingrecyclerview;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    LandScapeAdapter landScapeAdapter;
    ArrayList<LandScape> recycleViewDatas;
    RecyclerView recyclerViewlandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //3
        recycleViewDatas = getDataForRecyclerView();
        //4
        recyclerViewlandscape = findViewById(R.id.recycleLand);
        //5
        RecyclerView.LayoutManager layoutLinear =new LinearLayoutManager(this);
        recyclerViewlandscape.setLayoutManager(layoutLinear);
        //6
        landScapeAdapter = new LandScapeAdapter(this,recycleViewDatas);
        //7
        recyclerViewlandscape.setAdapter(landScapeAdapter);
    }
    ArrayList<LandScape> getDataForRecyclerView(){
        ArrayList<LandScape> dsDuLieu = new ArrayList<LandScape>();
        LandScape landScape1 = new LandScape("flag_tower_of_hanoi","Cot_co_Ha+noi");
        dsDuLieu.add(landScape1);
        dsDuLieu.add(new LandScape("effel","Thap Effel"));
        dsDuLieu.add(new LandScape("buckingham","Cung dien BuckingHam"));
        dsDuLieu.add(new LandScape("statue_of_liberty","Tuong Nu Than Tu Do"));
        return dsDuLieu;


    }
}