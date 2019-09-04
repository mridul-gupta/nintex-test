package com.sample.nintextest;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FragmentSearch extends Fragment {
    private EditText editTextFrom;
    private EditText editTextTo;
    private View layoutDepartureDate;
    private View layoutReturnDate;
    private TextView textDayDeparture;
    private TextView textMonthYearDeparture;
    private TextView textDayNameDeparture;
    private TextView textDayReturn;
    private TextView textMonthYearReturn;
    private TextView textDayNameReturn;
    private Button buttonSearch;

    private DatePickerDialog.OnDateSetListener mOnDepartureDateSetListener;
    private DatePickerDialog.OnDateSetListener mOnReturnDateSetListener;

    private Date departureDate;
    private int departureDay;
    private int departureMonth;
    private int departureYear;

    private Date returnDate;
    private int returnDay;
    private int returnMonth;
    private int returnYear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextFrom = getView().findViewById(R.id.editTextFrom);
        editTextTo = getView().findViewById(R.id.editTextTo);
        layoutDepartureDate = getView().findViewById(R.id.cl_departureDate);
        layoutReturnDate = getView().findViewById(R.id.cl_returnDate);
        textDayDeparture = getView().findViewById(R.id.tv_dayDeparture);
        textMonthYearDeparture = getView().findViewById(R.id.tv_monthYearDeparture);
        textDayNameDeparture = getView().findViewById(R.id.tv_dayNameDeparture);
        textDayReturn = getView().findViewById(R.id.tv_dayReturn);
        textMonthYearReturn = getView().findViewById(R.id.tv_monthYearReturn);
        textDayNameReturn = getView().findViewById(R.id.tv_dayNameReturn);
        buttonSearch = getView().findViewById(R.id.bt_search);

        editTextFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (!s.equals(s.toUpperCase())) {
                    s = s.toUpperCase();
                    editTextFrom.setText(s);
                    editTextFrom.setSelection(s.length());
                }
            }
        });

        editTextTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString().trim();
                if (!s.equals(s.toUpperCase())) {
                    s = s.toUpperCase();
                    editTextTo.setText(s);
                    editTextTo.setSelection(s.length());
                }
            }
        });

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        departureDay = day;
        departureMonth = month;
        departureYear = year;
        departureDate = new Date(departureYear, departureMonth, departureDay);

        returnDay = day + 1;
        returnMonth = month;
        returnYear = year;
        returnDate = new Date(returnYear, returnMonth, returnDay);

        layoutDepartureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePickerDialog = new DatePickerDialog(
                        requireActivity(),
                        mOnDepartureDateSetListener,
                        departureYear,
                        departureMonth,
                        departureDay);
                mDatePickerDialog.show();
            }
        });

        layoutReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePickerDialog = new DatePickerDialog(
                        requireActivity(),
                        mOnReturnDateSetListener,
                        returnYear,
                        returnMonth,
                        returnDay);
                mDatePickerDialog.show();
            }
        });

        mOnDepartureDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                departureDay = day;
                departureMonth = month;
                departureYear = year;
                departureDate = new Date(departureYear, departureMonth, departureDay);
            }
        };

        mOnReturnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                returnDay = day;
                returnMonth = month;
                returnYear = year;
                returnDate = new Date(returnYear, returnMonth, returnDay);
            }
        };

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()) {

                } else {

                }
            }
        });

        updateUi();
    }

    private void updateUi() {
        Locale locale = getResources().getConfiguration().getLocales().get(0);

        textDayDeparture.setText(String.format(locale, "%02d", departureDay));
        textMonthYearDeparture.setText(new SimpleDateFormat("MMM", locale).format(departureDate) + " " + departureYear);
        textDayNameDeparture.setText(new SimpleDateFormat("EEEE", locale).format(departureDate));

        textDayReturn.setText(String.format(locale, "%02d", returnDay));
        textMonthYearReturn.setText(new SimpleDateFormat("MMM", locale).format(returnDate) + " " + returnYear);
        textDayNameReturn.setText(new SimpleDateFormat("EEEE", locale).format(returnDate));
    }

    private boolean validateFields() {

        final boolean isValidFrom;
        final boolean isValidTo;
        final boolean isValidDeparture;
        final boolean isValidReturn;

        isValidFrom = !TextUtils.isEmpty(editTextFrom.getText()) &&
                        editTextFrom.length() == 3 &&
                        isAlphaNumeric(editTextFrom.getText().toString());

        isValidTo = !TextUtils.isEmpty(editTextTo.getText()) &&
                        editTextTo.length() == 3 &&
                        isAlphaNumeric(editTextTo.getText().toString());

        Date today = Calendar.getInstance().getTime();

        isValidDeparture = !departureDate.before(today);
        isValidReturn = returnDate.after(departureDate);

        Drawable customErrorDrawable = getResources().getDrawable(R.drawable.ic_error, null);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());

        if (!isValidFrom) {
            editTextFrom.setError("Enter valid 'From' City", customErrorDrawable);
        }
        if (!isValidTo) {
            editTextTo.setError("Enter valid 'To' City", customErrorDrawable);
        }
        return isValidFrom && isValidTo && isValidDeparture && isValidReturn;
    }

    private boolean isAlphaNumeric(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isLetterOrDigit(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
