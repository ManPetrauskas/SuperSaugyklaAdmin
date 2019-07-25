package net.simplifiedlearning.myheroapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }
    class HeroAdapter extends ArrayAdapter<TableGeneral> {
        List<TableGeneral> tableList;

        public HeroAdapter(List<TableGeneral> tableList) {
            super(details.this, R.layout.layout_hero_list, tableList);
            this.tableList = tableList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_detail_list, null, true);

            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            TextView textViewDetails = listViewItem.findViewById(R.id.textViewDetails);
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final TableGeneral table = tableList.get(position);

            textViewName.setText(table.getFirst_name()+table.getLast_name());

            textViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDetails(table);
                }
            });

            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isUpdating = true;
                    editTextTimeStarted.setVisibility(View.VISIBLE);
                    editTextTimeEnded.setVisibility(View.VISIBLE);
                    editTextTotalTime.setVisibility(View.VISIBLE);
                    editTextIsWorking.setVisibility(View.VISIBLE);
                    editTextHeroId.setText(String.valueOf(table.getId()));
                    editTextName.setText(table.getFirst_name());
                    editTextRealname.setText(table.getLast_name());
                    editTextToken.setText(table.getLogin_token());
                    editTextTimeStarted.setText(table.getLast_time_started());
                    editTextTimeEnded.setText(table.getLast_time_ended());
                    editTextTotalTime.setText(Long.toString(table.getTodays_worktime()));
                    editTextIsWorking.setText(Integer.toString(table.getCheckas()));
//                    ratingBar.setRating(3);
//                    spinnerTeam.setSelection(((ArrayAdapter<String>) spinnerTeam.getAdapter()).getPosition("Avengers"));
                    buttonAddUpdate.setText("Update");
                }
            });

            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("Delete " + table.getFirst_name())
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteHero(table.getLogin_token());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });

            return listViewItem;
        }
    }
}
