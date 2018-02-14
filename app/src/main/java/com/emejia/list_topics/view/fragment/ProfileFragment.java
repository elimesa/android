package com.emejia.list_topics.view.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.emejia.list_topics.R;
import com.emejia.list_topics.adapter.ListAdapter;
import com.emejia.list_topics.model.ListItem;
import com.emejia.list_topics.utils.DBHelper;
import com.emejia.list_topics.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public RecyclerView picturesRecycler;
    private static final String URL_DATA="https://www.reddit.com/reddits.json";
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    DBHelper database;
    private ProgressDialog progresDialog ;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        showToolbar("", false, view);

        picturesRecycler = (RecyclerView) view.findViewById(R.id.picturesProfileRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        picturesRecycler.setLayoutManager(linearLayoutManager);
        progresDialog = new ProgressDialog(getContext());
        listItems = new ArrayList<>();
        loadViewData();
        return view;
    }


    public void showToolbar(String tittle, boolean upButton, View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

    private void loadViewData(){

        if (!Utils.isConnectedToNetwork(this.getContext())) {
            loadLocalData();
            return;
        }

       // final ProgressDialog progresDialog = new ProgressDialog(getContext());
        progresDialog.setMessage("Loading...");
        progresDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progresDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response).getJSONObject("data");
                            JSONArray array = jsonObject.getJSONArray("children");

                            for(int i=0; i<array.length();i++){
                                JSONObject o = array.getJSONObject(i).getJSONObject("data");
                                ListItem item = new ListItem(
                                        o.getString("title"),
                                        o.getString("public_description"),
                                        o.getString("url"),
                                        o.getString("header_img")
                                );
                                listItems.add(item);
                            }

                            adapter = new ListAdapter(listItems,getActivity());
                            picturesRecycler.setAdapter(adapter);
                            //recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progresDialog.dismiss();
                        Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void addItemLocalDataBase(ListItem item) {

        ListItem newItem = new ListItem(item.getHead(),item.getDesc(),item.getUrl(),item.getImage());

        if (!newItem.getHead().isEmpty() && !newItem.getDesc().isEmpty() && !newItem.getUrl().isEmpty()) {
            ;
            if (DBHelper.getInstance(getContext()).addItems(newItem)) {

                // Utils.cusomToast(getContext(), getLayoutInflater(), "Se agregó " + nuevo.titulo + " a tus ubicaciones favoritas.", Toast.LENGTH_SHORT);
            }
        }
    }
    private void loadLocalData() {

        try{
            listItems = DBHelper.getInstance(getContext()).ListItems();
            if(listItems!= null){
                progresDialog.dismiss();
                adapter = new ListAdapter(listItems,getActivity());
                picturesRecycler.setAdapter(adapter);
            }

        }catch(Exception ex){

        }


    }

}
