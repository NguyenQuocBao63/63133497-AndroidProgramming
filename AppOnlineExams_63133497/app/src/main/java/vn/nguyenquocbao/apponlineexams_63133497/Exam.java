package vn.nguyenquocbao.apponlineexams_63133497;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log; // Thêm import này để sử dụng Log.d/e/w
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Exam extends AppCompatActivity {

    private Question[] data;
    private String quizID;
    private String uid;
    private int oldTotalPoints = 0;
    private int oldTotalQuestions = 0;
    private TextView title; // Khai báo global để có thể truy cập sau

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exam);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        quizID = getIntent().getStringExtra("Quiz ID");
        ListView listview = findViewById(R.id.listview);
        Button submit = findViewById(R.id.submit);
        title = findViewById(R.id.title); // Gán TextView title tại đây

        // Kiểm tra xem quizID có null không
        if (quizID == null || quizID.isEmpty()) {
            Toast.makeText(this, "Không có mã phòng được cung cấp.", Toast.LENGTH_LONG).show();
            finish(); // Đóng Activity nếu không có quiz ID
            return; // Thoát khỏi onCreate
        }

        // Kiểm tra FirebaseAuth.getInstance().getCurrentUser() có null không
        // Điều này có thể xảy ra nếu người dùng chưa đăng nhập hoặc phiên đã hết hạn
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(this, "Bạn cần đăng nhập để tham gia bài kiểm tra.", Toast.LENGTH_LONG).show();
            // Chuyển hướng người dùng đến màn hình đăng nhập
            startActivity(new Intent(Exam.this, SignUp.class)); // Giả sử SignUp là màn hình đăng nhập
            finish();
            return;
        }
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Kiểm tra xem node 'Quizzes' có tồn tại và có dữ liệu không
                if (snapshot.hasChild("Quizzes") && snapshot.child("Quizzes").hasChild(quizID)) {
                    DataSnapshot ref = snapshot.child("Quizzes").child(quizID);

                    // --- Sửa lỗi NullPointerException tại dòng 63 ---
                    // Kiểm tra và hiển thị Title
                    String quizTitle = "";
                    if (ref.child("Title").exists() && ref.child("Title").getValue() != null) {
                        quizTitle = ref.child("Title").getValue().toString();
                        title.setText(quizTitle);
                    } else {
                        Log.w("Exam", "Trường 'Title' cho Quiz ID " + quizID + " là null hoặc không tồn tại.");
                        title.setText("Bài kiểm tra không có tiêu đề"); // Đặt giá trị mặc định
                        Toast.makeText(Exam.this, "Lỗi dữ liệu: Tiêu đề bài kiểm tra không tìm thấy.", Toast.LENGTH_SHORT).show();
                        // Có thể cân nhắc finish() hoặc hiển thị cảnh báo rõ ràng hơn
                    }

                    // Kiểm tra và lấy Total Questions
                    int num = 0;
                    if (ref.child("Total Questions").exists() && ref.child("Total Questions").getValue() != null) {
                        try {
                            num = Integer.parseInt(ref.child("Total Questions").getValue().toString());
                        } catch (NumberFormatException e) {
                            Log.e("Exam", "Lỗi: 'Total Questions' không phải là số hợp lệ cho Quiz ID " + quizID + ": " + e.getMessage());
                            Toast.makeText(Exam.this, "Lỗi dữ liệu: Tổng số câu hỏi không hợp lệ.", Toast.LENGTH_SHORT).show();
                            finish(); // Thoát nếu không thể lấy số câu hỏi
                            return;
                        }
                    } else {
                        Log.e("Exam", "Trường 'Total Questions' cho Quiz ID " + quizID + " là null hoặc không tồn tại.");
                        Toast.makeText(Exam.this, "Lỗi dữ liệu: Tổng số câu hỏi không tìm thấy.", Toast.LENGTH_SHORT).show();
                        finish(); // Thoát nếu không thể lấy số câu hỏi
                        return;
                    }

                    if (num <= 0) {
                        Toast.makeText(Exam.this, "Bài kiểm tra không có câu hỏi nào.", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }

                    data = new Question[num];
                    for (int i = 0; i < num; i++) {
                        DataSnapshot qRef = ref.child("Questions").child(String.valueOf(i));
                        Question question = new Question();

                        // Kiểm tra và gán từng thuộc tính của Question
                        // Sử dụng phương thức getOrDefault để an toàn hơn
                        question.setQuestion(getStringOrDefault(qRef.child("Question"), "N/A"));
                        question.setOption1(getStringOrDefault(qRef.child("Option 1"), "N/A"));
                        question.setOption2(getStringOrDefault(qRef.child("Option 2"), "N/A"));
                        question.setOption3(getStringOrDefault(qRef.child("Option 3"), "N/A"));
                        question.setOption4(getStringOrDefault(qRef.child("Option 4"), "N/A"));

                        int ans = 0;
                        if (qRef.child("Ans").exists() && qRef.child("Ans").getValue() != null) {
                            try {
                                ans = Integer.parseInt(qRef.child("Ans").getValue().toString());
                            } catch (NumberFormatException e) {
                                Log.e("Exam", "Lỗi: 'Ans' không phải là số hợp lệ cho câu hỏi " + i + ": " + e.getMessage());
                                ans = 0; // Giá trị mặc định hoặc xử lý lỗi
                            }
                        } else {
                            Log.w("Exam", "Trường 'Ans' cho câu hỏi " + i + " là null hoặc không tồn tại.");
                        }
                        question.setCorrectAnswer(ans);
                        data[i] = question;
                    }

                    ListAdapter listAdapter = new ListAdapter(data);
                    listview.setAdapter(listAdapter);

                    // Lấy Total Points và Total Questions cũ của User
                    DataSnapshot userRef = snapshot.child("Users").child(uid);
                    if (userRef.hasChild("Total Points") && userRef.child("Total Points").getValue() != null) {
                        try {
                            oldTotalPoints = Integer.parseInt(userRef.child("Total Points").getValue().toString());
                        } catch (NumberFormatException e) {
                            Log.e("Exam", "Lỗi chuyển đổi Total Points cũ của User: " + e.getMessage());
                            oldTotalPoints = 0; // Đặt về 0 nếu lỗi
                        }
                    } else {
                        Log.d("Exam", "User chưa có Total Points.");
                    }

                    if (userRef.hasChild("Total Questions") && userRef.child("Total Questions").getValue() != null) {
                        try {
                            oldTotalQuestions = Integer.parseInt(userRef.child("Total Questions").getValue().toString());
                        } catch (NumberFormatException e) {
                            Log.e("Exam", "Lỗi chuyển đổi Total Questions cũ của User: " + e.getMessage());
                            oldTotalQuestions = 0; // Đặt về 0 nếu lỗi
                        }
                    } else {
                        Log.d("Exam", "User chưa có Total Questions.");
                    }

                } else {
                    // Nếu quizID không tồn tại trong "Quizzes"
                    Toast.makeText(Exam.this, "Mã phòng không tồn tại. Vui lòng kiểm tra lại.", Toast.LENGTH_LONG).show();
                    finish(); // Đóng Activity
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi khi kết nối Firebase bị hủy hoặc có sự cố
                Log.e("Exam", "Lỗi kết nối Firebase: " + error.getMessage(), error.toException());
                Toast.makeText(Exam.this, "Lỗi kết nối hoặc đọc dữ liệu Firebase.", Toast.LENGTH_SHORT).show();
                finish(); // Đóng Activity nếu không thể kết nối
            }
        };
        database.addValueEventListener(listener); // Sử dụng addValueEventListener để theo dõi thay đổi

        submit.setOnClickListener(v -> {
            if (data == null || data.length == 0) {
                Toast.makeText(Exam.this, "Bài kiểm tra chưa được tải. Không thể nộp.", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference ref = database.child("Quizzes").child(quizID)
                    .child("Answers").child(uid);
            int totalPoints = oldTotalPoints;
            int pointsEarnedInThisQuiz = 0; // Điểm đạt được trong bài quiz này

            for (int i = 0; i < data.length; i++) {
                // Đảm bảo data[i] không phải là null trước khi truy cập
                if (data[i] != null) {
                    ref.child(String.valueOf((i + 1))).setValue(data[i].getSelectedAnswer());
                    if (data[i].getSelectedAnswer() == data[i].getCorrectAnswer()) {
                        totalPoints++; // Tổng điểm toàn bộ
                        pointsEarnedInThisQuiz++; // Điểm chỉ cho bài quiz hiện tại
                    }
                } else {
                    Log.w("Exam", "Dữ liệu câu hỏi thứ " + i + " là null khi nộp.");
                }
            }
            ref.child("Points").setValue(pointsEarnedInThisQuiz); // Lưu điểm của bài quiz này

            int totalquestions = oldTotalQuestions + data.length;
            database.child("Users").child(uid).child("Total Points").setValue(totalPoints);
            database.child("Users").child(uid).child("Total Questions").setValue(totalquestions);
            database.child("Users").child(uid).child("Quizzes Solved").child(quizID).setValue(true); // Nên lưu boolean thay vì chuỗi rỗng

            Intent i = new Intent(Exam.this, Result.class);
            i.putExtra("Quiz ID", quizID);
            i.putExtra("PointsEarned", pointsEarnedInThisQuiz); // Truyền điểm đạt được
            i.putExtra("TotalQuestionsInQuiz", data.length); // Truyền tổng số câu hỏi trong quiz
            startActivity(i);
            finish();
        });

    }

    // Helper method để lấy String an toàn từ DataSnapshot
    private String getStringOrDefault(DataSnapshot snapshot, String defaultValue) {
        if (snapshot.exists() && snapshot.getValue() != null) {
            return snapshot.getValue().toString();
        }
        return defaultValue;
    }

    public class ListAdapter extends BaseAdapter {
        Question[] arr;

        ListAdapter(Question[] arr2) {
            arr = arr2;
        }

        @Override
        public int getCount() {
            if (arr == null) return 0; // Tránh NullPointerException nếu arr chưa được gán
            return arr.length;
        }

        @Override
        public Object getItem(int i) {
            return arr[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater inflater = getLayoutInflater();
            View v = view; // Tối ưu hóa: tái sử dụng view nếu có
            if (v == null) {
                v = inflater.inflate(R.layout.question, viewGroup, false); // viewGroup và false để inflate đúng cách
            }

            TextView question = v.findViewById(R.id.question);
            RadioButton option1 = v.findViewById(R.id.option1);
            RadioButton option2 = v.findViewById(R.id.option2);
            RadioButton option3 = v.findViewById(R.id.option3);
            RadioButton option4 = v.findViewById(R.id.option4);

            // Kiểm tra data[i] trước khi truy cập
            if (data != null && i < data.length && data[i] != null) {
                question.setText(data[i].getQuestion());
                option1.setText(data[i].getOption1());
                option2.setText(data[i].getOption2());
                option3.setText(data[i].getOption3());
                option4.setText(data[i].getOption4());

                // Sử dụng setOnCheckedChangeListener thay vì setOnClickListener cho RadioButton
                option1.setOnCheckedChangeListener(null); // Clear listener trước khi gán
                option2.setOnCheckedChangeListener(null);
                option3.setOnCheckedChangeListener(null);
                option4.setOnCheckedChangeListener(null);

                option1.setChecked(data[i].getSelectedAnswer() == 1);
                option2.setChecked(data[i].getSelectedAnswer() == 2);
                option3.setChecked(data[i].getSelectedAnswer() == 3);
                option4.setChecked(data[i].getSelectedAnswer() == 4);

                option1.setOnCheckedChangeListener((compoundButton, b) -> {
                    if (b) data[i].setSelectedAnswer(1);
                });
                option2.setOnCheckedChangeListener((compoundButton, b) -> {
                    if (b) data[i].setSelectedAnswer(2);
                });
                option3.setOnCheckedChangeListener((compoundButton, b) -> {
                    if (b) data[i].setSelectedAnswer(3);
                });
                option4.setOnCheckedChangeListener((compoundButton, b) -> {
                    if (b) data[i].setSelectedAnswer(4);
                });

                // Bạn đã có switch case ở cuối, có thể thay thế 4 dòng setChecked ở trên
                // switch (data[i].getSelectedAnswer()){
                //     case 1:
                //         option1.setChecked(true);
                //         break;
                //     case 2:
                //         option2.setChecked(true);
                //         break;
                //     case 3:
                //         option3.setChecked(true);
                //         break;
                //     case 4:
                //         option4.setChecked(true);
                //         break;
                // }
            } else {
                Log.e("ListAdapter", "Dữ liệu câu hỏi không hợp lệ tại vị trí: " + i);
                // Đặt văn bản mặc định hoặc ẩn view
                question.setText("Câu hỏi không tải được.");
                option1.setText("");
                option2.setText("");
                option3.setText("");
                option4.setText("");
                option1.setEnabled(false); // Vô hiệu hóa các radio button
                option2.setEnabled(false);
                option3.setEnabled(false);
                option4.setEnabled(false);
            }

            return v;
        }
    }
}