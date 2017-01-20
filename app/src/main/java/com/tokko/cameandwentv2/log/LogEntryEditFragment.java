package com.tokko.cameandwentv2.log;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.tokko.cameandwentv2.R;
import com.tokko.cameandwentv2.events.OttoBus;
import com.tokko.cameandwentv2.project.Project;
import com.tokko.cameandwentv2.resourceaccess.LogEntryRepository;
import com.tokko.cameandwentv2.resourceaccess.ProjectRepository;
import com.tokko.cameandwentv2.utils.TimeUtils;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@EFragment(R.layout.logentryedit)
public class LogEntryEditFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_ID = "EXTRA_ID";
    @ViewById
    Button timePickerButton;
    @ViewById
    Button datePickerButton;
    @ViewById
    TextView comment;
    @ViewById
    Spinner projectPicker;
    @ViewById
    Button endTimePickerButton;
    @Bean
    TimeUtils timeUtils;
    @Bean
    LogEntryRepository logEntryRepository;
    @Bean
    ProjectRepository projectRepository;
    @Bean
    OttoBus bus;

    private ArrayAdapter<Project> adapter;

    private LogEntry entry;
    private LogEditCallbacks callbacks;

    private MutableDateTime date;
    private MutableDateTime endDate;

    private boolean createMode;

    public static LogEntryEditFragment_ Create(Long id){
        Bundle extra = new Bundle();
        extra.putLong(EXTRA_ID, id);
        LogEntryEditFragment_ fragment = new LogEntryEditFragment_();
        fragment.setArguments(extra);
        return fragment;
    }

    @AfterViews
    public void init() {
        date = new MutableDateTime(timeUtils.getCurrentTimeMillis());
        endDate = new MutableDateTime(timeUtils.getCurrentTimeMillis());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (LogEditCallbacks) getActivity();
    }

    @AfterInject
    public void initBus() {
        bus.register(this);
    }


    private void setDateButtonText(long time) {
        datePickerButton.setText("Date: " + new SimpleDateFormat("yyyy-MM-dd").format(new Date(time)));
    }

    private void setTimePickerButtonText(Button timePickerButton, String prefix, long time) {
        timePickerButton.setText(prefix + " " + new SimpleDateFormat("HH:mm").format(new Date(time)));
    }

    @Override
    public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, android.R.id.text1);
        projectPicker.setAdapter(adapter);
        new ProjectLoader().execute();
        Long id = getArguments().getLong(EXTRA_ID, -1);
        createMode = id == -1;
        if (createMode) {
            entry = new LogEntry(timeUtils.getCurrentTimeMillis(), true, 0L);
            endTimePickerButton.setVisibility(View.VISIBLE);
            datePickerButton.setVisibility(View.VISIBLE);
            projectPicker.setVisibility(View.VISIBLE);
            setTimePickerButtonText(endTimePickerButton, "Time of departure", endDate.getMillis());
        }
        else
            entry = logEntryRepository.get(id);
        date.setTime(entry.getTime());
        comment.setText(entry.getComment());
        setDateButtonText(entry.getTime());
        setTimePickerButtonText(timePickerButton, "Time of arrival", entry.getTime());
        projectPicker.setOnItemSelectedListener(this);
    }

    @Click(R.id.endTimePickerButton)
    public void chooseEndDate() {
        DateTime dt = new DateTime(entry.getTime());
        TimePickerDialog dialog = new TimePickerDialog(getActivity(), (timePicker, hour, minute) -> {
            endDate.setTime(hour, minute, 0, 0);
            setTimePickerButtonText(endTimePickerButton, "Time of departure", endDate.getMillis());
        }, dt.getHourOfDay(), dt.getMinuteOfHour(), true);
        dialog.show();
    }

    @Click(R.id.datePickerButton)
    public void chooseDate() {
        DateTime dt = new DateTime(entry.getTime());
        DatePickerDialog dialogBuilder = new DatePickerDialog(getActivity(), (datePicker, year, month, day) -> {
            date.setDate(year, month, day);
            setDateButtonText(date.getMillis());
        }, dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
        dialogBuilder.show();
    }

    @Click(R.id.timePickerButton)
    public void chooseTime() {
        DateTime dt = new DateTime(entry.getTime());
        TimePickerDialog dialog = new TimePickerDialog(getActivity(), (timePicker, hour, minute) -> {
            date.setTime(hour, minute, 0, 0);
            setTimePickerButtonText(timePickerButton, "Time of arrival", date.getMillis());
        }, dt.getHourOfDay(), dt.getMinuteOfHour(), true);
        dialog.show();
    }

    @Click(R.id.ok)
    public void onOk() {
        String comment = this.comment.getText().toString();
        entry.setComment(comment);
        entry.setTime(date.getMillis());
        logEntryRepository.commit(entry);
        if (createMode) {
            LogEntry end = new LogEntry(endDate.getMillis(), false, entry.getProjectId(), entry.comment);
            logEntryRepository.commit(end);
        }
        dismiss();
    }

    @Click(R.id.cancel)
    public void dismiss() {
        callbacks.OnFinished();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        entry.setProject(adapter.getItem(i));
        projectPicker.setPrompt(entry.project.getTitle());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface LogEditCallbacks {
        void OnFinished();
    }

    public class ProjectLoader extends AsyncTask<Void, Void, List<Project>> {


        @Override
        protected List<Project> doInBackground(Void... voids) {
            return projectRepository.readAll();
        }

        @Override
        protected void onPostExecute(List<Project> projects) {
            adapter.clear();
            adapter.addAll(projects);
            adapter.notifyDataSetChanged();
        }
    }
}
