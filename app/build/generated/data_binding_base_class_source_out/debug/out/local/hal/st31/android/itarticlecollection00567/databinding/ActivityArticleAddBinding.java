// Generated by view binder compiler. Do not edit!
package local.hal.st31.android.itarticlecollection00567.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import local.hal.st31.android.itarticlecollection00567.R;

public final class ActivityArticleAddBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView comment;

  @NonNull
  public final EditText etComment;

  @NonNull
  public final EditText etTitle;

  @NonNull
  public final EditText etUrl;

  @NonNull
  public final TextView title;

  @NonNull
  public final TextView url;

  private ActivityArticleAddBinding(@NonNull ConstraintLayout rootView, @NonNull TextView comment,
      @NonNull EditText etComment, @NonNull EditText etTitle, @NonNull EditText etUrl,
      @NonNull TextView title, @NonNull TextView url) {
    this.rootView = rootView;
    this.comment = comment;
    this.etComment = etComment;
    this.etTitle = etTitle;
    this.etUrl = etUrl;
    this.title = title;
    this.url = url;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityArticleAddBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityArticleAddBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_article_add, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityArticleAddBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.comment;
      TextView comment = ViewBindings.findChildViewById(rootView, id);
      if (comment == null) {
        break missingId;
      }

      id = R.id.etComment;
      EditText etComment = ViewBindings.findChildViewById(rootView, id);
      if (etComment == null) {
        break missingId;
      }

      id = R.id.etTitle;
      EditText etTitle = ViewBindings.findChildViewById(rootView, id);
      if (etTitle == null) {
        break missingId;
      }

      id = R.id.etUrl;
      EditText etUrl = ViewBindings.findChildViewById(rootView, id);
      if (etUrl == null) {
        break missingId;
      }

      id = R.id.title;
      TextView title = ViewBindings.findChildViewById(rootView, id);
      if (title == null) {
        break missingId;
      }

      id = R.id.url;
      TextView url = ViewBindings.findChildViewById(rootView, id);
      if (url == null) {
        break missingId;
      }

      return new ActivityArticleAddBinding((ConstraintLayout) rootView, comment, etComment, etTitle,
          etUrl, title, url);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
