package k63.quocbao.ex3_simplesumapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Gắn layout tương ứng với file này
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    //Đây l bộ lăắng nghe va xu ly su kien click tren nut tinh tong

    public void XuLyCong(View view)
    {
        //Tim , tham chieu den dieu khien tren tap XML, mapping sang java file
        EditText  editTextSoA = findViewById(R.id.edtA);
        EditText   editTextSoB = findViewById(R.id.edtB);
        EditText   editTextKetQua = findViewById(R.id.edtKQ);
        //lay du lieu ve o dieu khien so a
        String strA=  editTextSoA.getText().toString();     //strA ="2"
        //lay du lieu ve o dieu khien so b
        String strB=  editTextSoB.getText().toString();     //strB ="4"

        //Chuyen du lieu sang dang so
        int so_A =   Integer.parseInt(strA);    //2
        int so_B =   Integer.parseInt(strB);    //4
        //Tinh toan theo yeu cau
        int tong =  so_A + so_B;    //6
        String strTong = String.valueOf(tong);   //chuyen sang dang chuoi; "6"

        // Hien ra man hinh
        editTextKetQua.setText((strTong));


    }
}