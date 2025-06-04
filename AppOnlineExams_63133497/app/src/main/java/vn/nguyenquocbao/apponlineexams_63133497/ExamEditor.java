package vn.nguyenquocbao.apponlineexams_63133497;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager; // Import LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ExamEditor extends AppCompatActivity {

    // Sử dụng biến non-static cho dữ liệu để quản lý trạng thái tốt hơn
    private ArrayList<Question> data;
    private RecyclerView listview;
    private CustomAdapter adapter;
    private int quizID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exam_editor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle b = getIntent().getExtras();
        String quizTitle = b.getString("Quiz Title");

        TextView title = findViewById(R.id.title);
        title.setText(quizTitle);

        Button submit = findViewById(R.id.submit);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Quizzes").hasChild("Last ID")){
                    String lID = snapshot.child("Quizzes").child("Last ID").getValue().toString();
                    quizID = Integer.parseInt(lID)+1;
                }else {
                    quizID = 100000;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ExamEditor.this, "Can't Connect",Toast.LENGTH_SHORT).show();
            }
        };
        database.addValueEventListener(listener);
        data = new ArrayList<>();
        data.add(new Question());

        listview = findViewById(R.id.listview);
        listview.setLayoutManager(new LinearLayoutManager(this));

        CustomAdapter customAdapter = new CustomAdapter(data);
        listview.setAdapter(customAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(listview);

        submit.setOnClickListener(v -> {
            DatabaseReference ref = database.child("Quizzes");
            ref.child("Last ID").setValue(quizID);
            ref.child(String.valueOf(quizID)).child("Titile").setValue(quizTitle);
            ref.child(String.valueOf(quizID)).child("Total Questions").setValue(data.size());
            DatabaseReference qRef = ref.child(String.valueOf(quizID)).child("Questions");
            for (int i = 0; i<data.size();i++){
                String p = String.valueOf(i);
                qRef.child(p).child("Question").setValue(data.get(i).getQuestion());
                qRef.child(p).child("Option 1").setValue(data.get(i).getOption1());
                qRef.child(p).child("Option 2").setValue(data.get(i).getOption2());
                qRef.child(p).child("Option 3").setValue(data.get(i).getOption3());
                qRef.child(p).child("Option 4").setValue(data.get(i).getOption4());
                qRef.child(p).child("Ans").setValue(data.get(i).getCorrectAnswer());
            }
            database.child("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("Quizzes Created")
                    .child(String.valueOf(quizID)).setValue("");

            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Quiz ID",String.valueOf(quizID));
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this,"Your quiz id:"+quizID+" copied to clipboard",
                    Toast.LENGTH_SHORT).show();
            finish();
        });
    }


    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {
            int position_dragged = dragged.getAdapterPosition();
            int position_target = target.getAdapterPosition();

            // Hoán đổi vị trí trong danh sách dữ liệu
            Collections.swap(data, position_dragged, position_target);
            // Thông báo cho adapter rằng các item đã được di chuyển
            adapter.notifyItemMoved(position_dragged, position_target);
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    // CustomAdapter cho RecyclerView
    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private final ArrayList<Question> arr;

        public CustomAdapter(ArrayList<Question> data) {
            this.arr = data;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            private final EditText question;
            private final RadioButton option1rb;
            private final RadioButton option2rb;
            private final RadioButton option3rb;
            private final RadioButton option4rb;
            private final EditText option1et;
            private final EditText option2et;
            private final EditText option3et;
            private final EditText option4et;
            private final LinearLayout new_question_container;
            private final RadioGroup radio_group;

            public ViewHolder(View view) {
                super(view);
                question = view.findViewById(R.id.question);
                option1rb = view.findViewById(R.id.option1rb);
                option2rb = view.findViewById(R.id.option2rb);
                option3rb = view.findViewById(R.id.option3rb);
                option4rb = view.findViewById(R.id.option4rb);
                option1et = view.findViewById(R.id.option1et);
                option2et = view.findViewById(R.id.option2et);
                option3et = view.findViewById(R.id.option3et);
                option4et = view.findViewById(R.id.option4et);
                new_question_container = view.findViewById(R.id.new_question);
                radio_group = view.findViewById(R.id.radio_group);


                question.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        int currentPosition = getAdapterPosition();
                        if (currentPosition != RecyclerView.NO_POSITION) {
                            arr.get(currentPosition).setQuestion(charSequence.toString());
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {}
                });

                option1et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        int currentPosition = getAdapterPosition();
                        if (currentPosition != RecyclerView.NO_POSITION) {
                            arr.get(currentPosition).setOption1(charSequence.toString());
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {}
                });

                option2et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        int currentPosition = getAdapterPosition();
                        if (currentPosition != RecyclerView.NO_POSITION) {
                            arr.get(currentPosition).setOption2(charSequence.toString());
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {}
                });

                option3et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        int currentPosition = getAdapterPosition();
                        if (currentPosition != RecyclerView.NO_POSITION) {
                            arr.get(currentPosition).setOption3(charSequence.toString());
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {}
                });

                option4et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        int currentPosition = getAdapterPosition();
                        if (currentPosition != RecyclerView.NO_POSITION) {
                            arr.get(currentPosition).setOption4(charSequence.toString());
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {}
                });

                radio_group.setOnCheckedChangeListener((group, checkedId) -> {
                    int currentPosition = getAdapterPosition();
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        if (option1rb.isChecked()) arr.get(currentPosition).setCorrectAnswer(1);
                        else if (option2rb.isChecked()) arr.get(currentPosition).setCorrectAnswer(2);
                        else if (option3rb.isChecked()) arr.get(currentPosition).setCorrectAnswer(3);
                        else if (option4rb.isChecked()) arr.get(currentPosition).setCorrectAnswer(4);
                    }
                });


                new_question_container.setOnClickListener(v -> {
                    int currentPosition = getAdapterPosition();
                    if (currentPosition != RecyclerView.NO_POSITION && currentPosition == arr.size() - 1) {
                        arr.add(new Question());
                        // Sử dụng notifyItemInserted để cập nhật hiệu quả
                        adapter.notifyItemInserted(arr.size() - 1);
                        // Cuộn xuống item mới thêm vào (tùy chọn)
                        listview.scrollToPosition(arr.size() - 1);
                    }
                });
            }

            // Các getter methods
            public EditText getQuestion() { return question; }
            public RadioButton getOption1rb() { return option1rb; }
            public RadioButton getOption2rb() { return option2rb; }
            public RadioButton getOption3rb() { return option3rb; }
            public RadioButton getOption4rb() { return option4rb; }
            public EditText getOption1et() { return option1et; }
            public EditText getOption2et() { return option2et; }
            public EditText getOption3et() { return option3et; }
            public EditText getOption4et() { return option4et; }
            public LinearLayout getNew_question_container() { return new_question_container; }
            public RadioGroup getRadio_group() { return radio_group; }
        }

        @NonNull
        @Override
        public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.question_edit, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
            // KHÔNG sử dụng holder.setIsRecyclable(false);
            // Nó làm mất đi lợi ích của RecyclerView

            // Lấy đối tượng Question tại vị trí hiện tại
            Question currentQuestion = arr.get(position);

            // Đặt dữ liệu vào các View
            holder.getQuestion().setText(currentQuestion.getQuestion());
            holder.getOption1et().setText(currentQuestion.getOption1());
            holder.getOption2et().setText(currentQuestion.getOption2());
            holder.getOption3et().setText(currentQuestion.getOption3());
            holder.getOption4et().setText(currentQuestion.getOption4());

            // Đặt trạng thái RadioButton dựa trên correctAnswer
            // Đảm bảo rằng bạn reset trạng thái checked trước khi đặt
            holder.getRadio_group().clearCheck(); // Xóa lựa chọn cũ trước
            switch (currentQuestion.getCorrectAnswer()) {
                case 1:
                    holder.getOption1rb().setChecked(true);
                    break;
                case 2:
                    holder.getOption2rb().setChecked(true);
                    break;
                case 3:
                    holder.getOption3rb().setChecked(true);
                    break;
                case 4:
                    holder.getOption4rb().setChecked(true);
                    break;
            }

            // Chỉ hiển thị nút "Thêm câu hỏi mới" ở item cuối cùng
            if (position == arr.size() - 1) {
                holder.getNew_question_container().setVisibility(View.VISIBLE);
            } else {
                holder.getNew_question_container().setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return arr.size();
        }
    }

    // Bạn cần có một lớp Question để lưu trữ dữ liệu câu hỏi
    // Ví dụ đơn giản về lớp Question:
    public static class Question {
        private String question;
        private String option1;
        private String option2;
        private String option3;
        private String option4;
        private int correctAnswer; // 1, 2, 3, 4

        public Question() {
            // Constructor mặc định, có thể khởi tạo giá trị ban đầu
            this.question = "";
            this.option1 = "";
            this.option2 = "";
            this.option3 = "";
            this.option4 = "";
            this.correctAnswer = 0; // Không có đáp án nào được chọn ban đầu
        }

        // Getters
        public String getQuestion() { return question; }
        public String getOption1() { return option1; }
        public String getOption2() { return option2; }
        public String getOption3() { return option3; }
        public String getOption4() { return option4; }
        public int getCorrectAnswer() { return correctAnswer; }

        // Setters
        public void setQuestion(String question) { this.question = question; }
        public void setOption1(String option1) { this.option1 = option1; }
        public void setOption2(String option2) { this.option2 = option2; }
        public void setOption3(String option3) { this.option3 = option3; }
        public void setOption4(String option4) { this.option4 = option4; }
        public void setCorrectAnswer(int correctAnswer) { this.correctAnswer = correctAnswer; }
    }
}
