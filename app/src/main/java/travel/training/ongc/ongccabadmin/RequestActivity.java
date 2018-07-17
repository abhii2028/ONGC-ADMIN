package travel.training.ongc.ongccabadmin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Model> request,assignList;
    ArrayList<String> keyList;
    DatabaseReference mdatabase,mdatabaseassign;
    ModelList adapter;
    Model a;
    MaterialDialog dialog;
    MaterialDialog.Builder materialDialog;
    TextView emptyMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        emptyMsg = findViewById(R.id.emptyMsg);
        listView = findViewById(R.id.listview);
        mdatabase = FirebaseDatabase.getInstance().getReference("Request");
        mdatabaseassign = FirebaseDatabase.getInstance().getReference("Assign");
        request = new ArrayList<>();
        keyList = new ArrayList<>();
        assignList = new ArrayList<>();
        dialog = new MaterialDialog.Builder(this)
                .title("Loading...")
                .content("Please wait.. \nFetching the data..")
                .progress(true, 0)
                .canceledOnTouchOutside(false)
                .show();
        materialDialog = new MaterialDialog.Builder(this)
                .title("Assign the Request?")
                .positiveText("Agree")
                .negativeText("Reject")
                .neutralText("Cancel");


        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dialog.dismiss();
                fetchData(dataSnapshot);
                if (dataSnapshot.getValue() == null)
                {
                    emptyMsg.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                materialDialog.show();
                materialDialog.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        request.remove(position);
                        adapter.notifyDataSetChanged();
                        mdatabaseassign.child(keyList.get(position)).setValue(assignList.get(position));
                        mdatabase.child(keyList.get(position)).removeValue();
                    }
                });
                materialDialog.onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mdatabase.child(keyList.get(position)).removeValue();
                    }
                });
                materialDialog.onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                });

            }
        });

    }

    private void fetchData(DataSnapshot dataSnapshot)
    {
        request.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            a = ds.getValue(Model.class);
            request.add(0,a);
            keyList.add(0,a.id);
            assignList.add(0,a);
        }
        adapter = new ModelList(RequestActivity.this, request);
        listView.setAdapter(adapter);
    }


}
