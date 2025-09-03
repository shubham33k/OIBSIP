package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    TextView tvInput;
    String input = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInput = findViewById(R.id.tvInput);

        int[] numberIds = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
                R.id.btn8, R.id.btn9, R.id.btnDot};

        int[] operatorIds = {R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide};

        // Numbers
        View.OnClickListener numberClick = v -> {
            Button b = (Button) v;
            input += b.getText().toString();
            tvInput.setText(input);
        };

        for (int id : numberIds) {
            findViewById(id).setOnClickListener(numberClick);
        }

        // Operators
        View.OnClickListener operatorClick = v -> {
            Button b = (Button) v;
            input += " " + b.getText().toString() + " ";
            tvInput.setText(input);
        };

        for (int id : operatorIds) {
            findViewById(id).setOnClickListener(operatorClick);
        }

        // Equal button
        findViewById(R.id.btnEqual).setOnClickListener(v -> {
            try {
                double result = evaluateExpression(input);
                tvInput.setText(String.valueOf(result));
                input = String.valueOf(result);
            } catch (Exception e) {
                tvInput.setText("Error");
                input = "";
            }
        });

        // Clear button
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            input = "";
            tvInput.setText("");
        });
    }

    // Function to evaluate expression
    private double evaluateExpression(String expression) {
        String[] tokens = expression.split(" ");
        Stack<Double> values = new Stack<>();
        Stack<String> ops = new Stack<>();

        for (String token : tokens) {
            if (token.isEmpty()) continue;
            if (token.matches("[0-9.]+")) {
                values.push(Double.parseDouble(token));
            } else if (token.matches("[+\\-*/]")) {
                while (!ops.isEmpty() && hasPrecedence(token, ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(token);
            }
        }
        while (!ops.isEmpty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }
        return values.pop();
    }

    private boolean hasPrecedence(String op1, String op2) {
        if ((op1.equals("*") || op1.equals("/")) && (op2.equals("+") || op2.equals("-"))) {
            return false;
        } else {
            return true;
        }
    }

    private double applyOp(String op, double b, double a) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": if (b == 0) return 0; else return a / b;
        }
        return 0;
    }
}
