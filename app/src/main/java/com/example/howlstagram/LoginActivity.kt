package com.example.howlstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.howlstagram.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var auth:FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        val email_login_button=binding.emailLoginButton

        email_login_button.setOnClickListener {
            signinAndSignup()
        }


    }

    fun signinAndSignup(){
        val email_edittext = binding.emailEdittext
        val password_edittext=binding.passwordEdittext
        auth?.createUserWithEmailAndPassword(email_edittext.toString().trim(),
            password_edittext.toString())?.addOnCompleteListener {
                task ->
                    if(task.isSuccessful){
                        //아이디가 생성되었을 때의 코드
                        moveMainPage(task.result.user)
                    }
                    else if(task.exception?.message.isNullOrEmpty()) {

                        //로그인 에러 메세지
                        Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
                    }
            else{
                // 로그인하도록
                signinEmail()
                    }
        }
    }

    fun signinEmail(){
        val email_edittext = binding.emailEdittext
        val password_edittext=binding.passwordEdittext


        auth?.createUserWithEmailAndPassword(email_edittext.toString(),
            password_edittext.toString())?.addOnCompleteListener {
                task ->
            if(task.isSuccessful){
                //아이디와 비번이 맞았을 때
                moveMainPage(task.result.user)

            }
            else{
                // 틀렸을 때
                Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()

            }
        }
    }//signinEmail

    fun moveMainPage(user:FirebaseUser?){
        if(user!=null){
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}