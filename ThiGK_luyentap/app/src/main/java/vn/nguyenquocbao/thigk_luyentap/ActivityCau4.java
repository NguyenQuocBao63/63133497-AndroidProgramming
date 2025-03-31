package vn.nguyenquocbao.thigk_luyentap;

import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityCau4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cau4);
        TextView txtFullName = findViewById(R.id.txtFullName);
        TextView txtBirthDate = findViewById(R.id.txtBirthDate);
        TextView txtJob = findViewById(R.id.txtJob);
        TextView txtAddress = findViewById(R.id.txtAddress);
        TextView txtPhone = findViewById(R.id.txtPhone);
        TextView txtID = findViewById(R.id.txtID);

        txtFullName.setText("Họ và tên: Nguyễn Quốc Bảo ");
        txtBirthDate.setText("Ngày sinh: 18/01/2003");
        txtJob.setText("Nghề nghiệp: Sinh Viên");
        txtAddress.setText("Địa chỉ liên hệ: 19 Đường Lang Liêu, P.Vĩnh Phước, TP.Nha Trang");
        txtPhone.setText("Số điện thoại: 093 533 0602");
        txtID.setText("Số CCCD: 054203001797");
    }
}