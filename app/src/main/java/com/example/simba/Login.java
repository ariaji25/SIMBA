package com.example.simba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity {
    private Button login;
    private User user1 = new User();
    private FirebaseAuth userAuth;
    private GoogleSignInClient userSignin;
    private static final int RC_SIGN_IN = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        Log.w("SigIn", getString(R.string.default_web_client_id));
        userSignin = GoogleSignIn.getClient(this,gso);
        userAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.w("SigIn", ""+requestCode+": "+resultCode);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("SigIn", "Google sign in failed", e);

            }
        }
    }

    synchronized private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("SignIn", "firebaseAuthWithGoogle:" + account.getId());
        User.email = account.getEmail();
        User.nama = account.getDisplayName();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        userAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SigIn", "signInWithCredential:success");
                            FirebaseUser user = userAuth.getCurrentUser();
                            user1.checkIsAdded(Login.this);
                            final ProgressDialog dialog = ProgressDialog.show(Login.this, "Sign In",
                                    "Loading. Please wait...", true);
                            Thread t = new Thread(){
                                @Override
                                public void run() {
                                    super.run();
                                    try {
                                        while (User.keyDoc==null){
                                            sleep(2000);
                                        }
                                        if(User.keyDoc!=null){
                                            dialog.cancel();
                                            if(Dataasset.dataasset.size()!=0){
                                                Log.w("asset", "notEmpty");
                                                finished();
                                            }
                                        }

                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            t.start();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SigIn", "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.login), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });

    }
    private void signIn() {
        Intent signInIntent = userSignin.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    void finished(){
        this.finish();
    }

}
