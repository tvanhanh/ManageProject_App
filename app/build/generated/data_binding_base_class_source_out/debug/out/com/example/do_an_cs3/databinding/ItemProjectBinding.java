// Generated by view binder compiler. Do not edit!
package com.example.do_an_cs3.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.do_an_cs3.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemProjectBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView NamePersonOfTaskUpdate;

  @NonNull
  public final TextView NameTaskNewUpdate;

  @NonNull
  public final TextView TimeUpdateTask;

  @NonNull
  public final CircleImageView imgUserProject;

  @NonNull
  public final TextView tvPersonCreation;

  @NonNull
  public final TextView tvProjectCreationTime;

  @NonNull
  public final TextView tvProjectDeadline;

  @NonNull
  public final TextView tvProjectName;

  @NonNull
  public final TextView tvProjectStatus;

  private ItemProjectBinding(@NonNull CardView rootView, @NonNull TextView NamePersonOfTaskUpdate,
      @NonNull TextView NameTaskNewUpdate, @NonNull TextView TimeUpdateTask,
      @NonNull CircleImageView imgUserProject, @NonNull TextView tvPersonCreation,
      @NonNull TextView tvProjectCreationTime, @NonNull TextView tvProjectDeadline,
      @NonNull TextView tvProjectName, @NonNull TextView tvProjectStatus) {
    this.rootView = rootView;
    this.NamePersonOfTaskUpdate = NamePersonOfTaskUpdate;
    this.NameTaskNewUpdate = NameTaskNewUpdate;
    this.TimeUpdateTask = TimeUpdateTask;
    this.imgUserProject = imgUserProject;
    this.tvPersonCreation = tvPersonCreation;
    this.tvProjectCreationTime = tvProjectCreationTime;
    this.tvProjectDeadline = tvProjectDeadline;
    this.tvProjectName = tvProjectName;
    this.tvProjectStatus = tvProjectStatus;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemProjectBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemProjectBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_project, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemProjectBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.NamePersonOfTaskUpdate;
      TextView NamePersonOfTaskUpdate = ViewBindings.findChildViewById(rootView, id);
      if (NamePersonOfTaskUpdate == null) {
        break missingId;
      }

      id = R.id.NameTaskNewUpdate;
      TextView NameTaskNewUpdate = ViewBindings.findChildViewById(rootView, id);
      if (NameTaskNewUpdate == null) {
        break missingId;
      }

      id = R.id.TimeUpdateTask;
      TextView TimeUpdateTask = ViewBindings.findChildViewById(rootView, id);
      if (TimeUpdateTask == null) {
        break missingId;
      }

      id = R.id.img_userProject;
      CircleImageView imgUserProject = ViewBindings.findChildViewById(rootView, id);
      if (imgUserProject == null) {
        break missingId;
      }

      id = R.id.tvPersonCreation;
      TextView tvPersonCreation = ViewBindings.findChildViewById(rootView, id);
      if (tvPersonCreation == null) {
        break missingId;
      }

      id = R.id.tvProjectCreationTime;
      TextView tvProjectCreationTime = ViewBindings.findChildViewById(rootView, id);
      if (tvProjectCreationTime == null) {
        break missingId;
      }

      id = R.id.tvProjectDeadline;
      TextView tvProjectDeadline = ViewBindings.findChildViewById(rootView, id);
      if (tvProjectDeadline == null) {
        break missingId;
      }

      id = R.id.tvProjectName;
      TextView tvProjectName = ViewBindings.findChildViewById(rootView, id);
      if (tvProjectName == null) {
        break missingId;
      }

      id = R.id.tvProjectStatus;
      TextView tvProjectStatus = ViewBindings.findChildViewById(rootView, id);
      if (tvProjectStatus == null) {
        break missingId;
      }

      return new ItemProjectBinding((CardView) rootView, NamePersonOfTaskUpdate, NameTaskNewUpdate,
          TimeUpdateTask, imgUserProject, tvPersonCreation, tvProjectCreationTime,
          tvProjectDeadline, tvProjectName, tvProjectStatus);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
