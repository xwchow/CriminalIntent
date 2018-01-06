package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class CrimeListFragment extends Fragment {

  private RecyclerView crimeRecyclerView;
  private CrimeAdapter adapter;

  private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView titleTextView;
    private TextView dateTextView;

    private Crime crime;

    public CrimeHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
      super(inflater.inflate(viewType == 1 ? R.layout.list_item_crime_police : R.layout.list_item_crime, parent, false));
      itemView.setOnClickListener(this);

      titleTextView = (TextView) itemView.findViewById(R.id.crime_title);
      dateTextView = (TextView) itemView.findViewById(R.id.crime_date);
    }

    @Override
    public void onClick(View view) {
      Intent intent = CrimeActivity.newIntent(getActivity(), crime.getId());
      Log.d("CrimeListFragment", crime.getId().toString());
      startActivity(intent);
    }

    public void bind(Crime crime) {
      this.crime = crime;
      titleTextView.setText(crime.getTitle());
      dateTextView.setText(crime.getDate().toString());
    }
  }

  private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

    private List<Crime> crimes;

    public CrimeAdapter(List<Crime> crimes) {
      this.crimes = crimes;
    }

    @Override
    public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

      return new CrimeHolder(layoutInflater, parent, viewType);
    }

    @Override
    public void onBindViewHolder(CrimeHolder holder, int position) {
      Crime crime = crimes.get(position);
      holder.bind(crime);
    }

    @Override
    public int getItemCount() {
      return crimes.size();
    }

    @Override
    public int getItemViewType(int position) {
      Crime crime = crimes.get(position);
      return crime.isRequiresPolice() ? 1 : 0;
    }
  }

  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

    crimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
    crimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    updateUI();

    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    updateUI();
  }

  private void updateUI() {
    CrimeLab crimeLab = CrimeLab.get(getActivity());
    List<Crime> crimes = crimeLab.getCrimes();

    if (adapter == null) {
      adapter = new CrimeAdapter(crimes);
      crimeRecyclerView.setAdapter(adapter);
    } else {
      adapter.notifyDataSetChanged();
    }
  }
}
