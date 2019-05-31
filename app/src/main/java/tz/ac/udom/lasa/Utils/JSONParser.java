package tz.ac.udom.lasa.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tz.ac.udom.lasa.Models.Student;

public class JSONParser {

    private static final String TAG  = JSONParser.class.getName();

    public static Student parseStudent(String jsonStudent){

        Student student = new Student();

        try {
            JSONObject jsonResponse = new JSONObject(jsonStudent);
            JSONObject jsonObject = jsonResponse.getJSONArray("server_response")
                    .getJSONObject(0);

            student.setName(jsonObject.getString("firstName")
            + " " + jsonObject.getString("middleName")
            + " " + jsonObject.getString("surname"));

            student.setCourse(jsonObject.getString("programName"));
            student.setYearOfStudy(Integer.parseInt(jsonObject.getString("yearsOfStudy")));
            student.setIsRegistered(Integer.parseInt(jsonObject.getString("status")));

            if(jsonObject.getString("sponsorship") == "Heslb"){
                Log.d(TAG, "parseStudent: Sponsorship : " + jsonObject.getString("sponsorship"));
                student.setIsBeneficiary(1);
            }else
                student.setIsBeneficiary(0);

            student.setAccountNo(jsonObject.getString("account_number"));

            String profilePhoto = jsonObject.getString("profile_image");
            if(profilePhoto != null)
                student.setProfilePhoto(profilePhoto);

            return student;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
