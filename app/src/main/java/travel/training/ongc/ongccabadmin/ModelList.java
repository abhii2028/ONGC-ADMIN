package travel.training.ongc.ongccabadmin;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ModelList extends ArrayAdapter<Model> {

    private Activity context;
    private List<Model> modelList;


    public ModelList (Activity context, List<Model> modelList)
    {
        super(context,R.layout.card_request,modelList);
        this.context=context;
        this.modelList=modelList;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.card_request,null,true);

        TextView txtnodeid = (TextView) listViewItem.findViewById(R.id.node_id);
        TextView txtpname = (TextView) listViewItem.findViewById(R.id.person_name);
        TextView txtdname = (TextView) listViewItem.findViewById(R.id.department_name);
        TextView txttimeslot = (TextView) listViewItem.findViewById(R.id.time_slot);
        TextView txtpp = (TextView) listViewItem.findViewById(R.id.pickup_point);
        TextView txtdestination = (TextView) listViewItem.findViewById(R.id.destination);

        Model request = modelList.get(position);
        txtpname.setText(request.getPersonName());
        txtdname.setText(request.getDepartmentName());
        txttimeslot.setText(request.getTimeSlot());
        txtpp.setText(request.getPickupPoint());
        txtdestination.setText(request.getDestination());
        txtnodeid.setText(request.getId());

        return listViewItem;


    }
}
