package com.example.yourmealplanner.Search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yourmealplanner.R;
import com.example.yourmealplanner.Search.model.Area;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> implements Filterable {

    private List<Area> areas;
    private List<Area> filteredAreas;
    private OnCountryClickListener listener;
    private Map<String, String> countryCodeMap = new HashMap<>();

    public CountryAdapter(OnCountryClickListener listener) {
        this.areas = new ArrayList<>();
        this.filteredAreas = new ArrayList<>();
        this.listener = listener;
        initializeCountryCodeMap();
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Area area = filteredAreas.get(position);
        holder.txtCountryName.setText(area.getAreaName());

        String flagUrl = getFlagUrl(area.getAreaName());
        Glide.with(holder.itemView.getContext())
                .load(flagUrl)
                .into(holder.imageCountry);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCountryClick(area.getAreaName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredAreas.size();
    }

    public void setCountries(List<Area> areas) {
        this.areas = areas;
        this.filteredAreas = new ArrayList<>(areas);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();
                List<Area> filteredList = new ArrayList<>();

                if (query.isEmpty()) {
                    filteredList.addAll(areas);
                } else {
                    for (Area area : areas) {
                        if (area.getAreaName().toLowerCase().contains(query)) {
                            filteredList.add(area);
                        }
                    }
                }

                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredAreas.clear();
                filteredAreas.addAll((List<Area>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public static class CountryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageCountry;
        TextView txtCountryName;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCountry = itemView.findViewById(R.id.imageCountry);
            txtCountryName = itemView.findViewById(R.id.txtCountryName);
        }
    }

    private void initializeCountryCodeMap() {
        countryCodeMap.put("Canadian", "ca");
        countryCodeMap.put("American", "us");
        countryCodeMap.put("British", "gb");
        countryCodeMap.put("Chinese", "cn");
        countryCodeMap.put("Croatian", "hr");
        countryCodeMap.put("Dutch", "nl");
        countryCodeMap.put("Egyptian", "eg");
        countryCodeMap.put("Filipino", "ph");
        countryCodeMap.put("French", "fr");
        countryCodeMap.put("Greek", "gr");
        countryCodeMap.put("Indian", "in");
        countryCodeMap.put("Irish", "ie");
        countryCodeMap.put("Italian", "it");
        countryCodeMap.put("Jamaican", "jm");
        countryCodeMap.put("Japanese", "jp");
        countryCodeMap.put("Kenyan", "ke");
        countryCodeMap.put("Malaysian", "my");
        countryCodeMap.put("Mexican", "mx");
        countryCodeMap.put("Moroccan", "ma");
        countryCodeMap.put("Polish", "pl");
        countryCodeMap.put("Portuguese", "pt");
        countryCodeMap.put("Russian", "ru");
        countryCodeMap.put("Spanish", "es");
        countryCodeMap.put("Thai", "th");
        countryCodeMap.put("Tunisian", "tn");
        countryCodeMap.put("Turkish", "tr");
        countryCodeMap.put("Ukrainian", "ua");
        countryCodeMap.put("Vietnamese", "vn");
    }

    private String getFlagUrl(String areaName) {
        String countryCode = countryCodeMap.get(areaName);
        if (countryCode != null) {
            return "https://flagcdn.com/160x120/" + countryCode + ".png";
        } else {
            return "https://flagcdn.com/160x120/un.png";
        }
    }
}
