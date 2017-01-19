package com.tokko.cameandwentv2.log;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.tokko.cameandwentv2.events.EventDatePicked;
import com.tokko.cameandwentv2.events.OttoBus;
import com.tokko.cameandwentv2.utils.TimeUtils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.joda.time.DateTime;

@EFragment
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private static final String EXTRA_YEAR = "EXTRA_YEAR";
    private static final String EXTRA_MONTH= "EXTRA_MONTH";
    private static final String EXTRA_DAY = "EXTRA_DAY";
    private int year;
    private int month;
    private int day;

    @Bean
    OttoBus bus;

    @Bean
    TimeUtils timeUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        year = getArguments().getInt(EXTRA_YEAR, 1);
        month = getArguments().getInt(EXTRA_MONTH, 1);
        day = getArguments().getInt(EXTRA_DAY, 1);
    }

    public static DatePickerFragment Create(int year, int month, int day){
        Bundle extras = new Bundle();
        extras.putInt(EXTRA_YEAR, year);
        extras.putInt(EXTRA_MONTH, month);
        extras.putInt(EXTRA_DAY, day);
        DatePickerFragment_ f = new DatePickerFragment_();
        f.setArguments(extras);
        return f;
    }

    public static DialogFragment Create(DateTime currentTimeDateTime) {
        return Create(currentTimeDateTime.getYear(), currentTimeDateTime.getMonthOfYear(), currentTimeDateTime.getDayOfMonth());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog dialogBuilder = new DatePickerDialog(getActivity(), this, year, month, day);
        return dialogBuilder;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        bus.post(new EventDatePicked(year, month, day));
    }

}
