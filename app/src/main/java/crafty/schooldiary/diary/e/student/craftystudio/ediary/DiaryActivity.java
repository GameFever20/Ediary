package crafty.schooldiary.diary.e.student.craftystudio.ediary;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import utils.DatabaseHandler;
import utils.NoticeDetail;

public class DiaryActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.dairyActivity_tabLayout);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openInputDialogue();
            }
        });

    }


    private void openInputDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");
        // I'm using fragment here so I'm using getView() to provide ViewGroup
        // but you can provide here any other instance of ViewGroup from your Fragment / Activity
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.notice_add_dialoguebox_layout, (ViewGroup) findViewById(R.id.content_notice), false);
        // Set up the input
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(viewInflated);

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                postNoticeToFireBase(input.getText().toString());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void postNoticeToFireBase(String s) {
        NoticeDetail noticeDetail = new NoticeDetail();
        noticeDetail.setDate("");
        noticeDetail.setNoticeMessage(s);
        DatabaseHandler databasehandler = new DatabaseHandler();

        switch (mViewPager.getCurrentItem()){
            case 0:

                databasehandler.postDailyComplain(noticeDetail);

                break;
            case 1:
                databasehandler.postDailyHomework(noticeDetail);
                break;
            case  2:
                databasehandler.postDailyProject(noticeDetail);
                break;

        }


        databasehandler.addNoticeListListner(new DatabaseHandler.DataBaseHandlerNoticeListner() {
            @Override
            public void onNoticeList(ArrayList<NoticeDetail> noticeDetailArrayList) {

            }

            @Override
            public void onNoticePost(boolean isSuccessful) {

                Toast.makeText(DiaryActivity.this, "Complain posted "+isSuccessful, Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        ListView noticeListView;
        ArrayAdapter<String> noticeListAdapter;
        public ArrayList<String> noticeStringList =new ArrayList<>() ;


        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_diary, container, false);

            noticeListAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1 ,noticeStringList  );

            noticeListView = (ListView)rootView.findViewById(R.id.diaryListView);
            noticeListView.setAdapter(noticeListAdapter);


            int num = getArguments().getInt(ARG_SECTION_NUMBER);
            switch (num){
                case 1:

                    if (noticeStringList.size() ==0) {
                        DatabaseHandler databasehandler =new DatabaseHandler();
                        databasehandler.getDailyComplainList(10);
                        databasehandler.addNoticeListListner(new DatabaseHandler.DataBaseHandlerNoticeListner() {
                            @Override
                            public void onNoticeList(ArrayList<NoticeDetail> noticeDetailArrayList) {
                                Toast.makeText(getContext(), ""+noticeDetailArrayList.size(), Toast.LENGTH_SHORT).show();

                                for (int i = noticeDetailArrayList.size()-1 ;i>=0 ; i-- )
                                {
                                    noticeStringList.add(noticeDetailArrayList.get(i).getNoticeMessage());
                                }


                                noticeListAdapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onNoticePost(boolean isSuccessful) {

                            }
                        });
                    }


                    break;
                case 2:

                    if (noticeStringList.size() ==0) {
                        DatabaseHandler databasehandler =new DatabaseHandler();
                        databasehandler.getDailyHomeworkList(10);
                        databasehandler.addNoticeListListner(new DatabaseHandler.DataBaseHandlerNoticeListner() {
                            @Override
                            public void onNoticeList(ArrayList<NoticeDetail> noticeDetailArrayList) {
                                Toast.makeText(getContext(), ""+noticeDetailArrayList.size(), Toast.LENGTH_SHORT).show();

                                for (int i = noticeDetailArrayList.size()-1 ;i>=0 ; i-- )
                                {
                                    noticeStringList.add(noticeDetailArrayList.get(i).getNoticeMessage());
                                }


                                noticeListAdapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onNoticePost(boolean isSuccessful) {

                            }
                        });
                    }


                    break;
                case 3:
                    if (noticeStringList.size() ==0) {
                        DatabaseHandler databasehandler =new DatabaseHandler();
                        databasehandler.getDailyprojectList(10);
                        databasehandler.addNoticeListListner(new DatabaseHandler.DataBaseHandlerNoticeListner() {
                            @Override
                            public void onNoticeList(ArrayList<NoticeDetail> noticeDetailArrayList) {
                                Toast.makeText(getContext(), ""+noticeDetailArrayList.size(), Toast.LENGTH_SHORT).show();

                                for (int i = noticeDetailArrayList.size()-1 ;i>=0 ; i-- )
                                {
                                    noticeStringList.add(noticeDetailArrayList.get(i).getNoticeMessage());
                                }


                                noticeListAdapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onNoticePost(boolean isSuccessful) {

                            }
                        });
                    }


                    break;
            }



            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "complain";
                case 1:
                    return "Homework";
                case 2:
                    return "Project";
            }
            return null;
        }
    }
}
