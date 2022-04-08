package com.example.spaceapp.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spaceapp.Adapters.SpaceAdapter;
import com.example.spaceapp.Models.Space;
import com.example.spaceapp.R;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    MaterialButton btnDetailInfo;
    RecyclerView spacesView;
    List<Space> spaces = new ArrayList<>();
    SpaceAdapter spaceAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    Space selected;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        spacesView = rootView.findViewById(R.id.recycler_view_spaces);
        btnDetailInfo = rootView.findViewById(R.id.button_detail_info);
        spaceAdapter = new SpaceAdapter(spaces,getContext());
        spacesView.setAdapter(spaceAdapter);
        new GetData().execute();

        spaceAdapter.setItemClickListener(new SpaceAdapter.ItemClickListener() {
            @Override
            public void OnClick(Space space) {
                selected=space;
            }
        });
        btnDetailInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putSerializable("Space",selected);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,DetailFragment.class,args).commit();
            }
        });
        return rootView;
    }
    private class GetData extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://api.open-notify.org/iss-now.json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line="";
                while((line= reader.readLine())!=null){
                    result.append(line);
                }
                return result.toString();
            }
            catch (Exception e){
                e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject obj = new JSONObject(s);
                Space space = new Space(
                        obj.getString("message"),
                        obj.getJSONObject("iss_position"),
                        obj.getString("timestamp")
                );
                spaces.add(space);
                spaceAdapter.notifyDataSetChanged();
            }
            catch (Exception e){
                e.getMessage();
            }
        }
    }
}