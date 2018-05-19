package com.example.uplabdhisingh.docufy.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uplabdhisingh.docufy.R;
import com.example.uplabdhisingh.docufy.ViewOneActivity;
import com.example.uplabdhisingh.docufy.ViewTwoActivity;
import com.example.uplabdhisingh.docufy.constants.Uploads;

import java.util.ArrayList;
import java.util.List;

public class UploadsListAdapter extends ArrayAdapter<Uploads>
{
    List<Uploads> uploadsList;

    public UploadsListAdapter(Context context, int resource, List<Uploads> objects)
    {
        super(context,resource,objects);
        uploadsList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.uploads_list_tem, parent, false);
        }

        TextView courseTextView = (TextView) convertView.findViewById(R.id.tv_course);
        TextView fileNameTextView = (TextView) convertView.findViewById(R.id.tv_file_name);

        Uploads uploads = getItem(position);

        courseTextView.setText(uploads.getCourse());
        fileNameTextView.setText(uploads.getName());

     /*   if(uploads.isSelected())
        {
            checkIV.setBackgroundResource(R.drawable.ic_check_box_black_24dp);
        } else
        {
            checkIV.setBackgroundResource(R.drawable.ic_check_box_outline_blank_black_24dp);
        }*/

        return convertView;
    }

    public void updateRecords(List<Uploads> uploadsList)
    {
        this.uploadsList = uploadsList;
        notifyDataSetChanged();
    }

}
