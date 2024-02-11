package com.example.simple_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener{
    TextView resultTV, solutionTV;
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonDivide, buttonPlus, buttonMinus, buttonMultiply, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot, buttonResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        resultTV = (TextView) findViewById(R.id.result_tv);
        solutionTV = (TextView) findViewById(R.id.solution_tv);
        buttonResult = (MaterialButton) findViewById(R.id.button_result);

        assignId(buttonResult, R.id.button_result);
        assignId(buttonC, R.id.button_c);
        assignId(buttonBrackClose, R.id.button_close_bracket);
        assignId(buttonBrackOpen, R.id.button_open_bracket);
        assignId(buttonDivide, R.id.button_divide);
        assignId(buttonMultiply, R.id.button_multiply);
        assignId(buttonPlus, R.id.button_plus);
        assignId(buttonMinus, R.id.button_minus);
        assignId(buttonEquals, R.id.button_equals);
        assignId(button0, R.id.button_0);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);
        assignId(buttonAC, R.id.button_ac);
        assignId(buttonDot, R.id.button_dot);
    }

    private void assignId(MaterialButton btn, int id){
        btn = (MaterialButton) findViewById(id);
        btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        MaterialButton button = (MaterialButton) v;
        String buttonText = button.getText().toString();
        String dataToCalculate = resultTV.getText().toString();

        if(buttonText.equals("C")){
            solutionTV.setText("0");
            resultTV.setText("");
            return;
        }
        if(buttonText.equals("=")){
            resultTV.setText(solutionTV.getText());
            buttonResult.setText(solutionTV.getText());
            return;
        }
        if(v == buttonResult){
            if(buttonText.equals("0"))
                resultTV.setText("");
            else
                resultTV.setText(buttonResult.getText().toString());
            return;
        }
        if(buttonText.equals("AC")){
            if(dataToCalculate.length() > 0) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
            else {
                resultTV.setText("");
            }
        }
        else{
            dataToCalculate += buttonText; //Объединение чисел в калькуляторе
        }

        resultTV.setText(dataToCalculate);
        String finalResult = getResult(dataToCalculate);
        if(!finalResult.equals("Error") && dataToCalculate.length() > 0){
            solutionTV.setText(finalResult);
        }
        else{
            solutionTV.setText("");
        }
    }

    private String getResult(String data){
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "JavaScript", 1, null).toString();

            if(finalResult.endsWith(".0"))
                finalResult = finalResult.replace(".0", "");
            return finalResult;
        }
        catch (Exception e){
            return "Error";
        }
    }
}