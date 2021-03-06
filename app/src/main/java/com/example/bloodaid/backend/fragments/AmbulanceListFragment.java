package com.example.bloodaid.backend.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bloodaid.AllToasts;
import com.example.bloodaid.BloodAidService;
import com.example.bloodaid.R;
import com.example.bloodaid.RetrofitInstance;
import com.example.bloodaid.backend.adapters.AdminAmbulanceListadAdapter;
import com.example.bloodaid.models.AmbulanceModelClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmbulanceListFragment extends Fragment {

    ArrayList<HashMap<String, String>> ambulanceList;
    AdminAmbulanceListadAdapter adminAmbulanceListadapter;
    private RecyclerView recyclerView;
    Dialog dialog;
    ImageView closepopupimg;
    Button deletebtn;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ambulancelist,container,false);

        recyclerView = rootView.findViewById(R.id.recyclerView_adminAmbulance_listItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        ambulanceList = new ArrayList<>();


        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        final Call<List<AmbulanceModelClass>> call = RetrofitInstance.getRetrofitInstance()
                .create(BloodAidService.class)
                .ambulanceList();

        Thread t =  new Thread(new Runnable() {
            @Override
            public void run() {
                call.enqueue(new Callback<List<AmbulanceModelClass>>() {
                    @Override
                    public void onResponse(Call<List<AmbulanceModelClass>> call, Response<List<AmbulanceModelClass>> response) {

                        if(!response.isSuccessful()){
                            Toast.makeText(getContext(), "Code : "+response.code()+" .", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }else{
                            List<AmbulanceModelClass> arrayObjects = response.body();
                            if(arrayObjects.get(0).getAmbulanceId() == -1){
                                AllToasts.infoToast(getContext(),
                                        "No data found !");
                                progressDialog.dismiss();
                            }
                            else{
                                //Response parsing
                                for(AmbulanceModelClass value : arrayObjects){

                                    Integer ambulanceId = value.getAmbulanceId();
                                    String name = value.getName();
                                    String mobile = value.getMobile();
                                    String district = value.getDistrict();
                                    String email = value.getEmail();
                                    String details = value.getDetails();

                                    HashMap<String,String> ambulanceDetails = new HashMap<>();
                                    ambulanceDetails.put("ambulanceid",Integer.toString(ambulanceId));
                                    ambulanceDetails.put("name",name);
                                    ambulanceDetails.put("mobile",mobile);
                                    ambulanceDetails.put("district",district);
                                    ambulanceDetails.put("email",email);
                                    ambulanceDetails.put("details",details);

                                    ambulanceList.add(ambulanceDetails);

                                }
                                progressDialog.dismiss();
                                adminAmbulanceListadapter = new AdminAmbulanceListadAdapter(getContext(),ambulanceList);
                                recyclerView.setAdapter(adminAmbulanceListadapter);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AmbulanceModelClass>> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage()+" .", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }
        progressDialog.show();
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        return rootView;
    }


    final ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
            dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.popup_negative);
            closepopupimg = dialog.findViewById(R.id.imageView_popupNegative_close);
            deletebtn = dialog.findViewById(R.id.button_popupNegative_delete);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);

            closepopupimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adminAmbulanceListadapter.notifyDataSetChanged();
                    dialog.dismiss();

                }
            });

            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ambulanceId = ambulanceList.get(viewHolder.getAdapterPosition()).get("ambulanceid");
                    final int idx = ambulanceList.indexOf(ambulanceList.get(viewHolder.getAdapterPosition()));


                    final Call<ResponseBody> call = RetrofitInstance.getRetrofitInstance()
                            .create(BloodAidService.class)
                            .deleteAmbulance(Integer.valueOf(ambulanceId));

                    Thread thread =  new Thread(new Runnable() {
                        @Override
                        public void run() {
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    try {
                                        String s = response.body().string();

                                        //Response parsing
                                        String status;
                                        if (s.isEmpty()) {
                                            status = "Failed";
                                            Toast.makeText(getContext(), status + " .", Toast.LENGTH_LONG).show();

                                        } else {
                                            JSONObject object = new JSONObject(s);
                                            status = object.getString("message");
                                            if(status.equals("Ambulance Item Deleted")){
                                                adminAmbulanceListadapter.notifyDataSetChanged();
                                                ambulanceList.remove(ambulanceList.get(idx));
                                            }
                                            else{
                                                adminAmbulanceListadapter.notifyDataSetChanged();
                                            }
                                            Toast.makeText(getContext(), status + " .", Toast.LENGTH_LONG).show();

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getContext(), e.getMessage() + " - JSON", Toast.LENGTH_LONG).show();

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getContext(), e.getMessage() + " - IO", Toast.LENGTH_LONG).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(getContext(), t.getMessage() + " .", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.show();
        }
    };
}
