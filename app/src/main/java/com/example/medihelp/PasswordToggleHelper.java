package com.example.medihelp;
import android.graphics.drawable.Drawable;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PasswordToggleHelper {
    private final TextView Textview;
    private final ImageView toggleImageView;
    private final Drawable visibleIcon;
    private final Drawable hiddenIcon;

    private boolean passwordVisible = false;

    public PasswordToggleHelper(TextView textView, ImageView toggleImageView, Drawable visibleIcon, Drawable hiddenIcon) {
        this.Textview = textView;
        this.toggleImageView = toggleImageView;
        this.visibleIcon = visibleIcon;
        this.hiddenIcon = hiddenIcon;

        // Set initial transformation method
        textView.setTransformationMethod(PasswordTransformationMethod.getInstance());

        toggleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePasswordVisibility();
            }
        });
    }

    private void togglePasswordVisibility() {
        if (passwordVisible) {
            // Hide the password
            Textview.setTransformationMethod(PasswordTransformationMethod.getInstance());
            toggleImageView.setImageDrawable(hiddenIcon);
        } else {
            // Show the password
            Textview.setTransformationMethod(null);
            toggleImageView.setImageDrawable(visibleIcon);
        }
        passwordVisible = !passwordVisible;
      //  Textview.setSelection(Textview.getText().length());
    }

    // New method to directly toggle password visibility for the associated ImageView
    public void togglePasswordVisibilityForImageView() {
        togglePasswordVisibility();
    }
}

