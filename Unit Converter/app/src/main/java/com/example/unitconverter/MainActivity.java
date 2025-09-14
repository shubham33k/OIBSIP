package com.example.unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText inputValue;
    Spinner categorySpinner, fromSpinner, toSpinner;
    Button convertButton;
    TextView resultText;

    ArrayAdapter<CharSequence> lengthAdapter, weightAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.inputValue);
        categorySpinner = findViewById(R.id.categorySpinner);
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        lengthAdapter = ArrayAdapter.createFromResource(this, R.array.length_units, android.R.layout.simple_spinner_item);
        lengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        weightAdapter = ArrayAdapter.createFromResource(this, R.array.weight_units, android.R.layout.simple_spinner_item);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) { // Length
                    fromSpinner.setAdapter(lengthAdapter);
                    toSpinner.setAdapter(lengthAdapter);
                } else { // Weight
                    fromSpinner.setAdapter(weightAdapter);
                    toSpinner.setAdapter(weightAdapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        convertButton.setOnClickListener(v -> {
            String input = inputValue.getText().toString();
            if (input.isEmpty()) {
                resultText.setText("Please enter a value");
                return;
            }

            double value = Double.parseDouble(input);
            String category = categorySpinner.getSelectedItem().toString();
            String fromUnit = fromSpinner.getSelectedItem().toString();
            String toUnit = toSpinner.getSelectedItem().toString();

            double result = convert(value, category, fromUnit, toUnit);
            resultText.setText("Result: " + result + " " + toUnit);
        });
    }

    private double convert(double value, String category, String fromUnit, String toUnit) {
        if (category.equals("Length")) {
            if (fromUnit.equals("Centimeters") && toUnit.equals("Meters")) return value / 100;
            if (fromUnit.equals("Meters") && toUnit.equals("Centimeters")) return value * 100;
        } else if (category.equals("Weight")) {
            if (fromUnit.equals("Grams") && toUnit.equals("Kilograms")) return value / 1000;
            if (fromUnit.equals("Kilograms") && toUnit.equals("Grams")) return value * 1000;
        }
        return value; // Same unit case
    }
}
