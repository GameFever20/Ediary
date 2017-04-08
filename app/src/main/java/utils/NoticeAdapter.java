package utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import crafty.schooldiary.diary.e.student.craftystudio.ediary.R;

/**
 * Created by bunny on 09/04/17.
 */

public class NoticeAdapter extends ArrayAdapter<NoticeDetail> {
    private final Context context;
    ArrayList<NoticeDetail> noticeDetailArrayAdapter;


    public NoticeAdapter(@NonNull Context context , ArrayList<NoticeDetail> noticeDetailArrayAdapter ) {
        super(context, 0 ,noticeDetailArrayAdapter);
        this.context =context;
        this.noticeDetailArrayAdapter=noticeDetailArrayAdapter;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.noticeadapter_layout, parent, false);


        TextView textView = (TextView)rowView.findViewById(R.id.noticeadapter_layout_textview);
        textView.setText(noticeDetailArrayAdapter.get(position).getNoticeMessage());


        return rowView;
    }



}
