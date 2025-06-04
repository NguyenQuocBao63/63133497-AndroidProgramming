package vn.nguyenquocbao.apponlineexams_63133497;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText first_name = findViewById(R.id.first_name);
        EditText last_name = findViewById(R.id.last_name);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText confirm_password = findViewById(R.id.confirm_password);
        Button signup = findViewById(R.id.signup);
        TextView login = findViewById(R.id.login);

        signup.setOnClickListener(view ->{
            ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Thread thread = new Thread(() -> {
                String pass = password.getText().toString();
                String confirmPass = confirm_password.getText().toString();
                String em = email.getText().toString();
                String firstName = first_name.getText().toString();
                String lastName = last_name.getText().toString();
                if(!pass.equals(confirmPass)){
                    runOnUiThread(() -> {
                        confirm_password.setError("Password doesn't match");
                        progressDialog.dismiss();
                    });
                    return;
                }
                auth.createUserWithEmailAndPassword(em, pass).addOnCompleteListener(SignUp.this, (OnCompleteListener<AuthResult>) task -> {
                    if (task.isSuccessful()){
                        FirebaseUser user = auth.getCurrentUser();
                        DatabaseReference ref = database.child("Users").child(user.getUid());
                        ref.child("First Name").setValue(firstName);
                        ref.child("Last Name").setValue(lastName);
                        runOnUiThread(() -> {
                            progressDialog.dismiss();
                            Intent i = new Intent(SignUp.this, Home.class);
                            i.putExtra("User UID", user.getUid());
                            startActivity(i);
                            finish();
                        });
                    }else {
                        Toast.makeText(SignUp.this, "Operation Failed", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                });
            });
            thread.start();
        });

        login.setOnClickListener(view -> {
            Intent i = new Intent(SignUp.this, MainActivity.class);
            startActivity(i);

        });


    }
}