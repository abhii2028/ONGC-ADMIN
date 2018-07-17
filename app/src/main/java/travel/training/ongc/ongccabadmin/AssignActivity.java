package travel.training.ongc.ongccabadmin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AssignActivity extends AppCompatActivity {

    ListView listView;
    List<Model> request;
    DatabaseReference mdatabaseassign;
    ModelList adapter;
    Model a;
    MaterialDialog dialog;
    TextView emptyMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);

        emptyMsg = findViewById(R.id.emptyMsg);
        listView = findViewById(R.id.listview);
        mdatabaseassign = FirebaseDatabase.getInstance().getReference("Assign");
        request = new ArrayList<>();
        dialog = new MaterialDialog.Builder(this)
                .title("Loading...")
                .content("Please wait.. \nFetching the data..")
                .progress(true, 0)
                .canceledOnTouchOutside(false)
                .show();
        mdatabaseassign.addValueEventListener(new ValueEventListener() {
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
    }

    private void fetchData(DataSnapshot dataSnapshot)
    {
        request.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            a = ds.getValue(Model.class);
            request.add(0,a);
        }
        adapter = new ModelList(AssignActivity.this, request);
        listView.setAdapter(adapter);
    }

}
