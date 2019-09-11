package com.sample.nintextest.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.sample.nintextest.R;
import com.sample.nintextest.ViewModelFactory;
import com.sample.nintextest.databinding.FragmentSearchBinding;
import com.sample.nintextest.utils.Utils;
import com.sample.nintextest.utils.Utils.Status;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class SearchFragment extends Fragment {
    private final String TAG = SearchFragment.class.getSimpleName();
    private SearchViewModel mViewModel;
    private FragmentSearchBinding mDataBinding;

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
    private ProgressBar progressBar;

    private DatePickerDialog.OnDateSetListener mOnDepartureDateSetListener;
    private DatePickerDialog.OnDateSetListener mOnReturnDateSetListener;

    private Calendar departureDate;
    private Calendar returnDate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDataBinding = FragmentSearchBinding.inflate(inflater, container, false);
        final Toolbar toolbar = mDataBinding.getRoot().findViewById(R.id.toolbar);
        ((TextView)toolbar.findViewById(R.id.toolbar_title)).setText(R.string.search_title);

        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        editTextFrom = mDataBinding.editTextFrom;
        editTextTo = mDataBinding.editTextTo;
        layoutDepartureDate = mDataBinding.clDepartureDate;
        layoutReturnDate = mDataBinding.clReturnDate;
        textDayDeparture = mDataBinding.tvDayDeparture;
        textMonthYearDeparture = mDataBinding.tvMonthYearDeparture;
        textDayNameDeparture = mDataBinding.tvDayNameDeparture;
        textDayReturn = mDataBinding.tvDayReturn;
        textMonthYearReturn = mDataBinding.tvMonthYearReturn;
        textDayNameReturn = mDataBinding.tvDayNameReturn;
        buttonSearch = mDataBinding.btSearch;
        progressBar = mDataBinding.progressCircular;

        mViewModel.responseStatus.observe(getViewLifecycleOwner(), this::consumeResponse);

        /* init UI fields */
        editTextFrom.setText(mViewModel.fromCity);
        editTextTo.setText(mViewModel.toCity);
        departureDate = mViewModel.departureDate;
        returnDate = mViewModel.returnDate;

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
                mViewModel.fromCity = editTextFrom.getText().toString();
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
                mViewModel.toCity = editTextTo.getText().toString();
            }
        });

        layoutDepartureDate.setOnClickListener(v -> {
            DatePickerDialog mDatePickerDialog = new DatePickerDialog(
                    requireActivity(),
                    mOnDepartureDateSetListener,
                    departureDate.get(Calendar.YEAR),
                    departureDate.get(Calendar.MONTH),
                    departureDate.get(Calendar.DAY_OF_MONTH));
            mDatePickerDialog.show();
        });

        layoutReturnDate.setOnClickListener(v -> {
            DatePickerDialog mDatePickerDialog = new DatePickerDialog(
                    requireActivity(),
                    mOnReturnDateSetListener,
                    returnDate.get(Calendar.YEAR),
                    returnDate.get(Calendar.MONTH),
                    returnDate.get(Calendar.DAY_OF_MONTH));
            mDatePickerDialog.show();
        });

        mOnDepartureDateSetListener = (datePicker, year, month, day) -> {
            departureDate.set(year, month, day);
            updateUi();
        };

        mOnReturnDateSetListener = (datePicker, year, month, day) -> {
            returnDate.set(year, month, day);
            updateUi();
        };

        buttonSearch.setOnClickListener(v -> {
            if (validateFields()) {
                mViewModel.getFlights(editTextFrom.getText().toString(),
                        editTextTo.getText().toString(),
                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault()).format(departureDate.getTime()),
                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault()).format(returnDate.getTime()));
            } else {
                Log.d(TAG, "Field validation failed.");
            }
        });

        updateUi();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        initViewModels(requireActivity()); //Obtain ViewModel for Fragment
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mViewModel.responseStatus.removeObserver(this::consumeResponse);
    }

    private void initViewModels(FragmentActivity activity) {
        final ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        mViewModel = ViewModelProviders.of(activity, factory).get(SearchViewModel.class);
    }

    private void updateUi() {
        Locale locale = Locale.getDefault();

        textDayDeparture.setText(String.format(locale, "%02d", departureDate.get(Calendar.DAY_OF_MONTH)));
        textMonthYearDeparture.setText(String.format(getString(R.string.month_year_format),
                new SimpleDateFormat("MMM", locale).format(departureDate.getTime()),
                departureDate.get(Calendar.YEAR)));
        textDayNameDeparture.setText(new SimpleDateFormat("EEEE", locale).format(departureDate.getTime()));

        textDayReturn.setText(String.format(locale, "%02d", returnDate.get(Calendar.DAY_OF_MONTH)));
        textMonthYearReturn.setText(String.format(getString(R.string.month_year_format),
                new SimpleDateFormat("MMM", locale).format(returnDate.getTime()),
                returnDate.get(Calendar.YEAR)));
        textDayNameReturn.setText(new SimpleDateFormat("EEEE", locale).format(returnDate.getTime()));
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

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        isValidDeparture = !departureDate.before(today);
        isValidReturn = returnDate.after(departureDate);

        Drawable customErrorDrawable = getResources().getDrawable(R.drawable.ic_error, null);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());

        if (!isValidFrom) {
            editTextFrom.setError(getString(R.string.invalid_from), customErrorDrawable);
        }
        if (!isValidTo) {
            editTextTo.setError(getString(R.string.invalid_to), customErrorDrawable);
        }
        if (!isValidDeparture) {
            Toast.makeText(requireContext(), "Invalid departure date", Toast.LENGTH_SHORT).show();
        }
        if (!isValidReturn) {
            Toast.makeText(requireContext(), "Invalid return date", Toast.LENGTH_SHORT).show();
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

    private void consumeResponse(Status status) {

        switch (status) {

            case LOADING:
                progressBar.setVisibility(View.VISIBLE);
                buttonSearch.setEnabled(false);
                break;

            case SUCCESS:
                progressBar.setVisibility(View.GONE);
                buttonSearch.setEnabled(true);
                /*Toast.makeText(requireContext(), "Success fetching API", Toast.LENGTH_SHORT).show();*/
                if (mViewModel.mFlightList.size() > 0) {
                    ((MainActivity) requireActivity()).loadFragment(Utils.FLIGHT_RESULT_SCREEN);
                } else {
                    Toast.makeText(requireContext(), "No flights found. Please try other dates.", Toast.LENGTH_SHORT).show();
                }
                break;

            case ERROR:
                progressBar.setVisibility(View.GONE);
                buttonSearch.setEnabled(true);
                Toast.makeText(requireContext(), R.string.error_fetching, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }
}
