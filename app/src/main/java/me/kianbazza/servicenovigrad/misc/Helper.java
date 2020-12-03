package me.kianbazza.servicenovigrad.misc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.google.rpc.Help;
import com.squareup.picasso.Picasso;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.services.Service;

import java.util.ArrayList;
import java.util.Arrays;

public class Helper {

    public static Helper get() {
        return new Helper();
    }

    public String[] trimArray(String[] arr) {
        String[] temp = Arrays.copyOf(arr, arr.length);

        for (int i=0; i < temp.length; i++) {
            temp[i] = temp[i].trim();
        }

        return temp;

    }

    @Deprecated
    public String threeDigitInt(int num) {
        if (num < 10 && num >= 0) {
            return "00" + num;
        } else if (num < 100) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

    public boolean contains(ArrayList<Service> serviceArrayList, Service service) {

        for (Service s : serviceArrayList) {
            if ( s.getServiceID().equals(service.getServiceID()) ) {
                return true;
            }
        }

        return false;

    }

    public void popupImageWithTitle(Activity activity, String imageTitle, String imageURL) {

        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.popupimage_with_title);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView imageTitleView = dialog.findViewById(R.id.popupImage_titleTextView);
        imageTitleView.setText(imageTitle);

        ImageView imageView = dialog.findViewById(R.id.popupImage_ImageView);
        Picasso.get().load(imageURL).into(imageView);

        dialog.show();

    }



}
