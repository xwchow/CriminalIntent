package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

  private static final String TAG = "CrimeLab";

  private static CrimeLab crimeLab;

  private List<Crime> crimes;

  public static CrimeLab get(Context context) {
    if (crimeLab == null) {
      crimeLab = new CrimeLab(context);
    }
    return crimeLab;
  }

  private CrimeLab(Context context) {
    crimes = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      Crime crime = new Crime();
      crime.setTitle("Crime #" + i);
      crime.setSolved(i % 2 == 0);
      crime.setRequiresPolice(i % 3 == 1);
      crimes.add(crime);
    }
  }

  public List<Crime> getCrimes() {
    return crimes;
  }

  public Crime getCrime(UUID id) {
    for (Crime crime : crimes) {
      if (crime.getId().equals(id)) {
        return crime;
      }
    }
    Log.e(TAG, "crime_id not matched");
    return null;
  }
}
