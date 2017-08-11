package com.neurosky.algo_sdk_sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText inputEmail;   //El texto en donde el usario ingresa el email ya dado para otorgarle informacion
    //sobre el cambio de su password
    private Button btnReset, btnBack;   //EL boton que envia al correo dado la liga para cambiar su contraseña
    //Y el boton para regresar a la pantalla de inicio de LogIn
    private FirebaseAuth auth;          //Un objeto de tipo Firebase-auth (firebase autentification)
    private ProgressBar progressBar;    //El circlo girando que aparece en la parte inferior de la pantalla

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //Localiza los inputs y los botones que se utilizaran en esta actividad
        inputEmail = (EditText) findViewById(R.id.email);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnBack = (Button) findViewById(R.id.btn_back);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //Inicia la instancia del objeto de tipo FirebaseAuth
        auth = FirebaseAuth.getInstance();

        //al dar click en el boton back, esta actividad se da por terminada y regresa a la anterior
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Esta parte realiza TODA la logica necesaria para hacer un cambio de password exitoso
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Lee el texto(email) como un input, transformandolo a un string.
                //El .trim es un metodo que devuelve la cadena de caracteres sin espacios en blanco, es decir pone
                //el conjunto de string como uno solo sin espacios.
                String email = inputEmail.getText().toString().trim();

                //Si el usuario no ha dado un email se le envia el mensaje de que ingrese su email.
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Al dar click en el boton de reset password el circulo empieza a girar en al parte inferior.
                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //Si el correo ya habia sido autentificado, entonces envia mensaje de que fue exitoso el cambio.
                                    Toast.makeText(ResetPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    //Si el correo no habia sido autentificado, entonces envia un mensaje de que nof ue exitoso el cambio.
                                    Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }

}

