package net.simplifiedlearning.myheroapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText editTextHeroId, editTextName, editTextRealname;
    EditText editTextToken,editTextTimeStarted,editTextTimeEnded,editTextTotalTime,editTextIsWorking;
//    RatingBar ratingBar;
//    Spinner spinnerTeam;
    public static TableGeneral forDetails;
    ProgressBar progressBar;
    ListView listView;
    Button buttonAddUpdate;

//    List<Hero> heroList;
    List<TableGeneral> tableList;

    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//=================================================================
        editTextToken = findViewById(R.id.editTextToken);
        editTextTimeStarted = findViewById(R.id.editTextTimeStarted);
        editTextTimeEnded = findViewById(R.id.editTextTimeEnded);
        editTextTotalTime = findViewById(R.id.editTextTotalTime);
        editTextIsWorking = findViewById(R.id.editTextBooleanEnd);
//        ==========================================


        editTextHeroId = (EditText) findViewById(R.id.editTextHeroId);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextRealname = (EditText) findViewById(R.id.editTextRealname);
//        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
//        spinnerTeam = (Spinner) findViewById(R.id.spinnerTeamAffiliation);

        buttonAddUpdate = (Button) findViewById(R.id.buttonAddUpdate);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewHeroes);

//        heroList = new ArrayList<>();
        tableList = new ArrayList<>();


        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUpdating) {
                    updateHero();
                } else {
                    createHero();
                }
            }
        });
        readHeroes();
    }

    private  void openDetails(TableGeneral tbl){
        forDetails = tbl;
        Intent intent = new Intent(this, details.class);
        startActivity(intent);
    }
    private void createHero() {
        String first_name = editTextName.getText().toString().trim();
        String last_name = editTextRealname.getText().toString().trim();
        String login_token = editTextToken.getText().toString().trim();


//        String team = spinnerTeam.getSelectedItem().toString();

        if (TextUtils.isEmpty(first_name)) {
            editTextName.setError("Please enter a name");
            editTextName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(last_name)) {
            editTextRealname.setError("Please enter a surname");
            editTextRealname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(last_name)) {
            editTextRealname.setError("Please enter a login token");
            editTextRealname.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("first_name", first_name);
        params.put("last_name", last_name);
        params.put("login_token", login_token);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_HERO, params, CODE_POST_REQUEST);
        request.execute();

    }

    private void readHeroes() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_HEROES, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void updateHero() {
        String id = editTextHeroId.getText().toString();
        String first_name = editTextName.getText().toString().trim();
        String last_name = editTextRealname.getText().toString().trim();
        String token = editTextToken.getText().toString();
        String last_time_started = editTextTimeStarted.getText().toString();
        String last_time_ended = editTextTimeEnded.getText().toString();
        String todays_worktime = editTextTotalTime.getText().toString();
        String checkas = editTextIsWorking.getText().toString();

//        int rating = (int) ratingBar.getRating();

//        String team = spinnerTeam.getSelectedItem().toString();


        if (TextUtils.isEmpty(first_name)) {
            editTextName.setError("Please enter name");
            editTextName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(last_name)) {
            editTextRealname.setError("Please enter real name");
            editTextRealname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(token)) {
            editTextToken.setError("Please enter a token");
            editTextToken.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(last_time_started)) {
            editTextTimeStarted.setError("Please enter a starting date");
            editTextTimeStarted.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(last_time_ended)) {
            editTextTimeEnded.setError("Please enter a ending date");
            editTextTimeEnded.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(todays_worktime)) {
            editTextTotalTime.setError("Please enter a total worktime");
            editTextTotalTime.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(checkas)) {
            editTextIsWorking.setError("Please enter a 0 or a 1");
            editTextIsWorking.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("first_name", first_name);
        params.put("last_name", last_name);
        params.put("login_token", token);
        params.put("last_time_started", last_time_started);
        params.put("last_time_ended", last_time_ended);
        params.put("todays_worktime", todays_worktime);
        params.put("checkas", checkas);
//        params.put("rating", String.valueOf(rating));
//        params.put("teamaffiliation", team);


        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_HERO, params, CODE_POST_REQUEST);
        request.execute();

        buttonAddUpdate.setText("Add");
        editTextTimeStarted.setVisibility(View.GONE);
        editTextTimeEnded.setVisibility(View.GONE);
        editTextTotalTime.setVisibility(View.GONE);
        editTextIsWorking.setVisibility(View.GONE);
        editTextName.setText("");
        editTextRealname.setText("");
        editTextToken.setText("");
        editTextTimeStarted.setText("");
        editTextTimeEnded.setText("");
        editTextTotalTime.setText("");
        editTextIsWorking.setText("");
//        ratingBar.setRating(0);
//        spinnerTeam.setSelection(0);

        isUpdating = false;
    }

    private void deleteHero(String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", token);
        PerformNetworkRequestDelete request = new PerformNetworkRequestDelete(Api.URL_DELETE_HERO, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void refreshHeroList(JSONArray heroes) throws JSONException {
//        heroList.clear();
        tableList.clear();

        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);

            tableList.add(new TableGeneral(
                    obj.getInt("id"),
                    obj.getString("first_name"),
                    obj.getString("last_name"),
                    obj.getString("login_token"),
                    obj.getString("last_time_started"),
                    obj.getString("last_time_ended"),
                    obj.getLong("todays_worktime"),
                    obj.getInt("checkas")
            ));
        }

        HeroAdapter adapter = new HeroAdapter(tableList);
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
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
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
    //================================================================================
    private class PerformNetworkRequestDelete extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequestDelete(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            readHeroes();
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

    class HeroAdapter extends ArrayAdapter<TableGeneral> {
        List<TableGeneral> tableList;

        public HeroAdapter(List<TableGeneral> tableList) {
            super(MainActivity.this, R.layout.layout_hero_list, tableList);
            this.tableList = tableList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_hero_list, null, true);

            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            TextView textViewDetails = listViewItem.findViewById(R.id.textViewDetails);
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final TableGeneral table = tableList.get(position);

            textViewName.setText(table.getFirst_name()+" "+table.getLast_name());

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
