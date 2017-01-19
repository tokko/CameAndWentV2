package com.tokko.cameandwentv2.log;

import android.app.DialogFragment;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.squareup.otto.Subscribe;
import com.tokko.cameandwentv2.R;
import com.tokko.cameandwentv2.events.EventDatePicked;
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
import java.util.stream.Collectors;

@EFragment(R.layout.logentryedit)
public class LogEntryEditFragment extends DialogFragment implements AdapterView.OnItemClickListener {

    @ViewById
    TimePicker timePicker;
    @ViewById
    Button datePickerButton;
    @ViewById
    TextView comment;
    @ViewById
    Spinner projectPicker;
    @Bean
    TimeUtils timeUtils;
    @Bean
    LogEntryRepository logEntryRepository;
    @Bean
    ProjectRepository projectRepository;
    @Bean
    OttoBus bus;

    private ArrayAdapter<String> adapter;

    private LogEntry entry;
    private LogEditCallbacks callbacks;

    private MutableDateTime date = new MutableDateTime();
    public void setEntry(LogEntry entry){

        this.entry = entry;
    }

    @AfterInject
    public void initBus(){
        bus.register(this);
    }
    @Subscribe
    public void onDatePicked(EventDatePicked event){
        date.setDate(event.getYear(), event.getMonth(), event.getDay());
        setDateButtonText(new DateTime(event.getYear(), event.getMonth(), event.getDay(), 0, 0));
    }

    private void setDateButtonText(DateTime datetime){
        datePickerButton.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date((datetime.getMillis()))));
    }
    @Override
    public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @AfterViews
    public void initForm(){
        timePicker.setHour(new DateTime(entry.getTime()).getHourOfDay());
        timePicker.setMinute(new DateTime(entry.getTime()).getMinuteOfDay());
        comment.setText(entry.getComment());
        setDateButtonText(timeUtils.getCurrentTimeDateTime());
    }

    @Override
    public void onResume() {
        super.onResume();
        new ProjectLoader().execute();
    }

    @Click(R.id.datePickerButton)
    public void chooseDate(){
        DatePickerFragment_.Create(timeUtils.getCurrentTimeDateTime()).show(getFragmentManager(), "date");
    }
    @Click(R.id.ok)
    public void onOk(){
        /*
        LogEntry entry = new LogEntry(new DateTime(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(), timePicker.getMinute()).getMillis(), true, 1L, comment.getText().toString());
        if(entry.getId() == null)
            logEntryRepository.insertLogEntry(entry);
        else
            logEntryRepository.update(entry);
        dismiss();
        */
    }

    @Click(R.id.cancel)
    public void dismiss(){
        callbacks.OnFinished();
    }
    public void setCallbacks(LogEditCallbacks callbacks){
        this.callbacks = callbacks;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        projectPicker.setPrompt(adapter.getItem(i));
    }

    public interface LogEditCallbacks{
        void OnFinished();
    }

    public class ProjectLoader extends AsyncTask<Void, Void, List<Project>> {


        @Override
        protected List<Project> doInBackground(Void... voids) {
            return projectRepository.readAll();
        }

        @Override
        protected void onPostExecute(List<Project> projects) {
            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, projects.stream().map(Project::getTitle).collect(Collectors.toList()));
            projectPicker.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
