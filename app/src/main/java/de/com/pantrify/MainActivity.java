package de.com.pantrify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import de.com.pantrify.ui.SwitchPlus;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /* UI */
    private SwitchPlus breakfastSwitch;
    private RelativeLayout switchBaseLayout;
    private final String TAG = "MainActivity";
    private TextView textVStatus;
    private final String username ="MSFER";

    /* Firebase elements */
    /* Fire Database */
    private DatabaseReference databaseRootReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference breakfastStatus = databaseRootReference.child("isBreakfastOpen");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setUpListeners();
        FirebaseMessaging.getInstance().subscribeToTopic("user_"+username);
    }

    private void setUpListeners() {
        switchBaseLayout.setOnClickListener(this);
    }

    private void initView() {
        breakfastSwitch = (SwitchPlus) findViewById(R.id.switchOpenClose);
        switchBaseLayout = (RelativeLayout) findViewById(R.id.switchBaseLayout);
        textVStatus = (TextView) findViewById(R.id.textVStatus);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.switchBaseLayout:
                toggleValueInDB();
                break;
        }
    }

    private void toggleValueInDB() {
        // toggling
        if(breakfastSwitch.isChecked()) {
            breakfastStatus.setValue("no");
        }else{
            breakfastStatus.setValue("yes");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        breakfastStatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String isBreakfastOpen =  dataSnapshot.getValue(String.class);
                if(isBreakfastOpen.equalsIgnoreCase("yes")){
                    breakfastSwitch.setChecked(true);
                    textVStatus.setText("open");
                }else{
                    breakfastSwitch.setChecked(false);
                    textVStatus.setText("closed");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
