package vn.nguyenquocbao.thigk_luyentap;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityCau1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cau1);
        EditText inputA = findViewById(R.id.inputA);
        EditText inputB = findViewById(R.id.inputB);
        Button btnSum = findViewById(R.id.btnSum);
        TextView txtResult = findViewById(R.id.txtResult);

        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aStr = inputA.getText().toString();
                String bStr = inputB.getText().toString();

                if (!aStr.isEmpty() && !bStr.isEmpty()) {
                    int a = Integer.parseInt(aStr);
                    int b = Integer.parseInt(bStr);
                    int sum = a + b;
                    txtResult.setText("Kết quả: " + sum);
                } else {
                    txtResult.setText("Vui lòng nhập đủ số");
                }
            }
        });
    }
}