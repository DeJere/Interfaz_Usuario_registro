package com.example.practic_intento_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;

public class ActividadLogin extends AppCompatActivity {
    // Declaración de vistas
    private TextInputEditText etUsuario;
    private TextInputEditText etClave;
    private Button buttonIngresar;
    private TextInputLayout tilUsuario;
    private TextInputLayout tilClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this); // A menudo no es necesario para layouts simples
        setContentView(R.layout.activity_actividad_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicialización de vistas con los IDs correctos del XML
        // Los TextInputEditText están DENTRO de los TextInputLayout
        tilUsuario = findViewById(R.id.txtUsuario);
        etUsuario = (TextInputEditText) tilUsuario.getEditText();

        tilClave = findViewById(R.id.txtclave);
        etClave = (TextInputEditText) tilClave.getEditText();

        buttonIngresar = findViewById(R.id.btnsesion);

        // Configurar el listener para el botón
        buttonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btIngresar(v);
            }
        });
    }

    // ... (resto de tu clase ActividadLogin)

    // Mtodo para manejar el clic del botón de ingreso
    public void btIngresar(View v) {
        String usuario = etUsuario.getText().toString();
        String clave = etClave.getText().toString();

        boolean esValido = true;

        // Validación de campos
        if (usuario.isEmpty()) {
            tilUsuario.setError("Nombre de usuario requerido");
            esValido = false;
        } else {
            tilUsuario.setError(null);
        }

        if (clave.isEmpty()) {
            tilClave.setError("Clave requerida");
            esValido = false;
        } else {
            tilClave.setError(null);
        }

        if (esValido) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://revistas.uteq.edu.ec/ws/login.php?usr=" + usuario + "&pass=" + clave;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                              String trimmedResponse = response.trim();
                              if (trimmedResponse.contains("Login Correcto")) {
                                Toast.makeText(ActividadLogin.this, "Login Exitoso", Toast.LENGTH_SHORT).show();

                                // Navegar a la actividad principal (MainActivity)
                                Intent intent = new Intent(ActividadLogin.this, MainActivity.class);
                                startActivity(intent);
                                finish(); // Cierra la actividad de login

                            } else {
                                Toast.makeText(ActividadLogin.this, "Usuario o clave incorrectos", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ActividadLogin.this, "Error de conexión: " + error.toString(), Toast.LENGTH_LONG).show();
                }
            });

            queue.add(stringRequest);
        }
    }



}
