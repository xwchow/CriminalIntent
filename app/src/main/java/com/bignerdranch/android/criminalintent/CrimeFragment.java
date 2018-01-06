package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import java.util.UUID;

public class CrimeFragment extends Fragment {

  private static final String ARG_CRIME_ID = "crime_id";

  private Crime crime;
  private EditText titleField;
  private Button dateButton;
  private CheckBox solvedCheckBox;

  public static CrimeFragment newInstance(UUID crimeId) {
    Bundle args = new Bundle();
    args.putSerializable(ARG_CRIME_ID, crimeId);

    CrimeFragment fragment = new CrimeFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
    Log.d("CrimeFragment", crimeId.toString());
    crime = CrimeLab.get(getActivity()).getCrime(crimeId);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_crime, container, false);

    titleField = (EditText) v.findViewById(R.id.crime_title);
    titleField.setText(crime.getTitle());
    titleField.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // This space intentionally left blank
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        crime.setTitle(charSequence.toString());
      }

      @Override
      public void afterTextChanged(Editable editable) {
        // This space intentionally left blank
      }
    });

    dateButton = (Button) v.findViewById(R.id.crime_date);
    dateButton.setText(crime.getDate().toString());
    dateButton.setEnabled(false);

    solvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
    solvedCheckBox.setChecked(crime.isSolved());
    solvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        crime.setSolved(b);
      }
    });

    return v;
  }
}
