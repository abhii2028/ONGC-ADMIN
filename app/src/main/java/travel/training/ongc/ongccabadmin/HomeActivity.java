package travel.training.ongc.ongccabadmin;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    public void request(View view)
    {
        startActivity(new Intent(HomeActivity.this,RequestActivity.class));
    }

    public void assign(View view)
    {
        startActivity(new Intent(HomeActivity.this,AssignActivity.class));
    }

}
