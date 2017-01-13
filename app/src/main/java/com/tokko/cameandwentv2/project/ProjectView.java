package com.tokko.cameandwentv2.project;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tokko.cameandwentv2.R;
import com.tokko.cameandwentv2.resourceaccess.ProjectRepository;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.projectlistrow)
public class ProjectView extends RelativeLayout{
    @ViewById
    public TextView text1;
    @ViewById
    ImageButton delete;

    @Bean
    ProjectRepository projectRepository;
    private Project project;

    public ProjectView(Context context) {
        super(context);
    }

    public void bind(Project project){
        this.project = project;
        text1.setText(project.getTitle());
    }

    @Click(R.id.delete)
    public void delete(){
        projectRepository.remove(project);
    }
}
