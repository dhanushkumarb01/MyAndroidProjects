package com.example.unitconverter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private Spinner fromUnitSpinner, toUnitSpinner;
    private EditText fromValueEditText;
    private TextView resultTextView;
    private Button convertButton;

    private String[] units;
    private UnitConverter unitConverter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply theme before super.onCreate
        ThemeManager.applyTheme(ThemeManager.isDarkModeEnabled(this));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the UnitConverter
        unitConverter = new UnitConverter();

        // Find views
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner);
        toUnitSpinner = findViewById(R.id.toUnitSpinner);
        fromValueEditText = findViewById(R.id.fromValueEditText);
        resultTextView = findViewById(R.id.resultTextView);
        convertButton = findViewById(R.id.convertButton);

        // Get units from resources
        units = getResources().getStringArray(R.array.length_units);

        // Create adapter for spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set adapters to spinners
        fromUnitSpinner.setAdapter(adapter);
        toUnitSpinner.setAdapter(adapter);

        // Set default selections
        fromUnitSpinner.setSelection(0); // Feet
        toUnitSpinner.setSelection(2); // Centimeters

        // Set click listener for convert button
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertUnits();
            }
        });

        // Set listeners for spinners
        fromUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertUnits();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        toUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertUnits();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void convertUnits() {
        String fromValueString = fromValueEditText.getText().toString();

        if (TextUtils.isEmpty(fromValueString)) {
            resultTextView.setText("Result will appear here");
            return;
        }

        try {
            double fromValue = Double.parseDouble(fromValueString);
            String fromUnit = units[fromUnitSpinner.getSelectedItemPosition()];
            String toUnit = units[toUnitSpinner.getSelectedItemPosition()];

            double result = unitConverter.convert(fromValue, fromUnit, toUnit);

            // Format the result to avoid excessive decimal places
            DecimalFormat df = new DecimalFormat("#.#####");
            String formattedResult = df.format(result);

            // Display the result
            resultTextView.setText(fromValueString + " " + fromUnit + " = " + formattedResult + " " + toUnit);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}