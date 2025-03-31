package vn.nguyenquocbao.thigk_luyentap;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class ActivityCau3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cau3);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> foodList = new ArrayList<>();
        foodList.add("Phở bò");
        foodList.add("Bún chả");
        foodList.add("Cơm tấm");
        foodList.add("Gỏi cuốn");
        foodList.add("Bánh xèo");
        foodList.add("Mì Quảng");
        foodList.add("Bánh mì");
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(foodList);
        recyclerView.setAdapter(adapter);
    }
}