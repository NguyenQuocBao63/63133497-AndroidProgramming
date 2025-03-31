package vn.nguyenquocbao.a63133497_nguyenquocbao_tgk;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityChucNang2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuc_nang2);

        // Khai báo các thành phần giao diện
        EditText edtQT = findViewById(R.id.edtQT);
        EditText edtGK = findViewById(R.id.edtGK);
        EditText edtCK = findViewById(R.id.edtCK);
        Button btnCalculate = findViewById(R.id.btnCalculate);
        TextView txtResult = findViewById(R.id.txtResult);

        // Xử lý sự kiện khi nhấn nút tính toán
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Lấy giá trị nhập vào
                    float qt = Float.parseFloat(edtQT.getText().toString());
                    float gk = Float.parseFloat(edtGK.getText().toString());
                    float ck = Float.parseFloat(edtCK.getText().toString());

                    // Tính điểm trung bình
                    float result = (qt * 0.2f) + (gk * 0.3f) + (ck * 0.5f);
                    txtResult.setText("Điểm trung bình: " + result);
                } catch (NumberFormatException e) {
                    txtResult.setText("Vui lòng nhập số hợp lệ!");
                }
            }
        });
    }
}