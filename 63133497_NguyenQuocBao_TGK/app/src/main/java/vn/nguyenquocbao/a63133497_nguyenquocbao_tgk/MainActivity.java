package vn.nguyenquocbao.a63133497_nguyenquocbao_tgk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khai báo các button
        Button ActivityChucNang2 = findViewById(R.id.btnFunction2);
        Button ActivityChucNang3 = findViewById(R.id.btnFunction3);
        Button ActivityChucNang4 = findViewById(R.id.btnFunction4);
        Button btnAboutMe = findViewById(R.id.btnAboutMe);
        Button ActivityChucNang5 = findViewById(R.id.btnExtra);

        // Xử lý sự kiện chuyển màn hình
        ActivityChucNang2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityChucNang2.class);
                startActivity(intent);
            }
        });

        ActivityChucNang3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityChucNang3.class);
                startActivity(intent);
            }
        });

        ActivityChucNang4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityChucNang4.class);
                startActivity(intent);
            }
        });

        btnAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityAboutMe.class);
                startActivity(intent);
            }
        });

        ActivityChucNang5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityChucNang5.class);
                startActivity(intent);
            }
        });
    }
}
