package com.yoji.radiobuttonrealisationwithcheckboxes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText inputInfoEditText;
    private EditText inputMoneyEditText;
    private CheckBox byCardCheckbox;
    private CheckBox byCashCheckbox;
    private CheckBox byPhoneCheckbox;
    private Button okButton;

    private String info;
    private String money;
    private String paymentMethod;
    private String paymentMethodMessage;
    private String inputInfoEditTextHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        inputInfoEditText = findViewById(R.id.inputInfoEditTextViewId);
        inputMoneyEditText = findViewById(R.id.inputMoneyEditTextViewId);
        byCardCheckbox = findViewById(R.id.byCardCheckboxId);
        byPhoneCheckbox = findViewById(R.id.byPhoneCheckboxId);
        byCashCheckbox = findViewById(R.id.byCashCheckboxId);
        okButton = findViewById(R.id.okButtonId);

        byCardCheckbox.setOnCheckedChangeListener(checkedChangeListener);
        byPhoneCheckbox.setOnCheckedChangeListener(checkedChangeListener);
        byCashCheckbox.setOnCheckedChangeListener(checkedChangeListener);

        inputInfoEditText.addTextChangedListener(textWatcher);
        inputMoneyEditText.addTextChangedListener(textWatcher);

        okButton.setOnClickListener(onClickListener);
    }

    private void clearEditTextViews() {
        inputMoneyEditText.setText("");
        inputInfoEditText.setText("");
    }

    private void resetCheckboxes() {
        byCashCheckbox.setChecked(false);
        byCardCheckbox.setChecked(false);
        byPhoneCheckbox.setChecked(false);
    }

    private void setViewsVisible() {
        inputMoneyEditText.setVisibility(View.VISIBLE);
        inputInfoEditText.setVisibility(View.VISIBLE);
        okButton.setVisibility(View.VISIBLE);
    }

    private void setViewsInvisible() {
        inputMoneyEditText.setVisibility(View.INVISIBLE);
        inputInfoEditText.setVisibility(View.INVISIBLE);
        okButton.setVisibility(View.INVISIBLE);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            info = inputInfoEditText.getText().toString().trim();
            money = inputMoneyEditText.getText().toString().trim();

            String toastMessage = getString(R.string.toast_message, paymentMethod, paymentMethodMessage, info, money);
            Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_LONG).show();
        }
    };

    CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(b){
                switch (compoundButton.getId()){
                    case (R.id.byCardCheckboxId):
                        byCardCheckboxAction();
                        break;
                    case (R.id.byPhoneCheckboxId):
                        byPhoneCheckboxAction();
                        break;
                    case (R.id.byCashCheckboxId):
                        byCashCheckboxAction();
                        break;
                }
            }
            if (byCashCheckbox.isChecked() || byPhoneCheckbox.isChecked() || byCardCheckbox.isChecked()) {
                setViewsVisible();
            }else setViewsInvisible();
        }
    };

    private void byCardCheckboxAction() {
        resetCheckboxes();
        byCardCheckbox.setChecked(true);
        clearEditTextViews();
        inputInfoEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        paymentMethod = getText(R.string.by_card).toString();
        paymentMethodMessage = getResources().getStringArray(R.array.payment_method_message)[PaymentMode.BY_CARD];
        inputInfoEditTextHint = getResources().getStringArray(R.array.input_info_hint)[PaymentMode.BY_CARD];
        inputInfoEditText.setHint(inputInfoEditTextHint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            inputInfoEditText.setAutofillHints(View.AUTOFILL_HINT_CREDIT_CARD_NUMBER);
        }
    }

    private void byPhoneCheckboxAction() {
        resetCheckboxes();
        byPhoneCheckbox.setChecked(true);
        clearEditTextViews();
        inputInfoEditText.setInputType(InputType.TYPE_CLASS_PHONE);
        paymentMethod = getText(R.string.by_mobile_phone).toString();
        paymentMethodMessage = getResources().getStringArray(R.array.payment_method_message)[PaymentMode.BY_PHONE];
        inputInfoEditTextHint = getResources().getStringArray(R.array.input_info_hint)[PaymentMode.BY_PHONE];
        inputInfoEditText.setHint(inputInfoEditTextHint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            inputInfoEditText.setAutofillHints(View.AUTOFILL_HINT_PHONE);
        }
    }

    private void byCashCheckboxAction() {
        resetCheckboxes();
        byCashCheckbox.setChecked(true);
        clearEditTextViews();
        inputInfoEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        paymentMethod = getText(R.string.by_cash).toString();
        paymentMethodMessage = getResources().getStringArray(R.array.payment_method_message)[PaymentMode.BY_CASH];
        inputInfoEditTextHint = getResources().getStringArray(R.array.input_info_hint)[PaymentMode.BY_CASH];
        inputInfoEditText.setHint(inputInfoEditTextHint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            inputInfoEditText.setAutofillHints(View.AUTOFILL_HINT_POSTAL_ADDRESS);
        }
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            info = inputInfoEditText.getText().toString().trim();
            money = inputMoneyEditText.getText().toString().trim();

            okButton.setEnabled(!info.isEmpty() && !money.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };
}