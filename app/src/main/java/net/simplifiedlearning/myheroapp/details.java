package net.simplifiedlearning.myheroapp;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class details extends AppCompatActivity {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    List<TableChanges> tableList;
    ListView listView;
    TableGeneral genaralTbl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        tableList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listViewHeroes);
        genaralTbl = MainActivity.forDetails;
        readHeroes();
    }
    private void readHeroes() {
        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", genaralTbl.getLogin_token());
        details.PerformNetworkRequest request = new details.PerformNetworkRequest(Api.URL_GETTABLE_CHANGES_MADE, params, CODE_POST_REQUEST);
        request.execute();
    }
    private void refreshHeroList(JSONArray heroes) throws JSONException {
//        heroList.clear();
        tableList.clear();

        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);

            tableList.add(new TableChanges(
                    obj.getInt("id"),
                    obj.getInt("user_id"),
                    obj.getString("first_name"),
                    obj.getString("last_name"),
                    obj.getString("login_token"),
                    obj.getString("last_time_started"),
                    obj.getString("last_time_ended"),
                    obj.getLong("todays_worktime")
            ));
        }

        details.HeroAdapter adapter = new details.HeroAdapter(tableList);
        listView.setAdapter(adapter);
    }
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshHeroList(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }
    class HeroAdapter extends ArrayAdapter<TableChanges> {
        List<TableChanges> tableList;

        public HeroAdapter(List<TableChanges> tableList) {
            super(details.this, R.layout.layout_detail_list, tableList);
            this.tableList = tableList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_detail_list, null, true);

            TextView textViewId = listViewItem.findViewById(R.id.textViewId);
            TextView textViewUserId = listViewItem.findViewById(R.id.textViewUserId);
            TextView textViewFirstName = listViewItem.findViewById(R.id.textViewFirstName);
            TextView textViewLastName = listViewItem.findViewById(R.id.textViewLastName);
            TextView textViewLoginToken = listViewItem.findViewById(R.id.textViewToken);
            TextView textViewTimeStarted = listViewItem.findViewById(R.id.textViewStartDate);
            TextView textViewTimeEnded = listViewItem.findViewById(R.id.textViewEndDate);
            TextView textViewTodaysWorktime = listViewItem.findViewById(R.id.textViewTotalTime);
//
//            TextView textViewDetails = listViewItem.findViewById(R.id.textViewDetails);
//            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
//            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final TableChanges table = tableList.get(position);

            textViewId.setText(table.getId());
            textViewUserId.setText(table.getUser_id());
            textViewFirstName.setText(table.getFirst_name());
            textViewLastName.setText(table.getLast_name());
            textViewLoginToken.setText(table.getLogin_token());
            textViewTimeStarted.setText(table.getLast_time_started());
            textViewTimeEnded.setText(table.getLast_time_ended());
            textViewTodaysWorktime.setText(Long.toString(table.getTodays_worktime()));

//            textViewDetails.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    openDetails(table);
//                }
//            });
//
//            textViewUpdate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    isUpdating = true;
//                    editTextTimeStarted.setVisibility(View.VISIBLE);
//                    editTextTimeEnded.setVisibility(View.VISIBLE);
//                    editTextTotalTime.setVisibility(View.VISIBLE);
//                    editTextIsWorking.setVisibility(View.VISIBLE);
//                    editTextHeroId.setText(String.valueOf(table.getId()));
//                    editTextName.setText(table.getFirst_name());
//                    editTextRealname.setText(table.getLast_name());
//                    editTextToken.setText(table.getLogin_token());
//                    editTextTimeStarted.setText(table.getLast_time_started());
//                    editTextTimeEnded.setText(table.getLast_time_ended());
//                    editTextTotalTime.setText(Long.toString(table.getTodays_worktime()));
//                    editTextIsWorking.setText(Integer.toString(table.getCheckas()));
////                    ratingBar.setRating(3);
////                    spinnerTeam.setSelection(((ArrayAdapter<String>) spinnerTeam.getAdapter()).getPosition("Avengers"));
//                    buttonAddUpdate.setText("Update");
//                }
//            });
//
//            textViewDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
////                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
////
////                    builder.setTitle("Delete " + table.getFirst_name())
////                            .setMessage("Are you sure you want to delete it?")
////                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
////                                public void onClick(DialogInterface dialog, int which) {
////                                    deleteHero(table.getLogin_token());
////                                }
////                            })
////                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
////                                public void onClick(DialogInterface dialog, int which) {
////
////                                }
////                            })
////                            .setIcon(android.R.drawable.ic_dialog_alert)
////                            .show();
//
//                }
//            });

            return listViewItem;
        }
    }
}
