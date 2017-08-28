package com.quiz.series.tvseriesquiz.model.datastore.firebase;

/**
 * Created by Emilio on 30/06/2016.
 */

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class ADFirebaseAuth implements ADFirebaseAuthInterface {

    //private Callback callback;

    public static FirebaseAuth auth;
    //public static FirebaseUser user;

    //private FirebaseAuth.AuthStateListener mAuthListener;


    public ADFirebaseAuth() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        /*
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(ADConstants.APPNAME, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(ADConstants.APPNAME, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        */
    }

    public static void connect() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();

            /*
            auth.signInWithEmailAndPassword(ADFirebaseUser.EMAIL, ADFirebaseUser.PASS)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Log.e(ADConstants.APPNAME, "Authentication failed.");
                            } else {
                                MyApp.user.setUser(FirebaseAuth.getInstance().getCurrentUser());
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(ADConstants.APPNAME, "Authentication failed.");
                        }
                    });
               */
        }
    }

    @Override
    public void authAnonymously(final Callback callback) {
        auth.signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            callback.onError("Authentication failed.");
                        } else {
                            callback.onSuccess("");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void authCustomToken(final Callback callback) {
        String token = "c09bd5a4-2ed4-4898-989a-8d9983c436ac";

        auth.signInWithCustomToken(token)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            callback.onError("Authentication failed.");
                        } else {
                            callback.onSuccess("");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e.getLocalizedMessage());
                    }
                });
    }

    public void authWithPassword(final String email, final String password, final Callback callback) {
        //auth.addAuthStateListener(mAuthListener);

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            callback.onError("Authentication failed.");
                        } else {
                            callback.onSuccess(task.getResult().getUser().getUid());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e.getLocalizedMessage());
                    }
                });

    }
}
