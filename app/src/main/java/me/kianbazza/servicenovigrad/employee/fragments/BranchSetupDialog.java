package me.kianbazza.servicenovigrad.employee.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.accounts.Branch;
import me.kianbazza.servicenovigrad.misc.FragmentToActivity;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class BranchSetupDialog extends AppCompatDialogFragment {

    // Branch
    private Branch branch;

    // Interface
    private EditText branchAddress, branchPhoneNumber;
    private TextView branchOpeningTime, branchClosingTime;
    private Button btn_pickOpeningTime, btn_pickClosingTime;

    // Fragment to Activity Bus
    private FragmentToActivity callBack;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);

        try {
            callBack = (FragmentToActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentToActivity");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        branch = getArguments().getParcelable("Branch");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_branch_setup, null);

        branchAddress = view.findViewById(R.id.branch_addressTextField);
        branchPhoneNumber = view.findViewById(R.id.branch_phoneNumberTextField);

        branchOpeningTime = view.findViewById(R.id.branch_viewOpeningTime);
        branchClosingTime = view.findViewById(R.id.branch_viewClosingTime);

        btn_pickOpeningTime = view.findViewById(R.id.btn_branch_pickOpeningTime);
        btn_pickClosingTime = view.findViewById(R.id.btn_branch_pickClosingTime);

        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (validateFields()) {
                    AlertDialog dialog = (AlertDialog) getDialog();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                } else {
                    AlertDialog dialog = (AlertDialog) getDialog();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }



            }
        };

        branchAddress.addTextChangedListener(textWatcher);
        branchPhoneNumber.addTextChangedListener(textWatcher);

        branchOpeningTime.setText("");
        branchOpeningTime.addTextChangedListener(textWatcher);

        branchClosingTime.setText("");
        branchClosingTime.addTextChangedListener(textWatcher);

        btn_pickOpeningTime.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("America/Toronto"));

            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                    (view1, hourOfDay, minute) -> {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                        dateFormat.setCalendar(calendar);
                        branchOpeningTime.setText(dateFormat.format(calendar.getTime()));
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), false);

            timePickerDialog.show();
        });

        btn_pickClosingTime.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("America/Toronto"));

            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                    (view1, hourOfDay, minute) ->
                    {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                        dateFormat.setCalendar(calendar);

                        branchClosingTime.setText(dateFormat.format(calendar.getTime()));

                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), false);

            timePickerDialog.show();
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view)
                .setTitle("Setup Branch Information")
                .setNegativeButton("Cancel", (dialog, which) -> {

                    getActivity().finish();

                })
                .setPositiveButton("OK", null);

        return builder.create();
    }

    private boolean validateFields() {
        if (TextUtils.isEmpty(branchAddress.getText().toString().trim())) {
            return false;
        } else if (TextUtils.isEmpty(branchPhoneNumber.getText().toString().trim())) {
            return false;
        } else if (TextUtils.isEmpty(branchOpeningTime.getText().toString().trim())) {
            return false;
        } else if (TextUtils.isEmpty(branchClosingTime.getText().toString().trim())) {
            return false;
        } else {
            return true;
        }

    }

    private boolean deepValidateFields() {

        String branchPhoneNumberStr = branchPhoneNumber.getText().toString().trim();

        if (!TextUtils.isDigitsOnly(branchPhoneNumberStr)) {
            Toast.makeText(getActivity(), "Phone number contains characters other than digits", Toast.LENGTH_SHORT).show();
            return false;
        } else if (branchPhoneNumberStr.length() != 10) {
            Toast.makeText(getActivity(), "Only 10-digit phone numbers are accepted. Try removing '+1' at the beginning of the number?", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private void completeBranchSetup() {

        String branchAddressStr = branchAddress.getText().toString().trim();
        String branchPhoneNumberStr = branchPhoneNumber.getText().toString().trim();
        String branchOpeningTimeStr = branchOpeningTime.getText().toString().trim();
        String branchClosingTimeStr = branchClosingTime.getText().toString().trim();

        if (deepValidateFields()) {
            // Fields have passed validation
            // Finish branch setup

            // Set branch details
            branch.setAddress(branchAddressStr);
            branch.setPhoneNumber(branchPhoneNumberStr);
            branch.setOpeningTime(branchOpeningTimeStr);
            branch.setClosingTime(branchClosingTimeStr);
            branch.setDoneSetup(true);

            DatabaseReference branchRef = FirebaseDatabase.getInstance().getReference().child("branches").child(branch.getBranchID());
            branchRef.setValue(branch);

            sendDataToActivity(branch);

            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

        }


    }

    private void sendDataToActivity(Object obj) {
        callBack.communicate(obj);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Disable positive button by default
        AlertDialog dialog = (AlertDialog) getDialog();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            completeBranchSetup();
        });
    }

    @Override
    public void onDetach() {
        callBack = null;
        super.onDetach();
    }
}
