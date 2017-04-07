package utils;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by gamef on 02-04-2017.
 */

public class DatabaseHandler {
    private DatabaseReference mDatabase;
    DataBaseHandlerNoticeListner dataBaseHandlerNoticeListner ;

    public DatabaseHandler() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }



    public void addNoticeListListner(DataBaseHandlerNoticeListner dataBaseHandlerNoticeListner){

        this.dataBaseHandlerNoticeListner =dataBaseHandlerNoticeListner;

    }

    public void postNotice(NoticeDetail noticeDetail) {
        DatabaseReference myRef = mDatabase.child("Notice");

        myRef.push().setValue(noticeDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                dataBaseHandlerNoticeListner.onNoticePost(task.isSuccessful());
            }
        });


    }

    public void getNoticeList(int limit) {

        DatabaseReference myRef = mDatabase.child("Notice");

        Query myref2= myRef.limitToLast(limit);

        myref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<NoticeDetail> noticeDetailArrayList =new ArrayList<NoticeDetail>();

                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                    NoticeDetail noticeDetail = snapshot.getValue(NoticeDetail.class);

                    noticeDetailArrayList.add(noticeDetail);
                }

                dataBaseHandlerNoticeListner.onNoticeList(noticeDetailArrayList);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


    }

    public void getDailyComplainList(int limit){

        DatabaseReference myRef = mDatabase.child("Student/Complain");

        Query myref2= myRef.limitToLast(limit);

        myref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<NoticeDetail> noticeDetailArrayList =new ArrayList<NoticeDetail>();

                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                    NoticeDetail noticeDetail = snapshot.getValue(NoticeDetail.class);

                    noticeDetailArrayList.add(noticeDetail);
                }

                dataBaseHandlerNoticeListner.onNoticeList(noticeDetailArrayList);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


    }

    public void postDailyComplain(NoticeDetail noticeDetail) {
        DatabaseReference myRef = mDatabase.child("Student/Complain");

        myRef.push().setValue(noticeDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                dataBaseHandlerNoticeListner.onNoticePost(task.isSuccessful());
            }
        });


    }


    public interface DataBaseHandlerNoticeListner {
        public void onNoticeList(ArrayList<NoticeDetail> noticeDetailArrayList);
        public void onNoticePost(boolean isSuccessful);
    }




}
